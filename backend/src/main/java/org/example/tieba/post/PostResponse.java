package org.example.tieba.post;

import lombok.Data;
import org.example.tieba.common.dto.UserBrief;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id;
    private UserBrief author;
    private Long boardId;
    private String title;
    private String content;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean liked;

    public PostResponse() {}

    public PostResponse(Post post) {
        this.id = post.getId();
        this.boardId = post.getBoardId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}