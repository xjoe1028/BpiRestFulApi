package com.example.demo.model;

import java.io.Serializable;

import javax.persistence.Column;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
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
@NoArgsConstructor
public class BpiRq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 貨幣名稱
	 */
	@ApiModelProperty("code 貨幣名稱")
	private String code;
	
	/**
	 * 貨幣中文名稱
	 */
	@ApiModelProperty("codeChineseName 貨幣中文名稱")
	private String codeChineseName;
	
	/**
	 * 金錢格式 ex: $
	 */
	@ApiModelProperty("code 金錢符號")
	private String symbol;
	
	/**
	 * 匯率 有千分位樣式 
	 */
	@ApiModelProperty("rate 匯率(千分位,)")
	private String rate; 
	
	/**
	 * 匯率 
	 */
	@ApiModelProperty("rate 匯率")
	private Double rateFloat;
	
	/**
	 * 描述
	 */
	@ApiModelProperty("description 描述")
	private String description;
	
	/**
	 * 創建時間
	 */
	@ApiModelProperty("created 創建時間")
	@Column
	private String created;
	
	/**
	 * 更新時間
	 */
	@ApiModelProperty("updated 更新時間")
	private String updated;

	@Builder
	public BpiRq(String code, String codeChineseName, String symbol, String rate, Double rateFloat,
			String description, String created, String updated) {
		super();
		this.code = code;
		this.codeChineseName = codeChineseName;
		this.symbol = symbol;
		this.rate = rate;
		this.rateFloat = rateFloat;
		this.description = description;
		this.created = created;
		this.updated = updated;
	}
	
}