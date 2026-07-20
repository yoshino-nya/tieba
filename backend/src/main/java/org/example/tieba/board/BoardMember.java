package org.example.tieba.board;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardMember {
    public BoardMember(Long board_id, Long user_id, String role, LocalDateTime joined_at) {
        this.board_id = board_id;
        this.user_id = user_id;
        this.role = role;
        this.joined_at = joined_at;
    }

    private Long board_id;
    private Long user_id;
    private String role;
    private LocalDateTime joined_at;
}
