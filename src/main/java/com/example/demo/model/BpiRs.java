package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

import com.example.demo.model.entity.Bpi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Bpi rs
 * 
 * @author Joe
 * 
 * @Date 2022/01/17
 */
@Data
public class BpiRs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Bpi bpi;
	
	private List<Bpi> bpiList;
	
	@ApiModelProperty("return 訊息內容")
	private String message;
	
	@Builder
	public BpiRs(Bpi bpi, List<Bpi> bpiList , String message) {
		this.bpi = bpi;
		this.bpiList = bpiList;
		this.message = message;
	}
}
