package com.example.demo.model;

import java.util.Map;

import com.example.demo.model.entity.Bpi;
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
