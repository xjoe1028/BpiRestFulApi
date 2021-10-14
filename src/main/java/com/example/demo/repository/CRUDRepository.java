package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Bpi;


/**
 * 
 * @author Joe
 * 
 * @Date 2021/10/06
 */
@Repository
public interface CRUDRepository extends JpaRepository<Bpi, Long> {
	
}
