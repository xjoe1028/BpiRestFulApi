package com.bpi.configuration;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate Configuration
 * 
 * @author Joe
 * 
 */
@Configuration
public class RestTemplateConfiguration {

	@Bean
	public RestTemplate restTesmplate(ClientHttpRequestFactory factory) {
		RestTemplate restTesmplate = new RestTemplate(factory);
		// 支持中文編碼
		restTesmplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTesmplate;
	}
	
	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(5000);
		factory.setConnectTimeout(5000);
		return factory;
	}

}
