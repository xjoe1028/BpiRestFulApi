package com.bpi.model.rs;

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
public class NewBpiRs {

	String updated;

	List<NewBpi> bpisList;

	Map<String, NewBpi> bpisMap;

}
