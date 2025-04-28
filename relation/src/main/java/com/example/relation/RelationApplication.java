package com.example.relation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class RelationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RelationApplication.class, args);
	}

}
