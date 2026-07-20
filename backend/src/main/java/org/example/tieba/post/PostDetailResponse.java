package org.example.tieba.post;

import lombok.Data;
import org.example.tieba.common.dto.UserBrief;

import java.time.LocalDateTime;

@Data
public class PostDetailResponse {
    private Long id;
    private UserBrief author;
    private Long boardId;
    private String title;
    private String content;
    private Long likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean liked;

    public PostDetailResponse() {
    }
}
