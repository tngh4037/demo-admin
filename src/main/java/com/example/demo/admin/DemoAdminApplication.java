package com.example.demo.admin;

import com.example.demo.admin.domain.admin.repository.AdminRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoAdminApplication.class, args);
	}

	@Bean
	@Profile("local")
	public DataInit dataInit(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		return new DataInit(adminRepository, passwordEncoder);
	}
}
