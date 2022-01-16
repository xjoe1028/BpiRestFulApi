package com.example.demo.entity.pk;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bpi Primary Key
 * 
 * @author Joe
 *
 * @Date 2021/12/29
 */
@Data
@NoArgsConstructor
public class BpiPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("bpi id")
	private Long bpiId;
	
	@ApiModelProperty("code 貨幣名稱")
	private String code;

	@Builder
	public BpiPK(Long bpiId, String code) {
		super();
		this.bpiId = bpiId;
		this.code = code;
	}
	
}
