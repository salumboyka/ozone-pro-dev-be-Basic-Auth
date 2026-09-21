package ozone.com.eproz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EprozApplication {

	public static void main(String[] args) {
		SpringApplication.run(EprozApplication.class, args);
	}
}
