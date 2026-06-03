package org.example.tieba.dto;

import lombok.Data;
import org.example.tieba.model.Comment;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private Long userId;
    private String content;
    private Long postId;
    private Long rootId;
    private Long parentId;
    private LocalDateTime createdAt;

    public CommentResponse() {}

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.postId = comment.getPostId();
        this.rootId = comment.getRootId();
        this.parentId = comment.getParentId();
        this.createdAt = comment.getCreatedAt();
    }
}
