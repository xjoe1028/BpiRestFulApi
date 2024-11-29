package com.bpi.model.rq;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Bpi rq
 * 
 * @EqualsAndHashCode(callSuper = false) 
 * callSuper = true，根據子類自身的字段值和從父類繼承的字段值来生成hashcode，
 * 當兩個子類對象比較時，只有子類對象的本身的字段值和繼承父類的字段值都相同，equals方法的返回值是true。
 * callSuper = false，根據子類自身的字段值 来生成hashcode，
 * 當兩個子類對象比较時，只有子類对象的本身的字段值相同，父類字段值可以不同，equals方法的返回值是true。
 * 
 * @author Joe
 * 
 */
@Schema(description = "幣別 API Request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BpiRq extends BaseRq {

	@Description("貨幣名稱")
	@NotBlank(message = "code must be not empty")
	String code;

	@Description("貨幣中文名稱")
	@NotBlank(message = "codeChineseName must be not empty")
	String codeChineseName;

	/**
	 * 金錢格式 ex: $
	 */
	@Description("金錢符號")
	String symbol;

	@Description("匯率")
	@NotNull(message = "rateFloat must be not empty")
	BigDecimal rateFloat;

	@Description("描述")
	String description;

	@Description("匯率(千分位格式)")
	String rate;

	/**
	 * 舊幣別 for update用
	 */
	@Description("舊幣別")
	String oldCode;

}
