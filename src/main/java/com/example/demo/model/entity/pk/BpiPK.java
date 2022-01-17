package com.example.demo.model.entity.pk;

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
	
	@ApiModelProperty("code 貨幣名稱")
	private String code;
	
	@ApiModelProperty("codeChineseName 貨幣中文名稱")
	private String codeChineseName;

	@Builder
	public BpiPK(String code, String codeChineseName) {
		super();
		this.code = code;
		this.codeChineseName = codeChineseName;
	}
	
}
