package org.example.tieba.infra.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.tieba.infra.aop.RateLimit;
import org.example.tieba.common.ErrorCode;
import org.example.tieba.common.BusinessException;
import org.example.tieba.infra.security.SecurityUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final StringRedisTemplate redis;
    private final SecurityUtil securityUtil;

    @Around("@annotation(rateLimit)")
    public Object around(ProceedingJoinPoint pjp, RateLimit rateLimit) throws Throwable {
        String userId = String.valueOf(securityUtil.getCurrentUserId());
        String redisKey = "rate:" + rateLimit.key() + ":" + userId;

        Long count = redis.opsForValue().increment(redisKey);
        if (count == 1) {
            redis.expire(redisKey, Duration.ofSeconds(rateLimit.window()));
        }
        if (count > rateLimit.limit()) {
            throw new BusinessException(ErrorCode.TOO_MANY_REQUESTS, rateLimit.message());
        }
        return pjp.proceed();
    }
}