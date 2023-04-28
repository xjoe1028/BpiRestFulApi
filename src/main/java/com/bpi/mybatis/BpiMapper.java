package com.bpi.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bpi.model.BpiForMyBatis;
import com.bpi.model.entity.BpiEntity;

/**
 * Bpi Mapper
 * 
 * @author Joe
 * 
 */
@Repository
public interface BpiMapper {

	public List<BpiEntity> findAll();
	
	public BpiEntity findByCode(String code);
	
	public void addBpi(BpiEntity entity);
	
	public Long updateByCode(BpiForMyBatis rq);
	
	public Long deleteByCode(String code);
	
}
