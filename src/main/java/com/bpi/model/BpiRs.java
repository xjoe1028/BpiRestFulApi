package com.bpi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bpi Rs
 * 
 * @author Joe
 *
 * @Date 2023/03/24
 */
@Data
@NoArgsConstructor
public class BpiRs {

	@ApiModelProperty("貨幣名稱")
	private String code;

	@ApiModelProperty("貨幣中文名稱")
	private String codeChineseName;

	@ApiModelProperty("金錢符號")
	private String symbol;

	@ApiModelProperty("匯率(千分位,)")
	private String rate;

	@ApiModelProperty("匯率")
	private Double rateFloat;

	@ApiModelProperty("描述")
	private String description;

	@Builder
	public BpiRs(String code, String codeChineseName, String symbol, String rate, Double rateFloat,
			String description) {
		this.code = code;
		this.codeChineseName = codeChineseName;
		this.symbol = symbol;
		this.rate = rate;
		this.rateFloat = rateFloat;
		this.description = description;
	}
}
