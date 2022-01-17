package com.example.demo.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.example.demo.model.entity.pk.BpiPK;
import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;
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
@IdClass(BpiPK.class)
@NoArgsConstructor
public class Bpi implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 貨幣名稱
	 */
	@ApiModelProperty("code 貨幣名稱")
	@Id
	@Column
	@NotNull
	private String code;
	
	/**
	 * 貨幣中文名稱
	 */
	@ApiModelProperty("codeChineseName 貨幣中文名稱")
	@Id
	@Column
	@NotNull
	private String codeChineseName;
	
	/**
	 * 金錢格式 ex: $
	 */
	@ApiModelProperty("code 金錢符號")
	@Column
	private String symbol;
	
	/**
	 * 匯率 有千分位樣式 
	 */
	@ApiModelProperty("rate 匯率(千分位,)")
	@Column
	private String rate; 
	
	/**
	 * 匯率 
	 */
	@ApiModelProperty("rate 匯率")
	@Column
	private Double rateFloat;
	
	/**
	 * 描述
	 */
	@ApiModelProperty("description 描述")
	@Column
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
	@Column
	private String updated;

	@Builder
	public Bpi(String code, String codeChineseName, String symbol, String rate, Double rateFloat,
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
