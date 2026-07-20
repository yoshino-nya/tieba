package org.example.tieba.comment;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private Long id;
    private Long userId;
    private String content;

    private Long postId;
    private Long rootId;
    private Long parentId;

    private byte status; // 0 正常， 1 删除
    private LocalDateTime createdAt;

}
