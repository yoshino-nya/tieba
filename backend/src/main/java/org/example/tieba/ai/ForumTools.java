package org.example.tieba.ai;

import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import org.example.tieba.board.BoardMapper;
import org.example.tieba.post.PostMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ForumTools {

    private final BoardMapper boardMapper;
    private final PostMapper postMapper;

    @Tool("获取所有贴吧列表，用于向用户推荐贴吧")
    public String listBoards() {
        var boards = boardMapper.selectAll(0, 50);
        if (boards.isEmpty()) return "暂时还没有人创建贴吧";
        return boards.stream()
                .map(b -> "%s（%d帖 / %d人）— %s".formatted(
                        b.getName(),
                        b.getPostCount(),
                        b.getMemberCount(),
                        b.getDescription() != null ? b.getDescription() : "暂无简介"))
                .collect(Collectors.joining("\n"));
    }

    @Tool("根据关键词搜索帖子，返回匹配的帖子标题和内容摘要")
    public String searchPosts(String keyword) {
        if (keyword == null || keyword.isBlank()) return "请提供搜索关键词";
        var posts = postMapper.searchByKeyword(keyword.trim(), 0L, 0, 20);
        if (posts.isEmpty()) return "没有找到与「%s」相关的帖子".formatted(keyword);
        return posts.stream()
                .map(p -> "【%s】%s — %s".formatted(
                        p.getBoardName() != null ? p.getBoardName() : "贴吧",
                        p.getTitle(),
                        p.getContent().length() > 100
                                ? p.getContent().substring(0, 100) + "..."
                                : p.getContent()))
                .collect(Collectors.joining("\n---\n"));
    }

    @Tool("获取当前日期时间")
    public String currentTime() {
        return LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
    }
}