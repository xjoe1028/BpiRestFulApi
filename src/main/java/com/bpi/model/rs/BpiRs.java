package com.bpi.model.rs;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
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
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
