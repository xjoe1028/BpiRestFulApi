package com.bpi.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bpi.model.rq.BaseRq;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
	public String code;

	/**
	 * 貨幣中文名稱
	 */
	@Schema(name = "codeChineseName 貨幣中文名稱")
	@NotBlank(message = "codeChineseName must be not empty")
	private String codeChineseName;

	/**
	 * 金錢格式 ex: $
	 */
	@Schema(name = "code 金錢符號")
	private String symbol;
	
	/**
	 * 匯率
	 */
	@Schema(name = "rateFloat 匯率")
	@NotNull(message = "rateFloat must be not empty")
	private Double rateFloat;

	/**
	 * 描述
	 */
	@Schema(name = "description 描述")
	private String description;
	
	/**
	 * 匯率(千分位格式)
	 */
	@Schema(name = "rate 匯率(千分位格式)")
	private String rate;
	
	/**
	 * 舊幣別 for update用
	 */
	@Schema(name = "舊幣別")
	private String oldCode;
	
	/**
	 * 創建時間
	 */
	@Schema(name = "created 創建時間")
	private String created;
	
	/**
	 * 更新時間
	 */
	@Schema(name = "updated 更新時間")
	private String updated;

	@Builder
	public BpiForMyBatis(String code, String codeChineseName, String symbol, Double rateFloat, String description, String created, String updated, String rate, String oldCode) {
		this.code = code;
		this.codeChineseName = codeChineseName;
		this.symbol = symbol;
		this.rateFloat = rateFloat;
		this.description = description;
		this.created = created;
		this.updated = updated;
		this.rate = rate;
		this.oldCode = oldCode;
	}

}
