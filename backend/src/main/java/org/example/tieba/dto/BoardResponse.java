package org.example.tieba.dto;

import lombok.Data;
import org.example.tieba.model.Board;

import java.time.LocalDateTime;

@Data
public class BoardResponse {
    private Long id;
    private String name;
    private String description;
    private Long managerId;
    private LocalDateTime createdAt;

    public BoardResponse() {
    }

    public BoardResponse(Long id, String name, String description, Long managerId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.managerId = managerId;
        this.createdAt = createdAt;
    }
}
