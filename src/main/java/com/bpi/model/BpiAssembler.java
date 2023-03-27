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
// @Mapper 指定生成的映射器應遵循的组件模型，取值有：default、cdi、spring、jsr330和jakarta
// 說人話就是——在生成(編譯)的類上會加上相應的注解。
@Mapper(componentModel = "spring")
public interface BpiAssembler {
	
	BpiEntity toEntity(BpiRq rq);
	
	BpiRs entityToRs(BpiEntity entity);
	
	List<BpiRs> entityListToListRs(List<BpiEntity> entitys);
}
