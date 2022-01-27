package com.example.demo.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Base rq
 * 
 * @author Joe
 *
 * @Date 2022/01/27
 * 
 */
@Data
public class BaseRq {

	/**
	 * 貨幣名稱
	 */
	@ApiModelProperty("code 貨幣名稱")
	@NotBlank(message = "code must be not empty")
	public String code;
	
}
