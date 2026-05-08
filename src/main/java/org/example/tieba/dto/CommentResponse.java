package org.example.tieba.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private Long userId;
    private String content;
    private Long postId;
    private Long rootId;
    private Long parentId;
    private Byte status;
    private LocalDateTime createdAt;
}
