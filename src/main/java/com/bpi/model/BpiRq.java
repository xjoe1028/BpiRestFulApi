package com.bpi.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class BpiRq extends BaseRq {
	
	/**
	 * 貨幣名稱
	 */
	@ApiModelProperty("code 貨幣名稱")
	@NotBlank(message = "code must be not empty")
	public String code;

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
	@ApiModelProperty("rateFloat 匯率")
	@NotNull(message = "rateFloat must be not empty")
	private Double rateFloat;

	/**
	 * 描述
	 */
	@ApiModelProperty("description 描述")
	private String description;
	
	/**
	 * 匯率(千分位格式)
	 */
	@ApiModelProperty("rate 匯率(千分位格式)")
	private String rate;
	
	/**
	 * 舊幣別 for update用
	 */
	@ApiModelProperty("舊幣別")
	private String oldCode;
	
	/**
	 * 創建時間
	 */
	@ApiModelProperty("created 創建時間")
	@Basic
	@Column
	private String created;
	
	/**
	 * 更新時間
	 */
	@ApiModelProperty("updated 更新時間")
	@Basic
	@Column
	private String updated;

	@Builder
	public BpiRq(String code, String codeChineseName, String symbol, Double rateFloat, String description) {
		this.code = code;
		this.codeChineseName = codeChineseName;
		this.symbol = symbol;
		this.rateFloat = rateFloat;
		this.description = description;
	}

}
