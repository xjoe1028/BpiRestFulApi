package com.example.demo.model;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 更新利率 BpiRateRq
 * 
 * @author Joe
 * 
 * @Date 2022/01/21
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BpiRateRq extends BaseRq {

	/**
	 * 匯率
	 */
	@ApiModelProperty("rate 貨幣名稱")
	@NotBlank(message = "rate must be not empty")
	private Double rate;
	
}
