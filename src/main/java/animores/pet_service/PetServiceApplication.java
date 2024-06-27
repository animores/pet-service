package animores.pet_service;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableBatchProcessing
@EnableAspectJAutoProxy
@ServletComponentScan
@SpringBootApplication
@EnableJpaAuditing
public class PetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetServiceApplication.class, args);
	}

}
