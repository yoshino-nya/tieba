package org.example.tieba.dto;

import lombok.Data;

@Data
public class BoardMemberResponse {
    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
    private String email;

    private String role;
    private String joinedAt;

    public BoardMemberResponse(Long userId, String username, String nickname, String avatar, String email, String role, String joinedAt) {
        this.username = username;
        this.nickname = nickname;
        this.avatar = avatar;
        this.email = email;
        this.role = role;
        this.joinedAt = joinedAt;
    }
}
