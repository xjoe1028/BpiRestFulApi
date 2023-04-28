package com.bpi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO
 * 
 * @author Joe
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewBpi {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;
	
	private String codeChineseName;
	
	private String rate;
	
	@JsonProperty("rate_float")
	private Double rateFloat;
	
}
