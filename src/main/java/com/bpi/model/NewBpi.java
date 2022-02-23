package com.bpi.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

/**
 * 
 * 
 * @author Joe
 * 
 * @Date 2022/02/16
 *
 */
@Data
public class NewBpi implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String codeChineseName;
	
	private String rate;
	
	@JsonProperty("rate_float")
	private Double rateFloat;

	@Builder
	public NewBpi(String code, String codeChineseName, String rate, Double rateFloat) {
		super();
		this.code = code;
		this.codeChineseName = codeChineseName;
		this.rate = rate;
		this.rateFloat = rateFloat;
	}
	
	
}
