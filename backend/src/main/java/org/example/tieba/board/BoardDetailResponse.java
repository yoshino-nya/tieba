package org.example.tieba.board;

import lombok.Data;
import org.example.tieba.common.dto.UserBrief;

import java.time.LocalDateTime;

@Data
public class BoardDetailResponse {
    private Long id;
    private String name;
    private String description;
    private UserBrief manager;
    private LocalDateTime createdAt;
    private int memberCount;
    private int postCount;

    public BoardDetailResponse() {

    }
}
