package com.eelve.limiting.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.eelve.limiting.sentinel")
public class AwesomesentinelApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwesomesentinelApplication.class, args);
	}
}
