package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * bid 比特幣種類 
 * 
 * @author Joe
 *
 * @Date 2021/10/06
 */
@Entity
@Data
@Table(name = "bpi")
@NoArgsConstructor
@AllArgsConstructor
public class Bpi implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonIgnore
	private Long bpiId;

	/**
	 * 貨幣名稱
	 */
	@ApiModelProperty("code 貨幣名稱")
	@Column
	@NotNull
	private String code;
	
	/**
	 * 貨幣中文名稱
	 */
	@ApiModelProperty("codeChineseName 貨幣中文名稱")
	@Column
	@NotNull
	private String codeChineseName;
	
	/**
	 * 金錢格式 ex: $
	 */
	@ApiModelProperty("code 金錢符號")
	@Column
	@NotNull
	private String symbol;
	
	/**
	 * 匯率 有千分位樣式 
	 */
	@ApiModelProperty("rate 匯率(千分位,)")
	@Column
	@NotNull
	private String rate; 
	
	/**
	 * 匯率 
	 */
	@ApiModelProperty("rate 匯率")
	@Column
	@NotNull
	private Double rate_float;
	
	/**
	 * 描述
	 */
	@ApiModelProperty("description 描述")
	@Column
	@NotNull
	private String description;
	
	/**
	 * 更新時間
	 */
	@ApiModelProperty("updated 更新時間")
	@Column
	@JsonIgnore
	private String updated;

	@Builder
	public Bpi(String code, String codeChineseName, String symbol, String rate, Double rate_float, String description) {
		super();
		this.code = code;
		this.codeChineseName = codeChineseName;
		this.symbol = symbol;
		this.rate = rate;
		this.rate_float = rate_float;
		this.description = description;
	}
	
}
