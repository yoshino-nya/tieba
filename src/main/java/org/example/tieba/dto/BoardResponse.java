package org.example.tieba.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardResponse {
    private Long id;
    private String name;
    private String description;
    private Long manager_id;
    private LocalDateTime created_at;

    public BoardResponse(Long id, String name, String description, Long manager_id, LocalDateTime created_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.manager_id = manager_id;
        this.created_at = created_at;
    }
}
