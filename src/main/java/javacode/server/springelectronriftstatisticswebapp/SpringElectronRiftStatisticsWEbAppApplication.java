package javacode.server.springelectronriftstatisticswebapp;

import javacode.server.springelectronriftstatisticswebapp.config.AppConfig;
import javacode.server.springelectronriftstatisticswebapp.config.AppConfig;
import no.stelar7.api.r4j.basic.cache.impl.FileSystemCacheProvider;
import no.stelar7.api.r4j.basic.calling.DataCall;
import no.stelar7.api.r4j.impl.R4J;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Import(AppConfig.class)
public class SpringElectronRiftStatisticsWEbAppApplication {

    public static void main (String[] args) {
        SpringApplication.run(SpringElectronRiftStatisticsWEbAppApplication.class, args);
    }

}