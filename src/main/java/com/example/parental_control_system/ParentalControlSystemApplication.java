package com.example.parental_control_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories
@EnableWebSocketMessageBroker
public class ParentalControlApplication {
	public static void main(String[] args) {
		SpringApplication.run(ParentalControlApplication.class, args);
	}
}

