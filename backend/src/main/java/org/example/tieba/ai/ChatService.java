package org.example.tieba.ai;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.*;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ChatService {

    private final ChatModel model;
    private final ForumTools tools;
    private final List<ToolSpecification> toolSpecs;
    private final Map<String, Method> toolMethods;
    private final ObjectMapper mapper;
    private final Map<String, List<ChatMessage>> memories;

    private static final SystemMessage SYSTEM_MESSAGE = SystemMessage.from("""
            你是"贴吧"论坛的AI助手，性格友善幽默，偶尔玩梗。

            你可以：
            1. 根据用户兴趣，调用 listBoards 工具推荐合适的贴吧
            2. 帮用户构思帖子标题和内容
            3. 回答关于论坛功能的问题（发帖、评论、点赞、加入贴吧等）
            4. 闲聊，保持轻松活泼的语气
            5. 需要时调用 currentTime 获取当前时间

            回复控制在200字以内，用中文。推荐贴吧时务必调用 listBoards 工具获取实时数据，不要编造。
            """);

    public ChatService(ChatModel model, ForumTools tools) {
        this.model = model;
        this.tools = tools;
        this.mapper = new ObjectMapper();
        this.memories = new ConcurrentHashMap<>();

        // 从 @Tool 注解提取工具定义
        this.toolSpecs = ToolSpecifications.toolSpecificationsFrom(tools.getClass());
        this.toolMethods = new HashMap<>();
        for (Method method : tools.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(dev.langchain4j.agent.tool.Tool.class)) {
                var annotation = method.getAnnotation(dev.langchain4j.agent.tool.Tool.class);
                String name = annotation.name().isEmpty() ? method.getName() : annotation.name();
                toolMethods.put(name, method);
            }
        }
        log.info("ChatService 初始化完成，已注册 {} 个工具: {}", toolMethods.size(), toolMethods.keySet());
    }

    /**
     * 普通对话，返回完整回复。
     */
    public String chat(String userId, String userMessage) {
        List<ChatMessage> history = memories.computeIfAbsent(userId, k -> new ArrayList<>());
        List<ChatMessage> messages = buildMessages(history, UserMessage.from(userMessage));

        String reply = callWithTools(messages);

        history.add(UserMessage.from(userMessage));
        history.add(AiMessage.from(reply));
        trim(history, 40);
        return reply;
    }

    /**
     * 清空用户对话记忆。
     */
    public void clear(String userId) {
        memories.remove(userId);
    }

    private List<ChatMessage> buildMessages(List<ChatMessage> history, UserMessage userMsg) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(SYSTEM_MESSAGE);
        messages.addAll(history);
        messages.add(userMsg);
        return messages;
    }

    /**
     * 工具调用循环：不断调用 LLM，直到它返回纯文本而非工具调用请求。
     */
    private String callWithTools(List<ChatMessage> messages) {
        ChatRequest request = ChatRequest.builder()
                .messages(messages)
                .toolSpecifications(toolSpecs)
                .build();

        ChatResponse response = model.chat(request);
        AiMessage aiMessage = response.aiMessage();

        while (aiMessage.hasToolExecutionRequests()) {
            messages.add(aiMessage);
            for (ToolExecutionRequest toolReq : aiMessage.toolExecutionRequests()) {
                log.info("AI 调用工具: {} 参数: {}", toolReq.name(), toolReq.arguments());
                String result = executeTool(toolReq);
                messages.add(ToolExecutionResultMessage.from(toolReq, result));
            }

            request = ChatRequest.builder()
                    .messages(messages)
                    .toolSpecifications(toolSpecs)
                    .build();
            response = model.chat(request);
            aiMessage = response.aiMessage();
        }

        return aiMessage.text();
    }

    private String executeTool(ToolExecutionRequest request) {
        Method method = toolMethods.get(request.name());
        if (method == null) {
            return "工具不存在: " + request.name();
        }
        try {
            Parameter[] params = method.getParameters();
            if (params.length == 0) {
                return (String) method.invoke(tools);
            }
            Map<String, Object> args = mapper.readValue(request.arguments(), new TypeReference<>() {});
            Object[] values = new Object[params.length];
            for (int i = 0; i < params.length; i++) {
                values[i] = String.valueOf(args.getOrDefault(params[i].getName(), ""));
            }
            return (String) method.invoke(tools, values);
        } catch (Exception e) {
            log.error("工具执行失败: {}", request.name(), e);
            return "工具执行出错: " + e.getMessage();
        }
    }

    private void trim(List<ChatMessage> history, int maxSize) {
        if (history.size() > maxSize) {
            history.subList(0, history.size() - maxSize).clear();
        }
    }
}
