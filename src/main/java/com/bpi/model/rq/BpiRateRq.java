package com.bpi.model.rq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 更新利率 BpiRateRq
 * 
 * @author Joe
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BpiRateRq extends BaseRq {
	
	@Schema(description = "code 貨幣名稱")
	@NotBlank(message = "code must be not empty")
	String code;
	
	@Schema(description = "rate 匯率")
	@NotNull(message = "rate must be not empty")
	BigDecimal rate;
	
}
