package com.bpi.model;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 呼叫 url coindesk return的物件
 * 
 * @author Joe
 *
 * @Date 2021/10/06
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
	@ApiModelProperty("disclaimer 免責聲明")
	private String disclaimer;
	
	private String chartName;
	
	private transient Map<String, BpiRq> bpi;
	
}
