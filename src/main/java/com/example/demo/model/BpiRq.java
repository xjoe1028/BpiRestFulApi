package com.example.demo.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Bpi rq
 * 
 * @author Joe
 * 
 * @Date 2022/01/17
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BpiRq extends CodeRq {

	/**
	 * 貨幣中文名稱
	 */
	@ApiModelProperty("codeChineseName 貨幣中文名稱")
	@NotBlank(message = "codeChineseName must be not empty")
	private String codeChineseName;

	/**
	 * 金錢格式 ex: $
	 */
	@ApiModelProperty("code 金錢符號")
	private String symbol;
	
	/**
	 * 匯率
	 */
	@JsonProperty("rate_float")
	@ApiModelProperty("rate 匯率")
	@NotNull(message = "rate must be not empty")
	private Double rateFloat;

	/**
	 * 描述
	 */
	@ApiModelProperty("description 描述")
	private String description;
	
	private String rate;

	@Builder
	public BpiRq(String code, String codeChineseName, String symbol, Double rateFloat, String description) {
		this.code = code;
		this.codeChineseName = codeChineseName;
		this.symbol = symbol;
		this.rateFloat = rateFloat;
		this.description = description;
	}

}
