package org.example.tieba.util;

import org.example.tieba.config.AuthenticatedUser;
import org.example.tieba.constants.ErrorCode;
import org.example.tieba.exception.BusinessException;
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
