package org.example.tieba.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private Long id;
    private Long userId;
    private Long boardId;
    private String title;
    private String content;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private byte status;

    public Post() {

    }

    public Post(Long id, Long userId, Long boardId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, byte status) {
        this.id = id;
        this.userId = userId;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }
}
