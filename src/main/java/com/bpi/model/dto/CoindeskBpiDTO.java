package com.bpi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * for Coindesk 用的 rate_float
 * 
 * @EqualsAndHashCode(callSuper = false)
 * callSuper = true，根據子類自身的字段值和從父類繼承的字段值 来生成hashcode，當兩個子類對象比較時，只有子類對象的本身的字段值和繼承父類的字段值都相同，equals方法的返回值是true。
 * callSuper = false，根據子類自身的字段值 来生成hashcode， 當兩個子類對象比较時，只有子類对象的本身的字段值相同，父類字段值可以不同，equals方法的返回值是true。
 * 
 * @author Joe
 * 
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoindeskBpiDTO {

	@Schema(description = "貨幣名稱")
	String code;
	
	@Schema(description = "金錢符號")
	String symbol;

	@Schema(description = "描述")
	String description;
	
	@Schema(description = "匯率(千分位格式)")
	String rate;
	
	@JsonProperty("rate_float")
	Double rateFloat;

}
