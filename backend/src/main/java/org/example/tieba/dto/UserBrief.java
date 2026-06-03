package org.example.tieba.dto;

import lombok.Data;

@Data
public class UserBrief {
    public Long id;
    public String username;
    public String nickname;
    public String avatar;
}
