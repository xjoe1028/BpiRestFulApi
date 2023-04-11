package com.bpi.model.dto;

import com.bpi.model.rq.BpiRq;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
 * @Date 2023/04/11
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class CoindeskBpiDTO extends BpiRq {

	@JsonProperty("rate_float")
	private Double rateFloat;

}
