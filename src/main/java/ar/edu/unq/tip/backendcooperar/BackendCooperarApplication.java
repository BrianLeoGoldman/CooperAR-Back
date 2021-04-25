package ar.edu.unq.tip.backendcooperar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BackendCooperarApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendCooperarApplication.class, args);
	}

}
