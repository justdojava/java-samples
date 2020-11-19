package com.just.done;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = "com.just")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
