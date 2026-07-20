package org.example.tieba.infra.security;

import org.example.tieba.common.AuthenticatedUser;
import org.example.tieba.common.ErrorCode;
import org.example.tieba.common.BusinessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    public Long getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    public String getCurrentUsername() {
        return getCurrentUser().getUsername();
    }

    public AuthenticatedUser getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof AuthenticatedUser)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED, "用户未登录");
        }
        return (AuthenticatedUser) auth.getPrincipal();
    }
}
