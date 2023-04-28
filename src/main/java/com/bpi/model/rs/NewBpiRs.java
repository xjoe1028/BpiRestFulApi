package com.bpi.model.rs;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bpi.model.dto.NewBpi;

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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewBpiRs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String updated;

	private List<NewBpi> bpisList;

	private Map<String, NewBpi> bpisMap;

}
