package com.bpi.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bpi.model.rq.BaseRq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Bpi for myBatis update
 * 
 * @author Joe
 * 
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BpiForMyBatis extends BaseRq {
	
	/**
	 * 貨幣名稱
	 */
	@Schema(name = "code 貨幣名稱")
	@NotBlank(message = "code must be not empty")
	String code;

	/**
	 * 貨幣中文名稱
	 */
	@Schema(name = "codeChineseName 貨幣中文名稱")
	@NotBlank(message = "codeChineseName must be not empty")
	String codeChineseName;

	/**
	 * 金錢格式 ex: $
	 */
	@Schema(name = "code 金錢符號")
	String symbol;
	
	/**
	 * 匯率
	 */
	@Schema(name = "rateFloat 匯率")
	@NotNull(message = "rateFloat must be not empty")
	BigDecimal rateFloat;

	/**
	 * 描述
	 */
	@Schema(name = "description 描述")
	String description;
	
	/**
	 * 匯率(千分位格式)
	 */
	@Schema(name = "rate 匯率(千分位格式)")
	String rate;
	
	/**
	 * 舊幣別 for update用
	 */
	@Schema(name = "舊幣別")
	String oldCode;
	
	/**
	 * 創建時間
	 */
	@Schema(name = "created 創建時間")
	String created;
	
	/**
	 * 更新時間
	 */
	@Schema(name = "updated 更新時間")
	String updated;

}
