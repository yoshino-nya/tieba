package org.example.tieba;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@MapperScan({"org.example.tieba.user", "org.example.tieba.board", "org.example.tieba.post", "org.example.tieba.comment", "org.example.tieba.like"})
public class TiebaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TiebaApplication.class, args);
    }
}
