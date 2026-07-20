package org.example.tieba.infra.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    String key();       // 如 "comment"
    int limit();        // 如 10
    int window();       // 秒，如 10
    String message() default "操作太频繁，请稍后再试";
}