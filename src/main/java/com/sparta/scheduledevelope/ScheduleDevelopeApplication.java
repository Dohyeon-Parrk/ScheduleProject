package com.sparta.scheduledevelope;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableJpaAuditing
public class ScheduleDevelopeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleDevelopeApplication.class, args);
    }

}
