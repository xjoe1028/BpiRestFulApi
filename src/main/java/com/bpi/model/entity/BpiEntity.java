package com.bpi.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * bpi 幣別 entity
 * 
 * @author Joe
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Bpi")
@Entity
public class BpiEntity implements Serializable {
	
	// @IdClass(BpiPK.class) 複合主鍵 
	// @Basic 它是基本類型，Hibernate 應該使用標準映射來保持其持久性。
	// @Basic註釋的屬性應用於 JPA 實體，而@Column的屬性 應用於數據庫列
	// 我們可以使用@Basic來指示一個字段應該被延遲加載
	
	/**
	 * 
	 */
	static final long serialVersionUID = 1L;
	
//	@Schema(name = "id pk")
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	Long id;

	/**
	 * 貨幣名稱
	 */
	@Schema(description = "貨幣名稱")
	@Id
	@Column(name = "CODE")
	String code;
	
	/**
	 * 貨幣中文名稱
	 */
	@Schema(description = "貨幣中文名稱")
	@Column(name = "CODE_CHINESE_NAME")
	String codeChineseName;
	
	/**
	 * 金錢格式 ex: $
	 */
	@Schema(description = "金錢符號")
	@Column(name = "SYMBOL")
	String symbol;
	
	/**
	 * 匯率 有千分位樣式 
	 */
	@Schema(description = "匯率(千分位,)")
	@Column(name = "RATE")
	String rate; 
	
	/**
	 * 匯率 
	 */
	@Schema(description = "匯率")
	@Column(name = "RATE_FLOAT")
	BigDecimal rateFloat;
	
	/**
	 * 描述
	 */
	@Schema(description = "描述")
	@Column(name = "DESCRIPTION")
	String description;
	
	/**
	 * 創建時間
	 */
	@Schema(description = "創建時間")
	@NotNull
	@Column(name = "CREATE_DATE_TIME", insertable = false, updatable = false)
	// @Convert(converter = TimeStampAttributeConverter.class)
	String createDateTime;
	
	/**
	 * 更新時間
	 */
	@Schema(description = "更新時間")
	@Column(name = "UPDATE_DATE_TIME")
	// @Convert(converter = TimeStampAttributeConverter.class)
	String updateDateTime;
	
}
