package org.example.tieba.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDetailResponse {
    private Long id;
    private UserBrief author;
    private Long boardId;
    private String title;
    private String content;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean liked;

    public PostDetailResponse() {
    }
}
