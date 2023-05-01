package com.bpi.model.rs;

import java.util.Map;

import com.bpi.model.dto.CoindeskBpiDTO;
import com.bpi.model.dto.Time;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 呼叫 url coindesk return的物件
 * 
 * @author Joe
 *
 */
@Data
public class Coindesk {
	
	private Time time;
	
	@Schema(description = "免責聲明")
	private String disclaimer;
	
	private String chartName;
	
	private Map<String, CoindeskBpiDTO> bpi;
	
}
