package com.bpi.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 更新利率 BpiRateRq
 * 
 * @author Joe
 * 
 * @Date 2022/01/21
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BpiRateRq extends CodeRq {
	
	/**
	 * 匯率
	 */
	@ApiModelProperty("rate 貨幣名稱")
	@NotNull(message = "rate must be not empty")
	private Double rate;
	
}
