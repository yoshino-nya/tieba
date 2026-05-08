package org.example.tieba.config;

import lombok.Getter;

@Getter
public class AuthenticatedUser {
    private final Long userId;
    private final String username;

    public AuthenticatedUser(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

}
