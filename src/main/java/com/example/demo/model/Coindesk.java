package com.example.demo.model;

import java.io.Serializable;
import java.util.Map;

import com.example.demo.model.entity.Bpi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 貨幣
 * 
 * @author Joe
 *
 * @Date 2021/10/06
 */
//@Entity
//@Table(name = "coindesk")
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
	
	private Map<String, Bpi> bpi;
	
}
