package com.SMWU.CarryUsServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CarryUsServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarryUsServerApplication.class, args);
	}

}
