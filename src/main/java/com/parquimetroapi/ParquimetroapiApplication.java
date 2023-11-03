package com.parquimetroapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.parquimetroapi")
@EnableJpaRepositories(basePackages = "com.parquimetroapi")
public class ParquimetroapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParquimetroapiApplication.class, args);
	}

}
