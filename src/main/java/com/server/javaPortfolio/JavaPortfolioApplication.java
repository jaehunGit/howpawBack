package com.server.javaPortfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/oauth.yml")
public class JavaPortfolioApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaPortfolioApplication.class, args);
	}
}

