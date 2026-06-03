package org.example.tieba.dto;

import lombok.Data;

@Data
public class BoardMemberResponse {
    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
    public BoardMemberResponse() {

    }
}
