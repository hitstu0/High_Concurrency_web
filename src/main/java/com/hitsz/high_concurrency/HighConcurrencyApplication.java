package com.hitsz.high_concurrency;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication

public class HighConcurrencyApplication {
	
	public static void main(String[] args) {		
		SpringApplication.run(HighConcurrencyApplication.class, args);
	}

}
