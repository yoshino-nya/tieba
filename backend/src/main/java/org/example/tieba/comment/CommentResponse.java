package org.example.tieba.comment;

import lombok.Data;
import org.example.tieba.common.dto.UserBrief;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private UserBrief userBrief;
    private String content;
    private Long postId;
    private Long rootId;
    private Long parentId;
    private LocalDateTime createdAt;

    public CommentResponse() {}
}
