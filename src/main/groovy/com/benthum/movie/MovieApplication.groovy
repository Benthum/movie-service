package com.benthum.movie

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class MovieApplication {
    static void main(String[] args) {
        SpringApplication.run MovieApplication, args
    }
}
