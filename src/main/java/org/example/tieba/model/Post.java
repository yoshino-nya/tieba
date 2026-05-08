package org.example.tieba.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private Long id;
    private Long user_id;
    private Long board_id;
    private String title;
    private String content;
    private Long like_count;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private byte status;
}
