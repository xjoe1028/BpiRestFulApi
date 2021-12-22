package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Bpi;

/**
 * JPA 底層 Hibernate
 * 
 * @author Joe
 * 
 * @Date 2021/10/06
 */
@Repository
public interface CRUDRepository extends JpaRepository<Bpi, Long> {

	// 使用 @Query 注釋 來做select
//	@Query(value = "SELECT * FROM bpi WHERE code = 1? AND codeChineseName=2?" , nativeQuery = true )
	public Bpi findByCodeAndCodeChineseName(String code, String codeChineseName);
   
	// 使用 @Query 注釋 來做select :code要對應到@Param("code") 要mapping 不然會報錯
	@Query(value = "SELECT * FROM bpi WHERE code = :code AND codeChineseName = :codeChineseName" , nativeQuery = true )
	public Bpi findBpi(@Param("code") String code, @Param("codeChineseName") String codeChineseName);
	
	/**
	 * update 語句 jpa 用法
	 * 
	 * @param code
	 * @return
	 */
	@Modifying
	@Query(value = "UPDATE bpi SET code = :code WHERE code = :code", nativeQuery = true)
	@Transactional
	public int updateBpiCodeByCode(@Param("code") String code);
	
	/**
	 * delete 語句 jpa 用法
	 * 
	 * @param code
	 * @return
	 */
	@Modifying
	@Query(value = "DELETE FROM bpi WHERE code = :code", nativeQuery = true)
	@Transactional
	public int deleteBpiByCode(@Param("code") String code);
}
