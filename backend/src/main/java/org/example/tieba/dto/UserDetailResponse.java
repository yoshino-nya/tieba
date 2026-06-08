package org.example.tieba.dto;

import lombok.Data;

@Data
public class UserDetailResponse {
    private Long id;
    private String username;
    private String email;
    private String nickname;
    private String avatar;

    public UserDetailResponse() {

    }
}
