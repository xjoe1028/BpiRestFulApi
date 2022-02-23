package com.bpi.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author Joe
 * 
 * @Date 2022/02/14
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CodeRq extends BaseRq {

	/**
	 * 貨幣名稱
	 */
	@ApiModelProperty("code 貨幣名稱")
	@NotBlank(message = "code must be not empty")
	public String code;
	
}
