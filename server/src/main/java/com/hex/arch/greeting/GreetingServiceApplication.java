package com.hex.arch.greeting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GreetingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreetingServiceApplication.class, args);
    }
}

