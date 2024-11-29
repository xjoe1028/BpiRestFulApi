package com.bpi.model.rq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import jdk.jfr.Description;
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

	@Description("貨幣名稱")
	@NotBlank(message = "code must be not empty")
	String code;

	@Description("匯率")
	@NotNull(message = "rate must be not empty")
	BigDecimal rate;
	
}
