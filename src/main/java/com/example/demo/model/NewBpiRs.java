package com.example.demo.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Joe
 *
 * @Date 2021/10/07
 */
@Data
@NoArgsConstructor
public class NewBpiRs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String updated;

	private List<NewBpi> bpisList;

	private Map<String, NewBpi> bpisMap;

	@Builder
	public NewBpiRs(String updated, List<NewBpi> bpisList, Map<String, NewBpi> bpisMap) {
		super();
		this.updated = updated;
		this.bpisList = bpisList;
		this.bpisMap = bpisMap;
	}

}
