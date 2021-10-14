package com.example.demo.entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * 
 * @author Joe
 *
 * @Date 2021/10/07
 */
@Data
public class NewBpi {

	@JsonProperty("updated")
	private String updated;
	
	private Map<String, Bpi> newBpi;
}
