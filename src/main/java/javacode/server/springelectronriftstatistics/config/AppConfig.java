package javacode.server.springelectronriftstatistics.config;

import javacode.server.springelectronriftstatistics.HtmlFactory.HtmlFactory;
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