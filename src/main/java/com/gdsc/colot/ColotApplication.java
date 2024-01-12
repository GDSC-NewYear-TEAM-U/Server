package com.gdsc.colot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ColotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ColotApplication.class, args);
    }

}
