package javacode.server.springelectronriftstatistics;

import javacode.server.springelectronriftstatistics.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Import(AppConfig.class)
public class SpringElectronRiftStatisticsApplication {

	public static void main (String[] args) {
		SpringApplication.run(SpringElectronRiftStatisticsApplication.class, args);
	}

}