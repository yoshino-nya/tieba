package org.example.tieba.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {
    private Long id;
    private String name;
    private String description;
    private Long manager_id;
    private LocalDateTime created_at;
}
