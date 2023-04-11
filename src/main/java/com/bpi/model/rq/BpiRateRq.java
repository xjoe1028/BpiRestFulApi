package com.bpi.model.rq;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class BpiRateRq extends BaseRq {
	
	@Schema(description = "code 貨幣名稱")
	@NotBlank(message = "code must be not empty")
	public String code;
	
	@Schema(description = "rate 匯率")
	@NotNull(message = "rate must be not empty")
	private Double rate;
	
}
