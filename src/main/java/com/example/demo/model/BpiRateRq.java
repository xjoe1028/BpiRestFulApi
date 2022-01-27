package com.example.demo.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 更新利率 BpiRateRq
 * 
 * @author Joe
 * 
 * @Date 2022/01/21
 *
 */
@Data
public class BpiRateRq {

	/**
	 * 貨幣名稱
	 */
	@ApiModelProperty("code 貨幣名稱")
	@NotBlank(message = "code must be not empty")
	private String code;
	
	/**
	 * 匯率
	 */
	@ApiModelProperty("rate 貨幣名稱")
	@NotBlank(message = "rate must be not empty")
	private Double rate;
	
}
