package com.bpi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bpi.model.entity.BpiEntity;

/**
 * JPA 底層 Hibernate
 * 
 * @author Joe
 * 
 * @Date 2021/10/06
 */
@Repository
public interface BpiRepository extends JpaRepository<BpiEntity, String> {
	
	/**
	 * jpa使用jpql做curd語法時 是吃@Entity的名稱 如沒取名就是className 
	 * ex: Bpi是entity如果只有@Entity就是吃Bpi
	 * 如果是@Entity(name = "bpi")就是吃bpi
	 */
	
	// 使用 @Query 注釋 來做select  這個等於jpa寫好的findById
//	@Query("SELECT * FROM Bpi Where code = 1?")
	public BpiEntity findByCode(String code);
	
	// 使用 @Query(native = true, value = "原生sql語句") native = true 必須用原生sql語句
//	@Query("SELECT * FROM Bpi Where codeChineseName = 1?")
	public Optional<BpiEntity> findByCodeChineseName(String codeChineseName);
	
	// 使用 @Query 注釋 來做select
//	@Query("SELECT * FROM Bpi WHERE code = 1? AND codeChineseName=2?")
	public Optional<BpiEntity> findByCodeAndCodeChineseName(String code, String codeChineseName);
   
	// 使用 @Query 注釋 來做select :code要對應到@Param("code") 要mapping 不然會報錯
//	@Query("SELECT * FROM BpiEntity WHERE code = :code AND codeChineseName = :codeChineseName")
//	public Bpi findBpi(@Param("code") String code, @Param("codeChineseName") String codeChineseName);
	
	/**
	 * update 語句 jpa 用法
	 * 
	 * clearAutomatically 刷新Hibernate一級緩存 jpa 底層 Hibernate 實際上先存在快照區 
	 * flushAutomatically flush
	 * 
	 * @param code
	 * @return
	 */
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("UPDATE BpiEntity SET rate = :rate , rateFloat = :rateFloat, updated = :updated WHERE code = :code")
	@Transactional
	public int updateBpiRateByCode(@Param("rate") String rate, @Param("rateFloat") Double rateFloat , @Param("code") String code, @Param("updated") String updated);
	
	/**
	 * delete 語句 jpa 用法
	 * 
	 * @param code
	 * @return
	 */
	@Modifying
	@Query("DELETE FROM BpiEntity WHERE code = :code")
	@Transactional
	public int deleteBpiByCode(@Param("code") String code);
}
