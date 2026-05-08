package org.example.tieba.model;

import lombok.Data;

@Data
public class PostLike {
    private Long post_id;
    private Long user_id;
    private byte status;
}
