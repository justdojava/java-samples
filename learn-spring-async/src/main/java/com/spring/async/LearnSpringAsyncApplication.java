package com.spring.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class LearnSpringAsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringAsyncApplication.class, args);
    }

}
