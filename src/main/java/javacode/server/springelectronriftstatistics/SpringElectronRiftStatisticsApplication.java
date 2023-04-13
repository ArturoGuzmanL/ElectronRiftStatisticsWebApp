package javacode.server.springelectronriftstatistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringElectronRiftStatisticsApplication {

	public static void main (String[] args) {
		SpringApplication.run(SpringElectronRiftStatisticsApplication.class, args);
	}

}