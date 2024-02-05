package com.example.demo;

import com.example.demo.domain.admin.repository.AdminRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@Profile("local")
	public DataInit dataInit(AdminRepository adminRepository) {
		return new DataInit(adminRepository);
	}
}
