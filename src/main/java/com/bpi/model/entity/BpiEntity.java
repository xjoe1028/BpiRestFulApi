package com.bpi.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	@NotNull
	String code;
	
	/**
	 * 貨幣中文名稱
	 */
	@Schema(description = "貨幣中文名稱")
	@Basic
	@Column
	@NotNull
	String codeChineseName;
	
	/**
	 * 金錢格式 ex: $
	 */
	@Schema(description = "金錢符號")
	@Basic
	@Column
	String symbol;
	
	/**
	 * 匯率 有千分位樣式 
	 */
	@Schema(description = "匯率(千分位,)")
	@Basic
	@Column
	String rate; 
	
	/**
	 * 匯率 
	 */
	@Schema(description = "匯率")
	@Basic
	@Column
	BigDecimal rateFloat;
	
	/**
	 * 描述
	 */
	@Schema(description = "描述")
	@Basic
	@Column
	String description;
	
	/**
	 * 創建時間
	 */
	@Schema(description = "創建時間")
	@Basic
	@Column
	@NotNull
	String created;
	
	/**
	 * 更新時間
	 */
	@Schema(description = "更新時間")
	@Basic
	@Column
	String updated;
	
}
