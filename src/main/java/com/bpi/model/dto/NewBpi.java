package com.bpi.model.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * @author Joe
 * 
 * @Date 2022/02/16
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
	
}
