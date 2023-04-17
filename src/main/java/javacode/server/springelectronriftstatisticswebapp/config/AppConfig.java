package javacode.server.springelectronriftstatisticswebapp.config;

import javacode.server.springelectronriftstatisticswebapp.HtmlFactory.HtmlFactory;
import javacode.server.springelectronriftstatisticswebapp.HtmlFactory.HtmlFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {
    @Bean
    public HtmlFactory htmlFactory() throws IOException {
        return HtmlFactory.getInstance();
    }
}