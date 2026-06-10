package org.example.tieba.controller;

import lombok.RequiredArgsConstructor;
import org.example.tieba.ai.ChatService;
import org.example.tieba.common.Result;
import org.example.tieba.dto.ChatRequest;
import org.example.tieba.util.SecurityUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SecurityUtil securityUtil;

    @PostMapping
    public Result<Map<String, String>> chat(@RequestBody ChatRequest req) {
        String userId = securityUtil.getCurrentUserId().toString();
        String reply = chatService.chat(userId, req.getMessage());
        return Result.success(Map.of("reply", reply));
    }

    @DeleteMapping("/memory")
    public Result<Void> clearMemory() {
        String userId = securityUtil.getCurrentUserId().toString();
        chatService.clear(userId);
        return Result.success("对话记忆已清空", null);
    }
}
