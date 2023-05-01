package com.bpi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO
 * 
 * @author Joe
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewBpi {
	
	@Schema(description = "貨幣名稱")
	private String code;
	
	@Schema(description = "貨幣中文名稱")
	private String codeChineseName;
	
	@Schema(description = "匯率(千分位格式)")
	private String rate;
	
	@Schema(description = "匯率")
	private Double rateFloat;
	
}
