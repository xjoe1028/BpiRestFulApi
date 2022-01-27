package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.demo.common.BpictlUtil;
import com.example.demo.common.CommonUtil;
import com.example.demo.common.ErrorCode;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.BpiRateRq;
import com.example.demo.model.BpiRq;
import com.example.demo.model.Coindesk;
import com.example.demo.model.NewBpi;
import com.example.demo.model.entity.Bpi;
import com.example.demo.repository.BpiRepository;
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

	public final static Map<String, String> coinNameMap = new HashMap<>();

	static {
		coinNameMap.put("USD", "美元");
		coinNameMap.put("GBP", "英镑");
		coinNameMap.put("EUR", "歐元");
		coinNameMap.put("TWD", "新台幣");
	}

	/**
	 * select all
	 * 
	 * @return
	 */
	public ApiResponse<List<Bpi>> findAll() {
		List<Bpi> bpiList = bpiRepository.findAll();
		if(CollectionUtils.isEmpty(bpiList)) {
			return BpictlUtil.getFailed(ErrorCode.SELECT_EMPTY);
		}
		
		return BpictlUtil.getSuccess(bpiList);
	}

	/**
	 * select by code
	 * 
	 * @param code
	 * @return
	 */
	public ApiResponse<Bpi> findBpiByCode(String code) {
		Bpi bpi = bpiRepository.findByCode(code);
		if(bpi == null) { 
			return BpictlUtil.getFailed(ErrorCode.SELECT_EMPTY);
		}
		
		return BpictlUtil.getSuccess(bpi);
	}
	
	/**
	 * select by codeChineseName
	 * 
	 * @param codeChineseName
	 * @return
	 */
	public ApiResponse<Bpi> findBpiByCodeChineseName(String codeChineseName) {
		Bpi bpi = bpiRepository.findByCodeChineseName(codeChineseName);
		if(bpi == null) {
			return BpictlUtil.getFailed(ErrorCode.SELECT_EMPTY);
		}
		
		return BpictlUtil.getSuccess(bpi);
	}
	
	/**
	 * 查詢 where code = ? and codeChineseName = ?
	 * 
	 * @param code
	 * @param codeChineseName
	 * @return
	 */
	public ApiResponse<Bpi> findByPk(String code, String codeChineseName) {
		Bpi bpi = bpiRepository.findByCodeAndCodeChineseName(code, codeChineseName);
		if(bpi == null) {
			return BpictlUtil.getFailed(ErrorCode.SELECT_EMPTY);
		}
		
		return BpictlUtil.getSuccess(bpi);
	}

	/**
	 * 新增
	 * 
	 * @param bpi
	 * @return
	 */
	public ApiResponse<Bpi> addBpi(BpiRq rq) {
		Bpi bpi = bpiRepository.findByCodeAndCodeChineseName(rq.getCode(), rq.getCodeChineseName());
		if(bpi != null) {
			return BpictlUtil.getFailed(ErrorCode.INSERT_FAILED_PK_ONLY);
		}
		
		Bpi entity = dtoToEntity(rq);
		entity.setRate(CommonUtil.fmtMicrometer(String.valueOf(rq.getRateFloat()))); // 千分位格式化
		entity.setCreated(CommonUtil.getNowDate());
		return BpictlUtil.getSuccess(bpiRepository.save(entity));
	}

	/**
	 * 修改
	 * 
	 * @param bpi
	 * @return
	 */
	public ApiResponse<Bpi> updateBpi(BpiRq rq) {
		Bpi bpi = bpiRepository.findByCodeAndCodeChineseName(rq.getCode(), rq.getCodeChineseName());
		if(bpi == null) {
			return BpictlUtil.getFailed(ErrorCode.UPDATE_FAILED_DATA_NOT_EXIST);
		}
		
		Bpi entity = dtoToEntity(rq);
		entity.setRate(CommonUtil.fmtMicrometer(String.valueOf(rq.getRateFloat()))); // 千分位格式化
		entity.setUpdated(CommonUtil.getNowDate());
		return BpictlUtil.getSuccess(bpiRepository.save(entity));
	}
	
	/**
	 * 修改匯率 by code
	 * 
	 * @param bpi
	 * @return
	 */
	public ApiResponse<Bpi> updateBpiRate(BpiRateRq rq) {
		Bpi bpi = bpiRepository.findByCode(rq.getCode());
		if(bpi == null) {
			return BpictlUtil.getFailed(ErrorCode.UPDATE_FAILED_DATA_NOT_EXIST);
		}
		
		String rateStr = CommonUtil.fmtMicrometer(String.valueOf(rq.getRate()));
		bpiRepository.updateBpiRateByCode(rateStr, rq.getRate(), rq.getCode(), CommonUtil.getNowDate());
		return BpictlUtil.getSuccess(bpiRepository.findByCode(rq.getCode()));
	}
	
	/**
	 * Delete entity
	 * 
	 * @param entity
	 */
	public ApiResponse<Bpi> deleteBpi(String code) {
		Bpi bpi = bpiRepository.findByCode(code);
		if(bpi == null) {
			return BpictlUtil.getFailed(ErrorCode.DELETE_FAILED_DATA_NOT_EXIST);
		}
		
		bpiRepository.delete(bpi);
		return BpictlUtil.getSuccess(bpi);
	}

	/**
	 * 刪除 by code
	 * 
	 * @param code
	 * @return
	 */
	public ApiResponse<Bpi> deleteBpiByCode(String code) {
		Bpi bpi = bpiRepository.findByCode(code);
		if(bpi == null) {
			return BpictlUtil.getFailed(ErrorCode.DELETE_FAILED_DATA_NOT_EXIST);
		}
		
		bpiRepository.deleteBpiByCode(code);
		return BpictlUtil.getSuccess(bpi);
	}

	public NewBpi transform(String jsonStr) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Coindesk coindesk = mapper.readValue(jsonStr, Coindesk.class);
		log.info("coindesk: {}", coindesk);

		Map<String, Bpi> map = new HashMap<>();
		NewBpi newBpi = new NewBpi();
		coindesk.getBpi().forEach((k, v) -> {
			v.setCodeChineseName(coinNameMap.get(k));
			map.put(k, v);
		});
		
		newBpi.setUpdated(CommonUtil.updatedFormat(coindesk.getTime().getUpdatedISO().substring(0,19)));
		newBpi.setNewBpi(map);
		return newBpi;
	}
	
	/**
	 * data transaction object (dto) transform entity
	 * 
	 * @param rq
	 * @return
	 */
	private Bpi dtoToEntity(BpiRq rq) {
		return Bpi.builder()
			.code(rq.getCode())
			.codeChineseName(rq.getCodeChineseName())
			.description(rq.getDescription())
			.rateFloat(rq.getRateFloat())
			.symbol(rq.getSymbol())
			.build();
	}
	
}
