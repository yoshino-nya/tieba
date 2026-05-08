package org.example.tieba.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateCommentRequest {
    private Long userId;

    @NotBlank
    private String content;

    private Long postId;
    private Long parentId;

    private LocalDateTime createdAt;
}
