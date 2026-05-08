package org.example.tieba.dto;

import lombok.Data;
import org.example.tieba.model.Post;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id;
    private Long user_id;
    private Long board_id;
    private String title;
    private String content;
    private Long like_count;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private byte status;

    public PostResponse(Long id, Long user_id, Long board_id, String title, String content, Long like_count, LocalDateTime created_at, LocalDateTime updated_at, byte status) {
        this.id = id;
        this.user_id = user_id;
        this.board_id = board_id;
        this.title = title;
        this.content = content;
        this.like_count = like_count;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.status = status;
    }

    public PostResponse(Post post) {
        this.id = post.getId();
        this.user_id = post.getUser_id();
        this.board_id = post.getBoard_id();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.like_count = post.getLike_count();
        this.created_at = post.getCreated_at();
        this.updated_at = post.getUpdated_at();
        this.status = post.getStatus();
    }
}
