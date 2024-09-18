package com.bpi.model.rs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Bpi Rs
 * 
 * @author Joe
 *
 */
@Schema(description = "幣別 API Response")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpiRs {

	@Schema(description = "貨幣名稱")
	String code;

	@Schema(description = "貨幣中文名稱")
	String codeChineseName;

	@Schema(description = "金錢符號")
	String symbol;

	@Schema(description = "匯率(千分位,)")
	String rate;

	@Schema(description = "匯率")
	BigDecimal rateFloat;

	@Schema(description = "描述")
	String description;

}
