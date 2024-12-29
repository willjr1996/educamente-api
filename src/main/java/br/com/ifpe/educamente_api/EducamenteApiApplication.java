package br.com.ifpe.educamente_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EducamenteApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EducamenteApiApplication.class, args);
	}

}
