package com.example.vvs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class VvsApplication {

    public static void main(String[] args) {
        SpringApplication.run(VvsApplication.class, args);
    }

}
