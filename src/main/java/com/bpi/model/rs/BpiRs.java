package com.bpi.model.rs;

import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jfr.Description;
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

	@Description("貨幣名稱")
	String code;

	@Description("貨幣中文名稱")
	String codeChineseName;

	@Description("金錢符號")
	String symbol;

	@Description("匯率(千分位,)")
	String rate;

	@Description("匯率")
	BigDecimal rateFloat;

	@Description("描述")
	String description;

}
