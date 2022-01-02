package com.hitsz.high_concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class HighConcurrencyApplication {
	public static void main(String[] args) {		
		SpringApplication.run(HighConcurrencyApplication.class, args);
	}

}
