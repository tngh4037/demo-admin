package web.commerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import web.commerce.domain.customer.repository.NoticeRepository;

@SpringBootApplication
public class CommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceApplication.class, args);
	}

	@Bean
	@Profile("local")
	public DataInit dataInit(NoticeRepository noticeRepository) {
		return new DataInit(noticeRepository);
	}
}
