package com.bpi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = { "com.bpi.mybatis" })
@ComponentScan(basePackages = { "com.bpi" })
@SpringBootApplication
public class BpiRestFulApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BpiRestFulApiApplication.class, args);
	}

}
