package com.bpi.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bpi.cconstant.CacheKeys;
import com.bpi.cconstant.ErrorCode;
import com.bpi.common.BpiRsUtil;
import com.bpi.common.CommonUtil;
import com.bpi.common.JsonUtils;
import com.bpi.common.RedisUtils;
import com.bpi.model.ApiResponse;
import com.bpi.model.BpiMapper;
import com.bpi.model.BpiRateRq;
import com.bpi.model.BpiRq;
import com.bpi.model.BpiRs;
import com.bpi.model.Coindesk;
import com.bpi.model.NewBpi;
import com.bpi.model.NewBpiRs;
import com.bpi.model.entity.BpiEntity;
import com.bpi.repository.BpiRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Joe
 * 
 * @Date 2021/10/06
 */
@Slf4j
@Service
public class BpiService {

	// JPA
	@Autowired
	private BpiRepository bpiRepository;
	
	// mapStruct
	@Autowired
	private BpiMapper bpiMapper;
	
	@Autowired
	private RedisUtils redisUtils;
	
	// 如db有更新就要先去查db存入redis，反之
	private boolean dbUpdatedFlag = false;
	
	/**
	 * select all
	 * 
	 * @return
	 */
	public ApiResponse<List<BpiRs>> findAll() {
		String key = CacheKeys.getCacheName(CacheKeys.BPIS_CACHE);
		List<BpiEntity> bpiList;
		// 判斷快取是否存在，存在則去撈redis，不存在則去查db
		if (redisUtils.exists(key) && !dbUpdatedFlag) {
			log.info("撈取redis");
			bpiList = JsonUtils.getListObject(redisUtils.get(key));
		} else {
			log.info("撈取database");
			bpiList = bpiRepository.findAll();
			
			if (bpiList.isEmpty()) 
				return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);
			
			redisUtils.set(key, JsonUtils.getJson(bpiList), RedisUtils.ONE_DAY);
			dbUpdatedFlag = false; // 查完存入redis，flag又改回false
		}
		
