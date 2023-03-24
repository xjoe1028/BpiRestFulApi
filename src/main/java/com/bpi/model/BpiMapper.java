package com.bpi.model;

import java.util.List;

import org.mapstruct.Mapper;

import com.bpi.model.entity.BpiEntity;

/**
 * map struct mapper 
 * 使用 mapStruct 來做 dto To Entity or entity To dto
 * 
 * @author Joe
 *
 * @Date 2023/03/24
 */
@Mapper(componentModel = "spring")
public interface BpiMapper {
	
	BpiEntity toEntity(BpiRq rq);
	
	BpiRs entityToRs(BpiEntity entity);
	
	List<BpiRs> entityListToListRs(List<BpiEntity> entitys);
}
