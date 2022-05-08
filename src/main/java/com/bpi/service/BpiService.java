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

import com.bpi.common.BpiRsUtil;
import com.bpi.common.CommonUtil;
import com.bpi.common.ErrorCode;
import com.bpi.model.ApiResponse;
import com.bpi.model.BpiRateRq;
import com.bpi.model.BpiRq;
import com.bpi.model.Coindesk;
import com.bpi.model.NewBpi;
import com.bpi.model.NewBpiRs;
import com.bpi.model.entity.BpiEntity;
import com.bpi.repository.BpiRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	@Autowired
	private BpiRepository bpiRepository;

	/**
	 * select all
	 * 
	 * @return
	 */
	public ApiResponse<List<BpiEntity>> findAll() {
		List<BpiEntity> bpiList = bpiRepository.findAll();
		if (bpiList.isEmpty()) {
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);
		}

		return BpiRsUtil.getSuccess(bpiList);
	}

	/**
	 * select by code(pk)
	 * 
	 * @param code
	 * @return
	 */
	public ApiResponse<BpiEntity> findBpiByPk(String code) {
		Optional<BpiEntity> bpi = bpiRepository.findById(code);
		if (!bpi.isPresent()) {
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);
		}

		return BpiRsUtil.getSuccess(bpi.get());
	}
	
	/**
	 * select by codeChineseName
	 * 
	 * @param codeChineseName
	 * @return
	 */
	public ApiResponse<BpiEntity> findBpiByCodeChineseName(String codeChineseName) {
		Optional<BpiEntity> bpi = bpiRepository.findByCodeChineseName(codeChineseName);
		if (!bpi.isPresent()) {
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);
		}

		return BpiRsUtil.getSuccess(bpi.get());
	}
	
	/**
	 * 查詢 where code = ? and codeChineseName = ?
	 * 
	 * @param code
	 * @param codeChineseName
	 * @return
	 */
	public ApiResponse<BpiEntity> findBpiByCodeAndCodeChineseName(String code, String codeChineseName) {
		Optional<BpiEntity> bpi = bpiRepository.findByCodeAndCodeChineseName(code, codeChineseName);
		if (!bpi.isPresent()) {
			return BpiRsUtil.getFailed(ErrorCode.SELECT_EMPTY);
		}

		return BpiRsUtil.getSuccess(bpi.get());
	}

	/**
	 * 新增
	 * 
	 * @param bpi
	 * @return
	 */
	public ApiResponse<BpiEntity> addBpi(BpiRq rq) {
		Optional<BpiEntity> bpi = bpiRepository.findById(rq.getCode());
		if (bpi.isPresent()) {
			return BpiRsUtil.getFailed(ErrorCode.INSERT_FAILED_PK_ONLY);
		}

		BpiEntity entity = dtoToEntity(rq);
		entity.setRate(CommonUtil.fmtMicrometer(String.valueOf(rq.getRateFloat()))); // 千分位格式化
		entity.setCreated(CommonUtil.getNowDate());
		return BpiRsUtil.getSuccess(bpiRepository.save(entity));
	}

	/**
	 * 修改
	 * 
	 * @param bpi
	 * @return
	 */
	public ApiResponse<BpiEntity> updateBpi(BpiRq rq) {
		Optional<BpiEntity> oldBpi = bpiRepository.findById(rq.getOldCode());
		if (!oldBpi.isPresent()) {
			log.info("原幣別資料不存在，直接做新增");
			return addBpi(rq);
		} else {
			rq.setRate(CommonUtil.fmtMicrometer(String.valueOf(rq.getRateFloat()))); // 千分位格式化
			rq.setCreated(oldBpi.get().getCreated());
			rq.setUpdated(CommonUtil.getNowDate());
			bpiRepository.updateBpi(rq);
			return BpiRsUtil.getSuccess(bpiRepository.getById(rq.getCode()));
		}
	}
	
	/**
	 * 修改匯率 by code
	 * 
	 * @param bpi
	 * @return
	 */
	public ApiResponse<BpiEntity> updateBpiRate(BpiRateRq rq) {
		Optional<BpiEntity> bpi = bpiRepository.findById(rq.getCode());
		if (!bpi.isPresent()) {
			return BpiRsUtil.getFailed(ErrorCode.UPDATE_FAILED_PK_ONLY);
		}

		String rateStr = CommonUtil.fmtMicrometer(String.valueOf(rq.getRate()));
		bpiRepository.updateBpiRateByCode(rateStr, rq.getRate(), rq.getCode(), CommonUtil.getNowDate());
		return BpiRsUtil.getSuccess(bpiRepository.findByCode(rq.getCode()));
	}
	
	/**
	 * Delete entity
	 * 
	 * @param entity
	 */
	public ApiResponse<BpiEntity> deleteBpi(String code) {
		Optional<BpiEntity> bpi = bpiRepository.findById(code);
		if (!bpi.isPresent()) {
			return BpiRsUtil.getFailed(ErrorCode.DELETE_FAILED_DATA_NOT_EXIST);
		}

		bpiRepository.delete(bpi.get());
		return BpiRsUtil.getSuccess(bpi.get());
	}

	/**
	 * 刪除 by code
	 * 
	 * @param code
	 * @return
	 */
	public ApiResponse<BpiEntity> deleteBpiByCode(String code) {
		Optional<BpiEntity> bpi = bpiRepository.findById(code);
		if (!bpi.isPresent()) {
			return BpiRsUtil.getFailed(ErrorCode.DELETE_FAILED_DATA_NOT_EXIST);
		}

		bpiRepository.deleteBpiByCode(code);
		return BpiRsUtil.getSuccess(bpi.get());
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
		ObjectMapper mapper = new ObjectMapper();
		Coindesk coindesk = mapper.readValue(jsonStr, Coindesk.class);
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
		
		return NewBpiRs.builder().bpisList(bpisList).bpisMap(bpisMap)
				.updated(CommonUtil.updatedFormat(coindesk.getTime().getUpdatedISO().substring(0,19))).build();
	}
	
	/**
	 * data transaction object (dto) transform entity
	 * 
	 * @param rq
	 * @return
	 */
	private BpiEntity dtoToEntity(BpiRq rq) {
		return BpiEntity.builder()
			.code(rq.getCode())
			.codeChineseName(rq.getCodeChineseName())
			.description(rq.getDescription())
			.rateFloat(rq.getRateFloat())
			.symbol(rq.getSymbol())
			.build();
	}
	
}