		return BpiRsUtil.getSuccess(bpiMapper.entityListToListRs(bpiList));
	}

	/**
	 * select by code(pk)
	 * 
	 * @param code
	 * @return
	 */
	public ApiResponse<BpiRs> findBpiByPk(String code) {
		Optional<BpiEntity> bpi = bpiRepository.findById(code);
		
		if (!bpi.isPresent()) 
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);
		
		return BpiRsUtil.getSuccess(bpiMapper.entityToRs(bpi.get()));
	}
	
	/**
	 * select by codeChineseName
	 * 
	 * @param codeChineseName
	 * @return
	 */
	public ApiResponse<BpiRs> findBpiByCodeChineseName(String codeChineseName) {
		Optional<BpiEntity> bpi = bpiRepository.findByCodeChineseName(codeChineseName);
		
		if (!bpi.isPresent())
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);

		return BpiRsUtil.getSuccess(bpiMapper.entityToRs(bpi.get()));
	}
	
	/**
	 * 查詢 where code = ? and codeChineseName = ?
	 * 
	 * @param code
	 * @param codeChineseName
	 * @return
	 */
	public ApiResponse<BpiRs> findBpiByCodeAndCodeChineseName(String code, String codeChineseName) {
		Optional<BpiEntity> bpi = bpiRepository.findByCodeAndCodeChineseName(code, codeChineseName);
		
		if (!bpi.isPresent()) 
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);

		return BpiRsUtil.getSuccess(bpiMapper.entityToRs(bpi.get()));
	}

	/**
	 * 新增
	 * 
	 * @param bpi
	 * @return
	 */
	public ApiResponse<BpiRs> addBpi(BpiRq rq) {
		Optional<BpiEntity> bpi = bpiRepository.findById(rq.getCode());
		
		if (bpi.isPresent()) 
			return BpiRsUtil.getFailed(ErrorCode.INSERT_FAILED_PK_ONLY);

		BpiEntity entity = bpiMapper.toEntity(rq);
		entity.setRate(CommonUtil.fmtMicrometer(String.valueOf(rq.getRateFloat()))); // 千分位格式化
		entity.setCreated(CommonUtil.getNowDate());
		return BpiRsUtil.getSuccess(bpiMapper.entityToRs(bpiRepository.save(entity)));
	}

	/**
	 * 修改
	 * 
	 * @param bpi
	 * @return
	 */
	public ApiResponse<BpiRs> updateBpi(BpiRq rq) {
		Optional<BpiEntity> oldBpi = bpiRepository.findById(rq.getOldCode());
		BpiEntity entity = bpiMapper.toEntity(rq);
		entity.setRate(CommonUtil.fmtMicrometer(String.valueOf(rq.getRateFloat()))); // 千分位格式化
		if (!oldBpi.isPresent()) {
			log.info("原幣別資料不存在，直接做新增");
			entity.setCreated(CommonUtil.getNowDate());
			return BpiRsUtil.getSuccess(bpiMapper.entityToRs(bpiRepository.save(entity)));
		} else {
			log.info("原幣別資料已存在，直接做修改");
			entity.setUpdated(CommonUtil.getNowDate());
			entity.setCreated(oldBpi.get().getCreated());
			bpiRepository.updateBpi(entity, rq.getOldCode());
			return BpiRsUtil.getSuccess(bpiMapper.entityToRs(bpiRepository.getById(rq.getCode())));
		}
	}
	
	/**
	 * 修改匯率 by code
	 * 
	 * @param bpi
	 * @return
	 */
	public ApiResponse<BpiRs> updateBpiRate(BpiRateRq rq) {
		Optional<BpiEntity> bpi = bpiRepository.findById(rq.getCode());

		if (!bpi.isPresent())
			return BpiRsUtil.getFailed(ErrorCode.UPDATE_FAILED_PK_ONLY);

		String rateStr = CommonUtil.fmtMicrometer(String.valueOf(rq.getRate()));
		bpiRepository.updateBpiRateByCode(rateStr, rq.getRate(), rq.getCode(), CommonUtil.getNowDate());
		return BpiRsUtil.getSuccess(bpiMapper.entityToRs(bpiRepository.findByCode(rq.getCode())));
	}
	
	/**
	 * Delete entity
	 * 
	 * @param entity
	 */
	public ApiResponse<BpiRs> deleteBpi(String code) {
		Optional<BpiEntity> bpi = bpiRepository.findById(code);

		if (!bpi.isPresent())
			return BpiRsUtil.getFailed(ErrorCode.DELETE_FAILED_DATA_NOT_EXIST);

		bpiRepository.delete(bpi.get());
		return BpiRsUtil.getSuccess(bpiMapper.entityToRs(bpi.get()));
	}

	/**
	 * 刪除 by code
	 * 
	 * @param code
	 * @return
	 */
	public ApiResponse<BpiRs> deleteBpiByCode(String code) {
		Optional<BpiEntity> bpi = bpiRepository.findById(code);
		
		if (!bpi.isPresent())
			return BpiRsUtil.getFailed(ErrorCode.DELETE_FAILED_DATA_NOT_EXIST);

		bpiRepository.deleteBpiByCode(code);
		return BpiRsUtil.getSuccess(bpiMapper.entityToRs(bpi.get()));
	}

	/**
	 * 呼叫 url 後 return 更新時間,幣別,幣別中文名稱,利率
	 * 
	 * @param jsonStr
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws ParseException
	 */
	public NewBpiRs transform(String jsonStr) throws JsonProcessingException, ParseException {
		Coindesk coindesk = JsonUtils.jsonToObject(jsonStr, Coindesk.class);
		log.info("coindesk: {}", coindesk);

		List<BpiEntity> allBpis = Optional.ofNullable(bpiRepository.findAll()).orElseGet(ArrayList::new);
		
		// 轉成list
		List<NewBpi> bpisList = coindesk.getBpi().values().stream().map(b -> {
			allBpis.stream().filter(ab -> ab.getCode().equals(b.getCode())).forEach(ab -> b.setCodeChineseName(ab.getCodeChineseName()));
			return NewBpi.builder()
				.code(b.getCode())
				.codeChineseName(b.getCodeChineseName())
				.rate(b.getRate())
				.rateFloat(b.getRateFloat())
				.build();
		}).collect(Collectors.toList());
		// 轉成map
		Map<String, NewBpi> bpisMap = coindesk.getBpi().values().stream().map(b -> {
			allBpis.stream().filter(ab -> ab.getCode().equals(b.getCode())).forEach(ab -> b.setCodeChineseName(ab.getCodeChineseName()));
			return NewBpi.builder()
				.code(b.getCode())
				.codeChineseName(b.getCodeChineseName())
				.rate(b.getRate())
				.rateFloat(b.getRateFloat())
				.build();
		}).collect(Collectors.toMap(NewBpi::getCode, Function.identity(), (v1, v2) -> v2));
		
		log.info("bpiList: {}", bpisList);
		log.info("bpiMap: {}", bpisMap);
		
		return NewBpiRs.builder()
			.bpisList(bpisList)
			.bpisMap(bpisMap)
			.updated(CommonUtil.updatedFormat(coindesk.getTime().getUpdatedISO().substring(0,19)))
			.build();
	}
	
//	/**
//	 * data transaction object (dto) transform entity
//	 * 
//	 * @param rq
//	 * @return
//	 */
//	private BpiEntity dtoToEntity(BpiRq rq) {
//		return BpiEntity.builder()
//			.code(rq.getCode())
//			.codeChineseName(rq.getCodeChineseName())
//			.description(rq.getDescription())
//			.rateFloat(rq.getRateFloat())
//			.symbol(rq.getSymbol())
//			.build();
//	}
//	
//	private BpiForMyBatis dtoToMyBatisDto(BpiRq rq) {
//		return BpiForMyBatis.builder()
//			.code(rq.getCode())
//			.codeChineseName(rq.getCodeChineseName())
//			.description(rq.getDescription())
//			.rateFloat(rq.getRateFloat())
//			.symbol(rq.getSymbol())
//			.created(rq.getCreated())
//			.updated(rq.getUpdated())
//			.rate(rq.getRate())
//			.oldCode(rq.getOldCode())
//			.build();
//	}
	
}
