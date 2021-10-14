package com.example.demo.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 時間 entity
 * 
 * @author Joe
 *
 * @Date 2021/10/06
 */
//@Entity
@Data
//@Table(name = "time")
public class Time implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long timeId;
	
	/**
	 * ex: Oct 6, 2021 10:58:00 UTC
	 */
	@ApiModelProperty("updated 修改日期")
//	@Column
	private String updated;
	
	/**
	 * ex: 2021-10-06T10:58:00+00:00
	 */
	@ApiModelProperty("updatedISO 修改日期ISO")
//	@Column
	private String updatedISO;
	
	/**
	 * 原時區+一小時
	 * ex: Oct 6, 2021 at 11:58 BST
	 */
	@ApiModelProperty("updateduk 修改日期")
//	@Column
	private String updateduk;

}
