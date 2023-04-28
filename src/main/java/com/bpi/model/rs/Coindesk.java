package com.bpi.model.rs;

import java.io.Serializable;
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
public class Coindesk implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Time time;
	
	/**
	 * 免責聲明
	 */
	@Schema(description = "disclaimer 免責聲明")
	private String disclaimer;
	
	private String chartName;
	
	private transient Map<String, CoindeskBpiDTO> bpi;
	
}
