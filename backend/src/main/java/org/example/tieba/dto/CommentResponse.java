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
    private Byte status;
    private LocalDateTime createdAt;

    public CommentResponse(Long id, Long userId, String content, Long postId, Long rootId, Long parentId, Byte status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.postId = postId;
        this.rootId = rootId;
        this.parentId = parentId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUserId();
        this.content = comment.getContent();
        this.postId = comment.getPostId();
        this.rootId = comment.getRootId();
        this.parentId = comment.getParentId();
        this.status = comment.getStatus();
        this.createdAt = comment.getCreatedAt();
    }


}
