package com.example.demo.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.BpiRsUtil;
import com.example.demo.common.CommonUtil;
import com.example.demo.common.ErrorCode;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.BpiRateRq;
import com.example.demo.model.BpiRq;
import com.example.demo.model.Coindesk;
import com.example.demo.model.NewBpi;
import com.example.demo.model.NewBpiRs;
import com.example.demo.model.entity.Bpi;
import com.example.demo.repository.BpiRepository;
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
	public ApiResponse<List<Bpi>> findAll() {
		List<Bpi> bpiList = bpiRepository.findAll();
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
	public ApiResponse<Bpi> findBpiByPk(String code) {
		Optional<Bpi> bpi = bpiRepository.findByCode(code);
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
	public ApiResponse<Bpi> findBpiByCodeChineseName(String codeChineseName) {
		Optional<Bpi> bpi = bpiRepository.findByCodeChineseName(codeChineseName);
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
	public ApiResponse<Bpi> findBpiByCodeAndCodeChineseName(String code, String codeChineseName) {
		Optional<Bpi> bpi = bpiRepository.findByCodeAndCodeChineseName(code, codeChineseName);
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
	public ApiResponse<Bpi> addBpi(BpiRq rq) {
		Optional<Bpi> bpi = bpiRepository.findById(rq.getCode());
		if (bpi.isPresent()) {
			return BpiRsUtil.getFailed(ErrorCode.INSERT_FAILED_PK_ONLY);
		}

		Bpi entity = dtoToEntity(rq);
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
	public ApiResponse<Bpi> updateBpi(BpiRq rq) {
		Optional<Bpi> bpi = bpiRepository.findById(rq.getCode());
		if (!bpi.isPresent()) {
			return BpiRsUtil.getFailed(ErrorCode.UPDATE_FAILED_DATA_NOT_EXIST);
		}

		Bpi entity = dtoToEntity(rq);
		entity.setRate(CommonUtil.fmtMicrometer(String.valueOf(rq.getRateFloat()))); // 千分位格式化
		entity.setUpdated(CommonUtil.getNowDate());
		return BpiRsUtil.getSuccess(bpiRepository.save(entity));
	}
	
	/**
	 * 修改匯率 by code
	 * 
	 * @param bpi
	 * @return
	 */
	public ApiResponse<Bpi> updateBpiRate(BpiRateRq rq) {
		Optional<Bpi> bpi = bpiRepository.findById(rq.getCode());
		if (!bpi.isPresent()) {
			return BpiRsUtil.getFailed(ErrorCode.UPDATE_FAILED_DATA_NOT_EXIST);
		}

		String rateStr = CommonUtil.fmtMicrometer(String.valueOf(rq.getRate()));
		bpiRepository.updateBpiRateByCode(rateStr, rq.getRate(), rq.getCode(), CommonUtil.getNowDate());
		return BpiRsUtil.getSuccess(bpiRepository.findById(rq.getCode()).orElse(new Bpi()));
	}
	
	/**
	 * Delete entity
	 * 
	 * @param entity
	 */
	public ApiResponse<Bpi> deleteBpi(String code) {
		Optional<Bpi> bpi = bpiRepository.findById(code);
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
	public ApiResponse<Bpi> deleteBpiByCode(String code) {
		Optional<Bpi> bpi = bpiRepository.findById(code);
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

		List<String> bpiCodeNames = getBpiCodeNames();
		
		// 轉成list
		List<NewBpi> bpisList = coindesk.getBpi().values().stream().map(b -> {
			bpiCodeNames.stream().filter(bcn -> bcn.equals(b.getCodeChineseName())).forEach(b::setCodeChineseName);
			return NewBpi.builder()
				.code(b.getCode())
				.codeChineseName(b.getCodeChineseName())
				.rate(b.getRate())
				.rateFloat(b.getRateFloat())
				.build();
		}).collect(Collectors.toList());
		
		// 轉成map
		Map<String, NewBpi> bpisMap = coindesk.getBpi().values().stream().map(b -> {
			bpiCodeNames.stream().filter(bcn -> bcn.equals(b.getCodeChineseName())).forEach(b::setCodeChineseName);
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
	private Bpi dtoToEntity(BpiRq rq) {
		return Bpi.builder()
			.code(rq.getCode())
			.codeChineseName(rq.getCodeChineseName())
			.description(rq.getDescription())
			.rateFloat(rq.getRateFloat())
			.symbol(rq.getSymbol())
			.build();
	}
	
	/**
	 * 取得 bpi 所有幣別中文名稱
	 * 
	 * @return
	 */
	private List<String> getBpiCodeNames() {
		return bpiRepository.findAll().stream().map(Bpi::getCodeChineseName).collect(Collectors.toList());
	}
	
}
