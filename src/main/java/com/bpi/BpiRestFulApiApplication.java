package com.bpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.bpi")
@SpringBootApplication
public class BpiRestFulApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpiRestFulApiApplication.class, args);
	}

}
