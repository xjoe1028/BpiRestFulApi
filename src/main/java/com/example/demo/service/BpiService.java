package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.CommonUtil;
import com.example.demo.model.BpiRq;
import com.example.demo.model.BpiRs;
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
	public List<Bpi> findAll() {
		return bpiRepository.findAll();
	}

	/**
	 * select by code
	 * 
	 * @param code
	 * @return
	 */
	public Bpi findBpiByCode(String code) {
		return bpiRepository.findByCode(code);
	}
	
	/**
	 * select by codeChineseName
	 * 
	 * @param codeChineseName
	 * @return
	 */
	public Bpi findBpiByCodeChineseName(String codeChineseName) {
		return bpiRepository.findByCodeChineseName(codeChineseName);
	}
	
	/**
	 * 查詢 where code = ? and codeChineseName = ?
	 * 
	 * @param code
	 * @param codeChineseName
	 * @return
	 */
	public Bpi findByCodeAndCodeChineseName(String code, String codeChineseName) {
		return bpiRepository.findByCodeAndCodeChineseName(code, codeChineseName);
	}

	/**
	 * 新增
	 * 
	 * @param bpi
	 * @return
	 */
	public BpiRs addBpi(BpiRq rq) {
		Bpi entity = dtoToEntity(rq);
		entity.setRate(CommonUtil.fmtMicrometer(String.valueOf(rq.getRateFloat())));
		entity.setCreated(CommonUtil.getNowDate(new Date()));
		Bpi bpi = bpiRepository.save(entity);		
		return BpiRs.builder().bpi(bpi).message("新增成功").build();
	}

	/**
	 * 修改
	 * 
	 * @param bpi
	 * @return
	 */
	public BpiRs updateBpi(BpiRq rq) {
		Bpi entity = dtoToEntity(rq);
		entity.setRate(String.valueOf(rq.getRateFloat())); // 千分位格式化
		entity.setUpdated(CommonUtil.getNowDate(new Date()));
		entity = bpiRepository.save(entity);
		return BpiRs.builder().bpi(entity).message("修改成功").build();
	}
	
	/**
	 * 修改 匯率 by code
	 * 
	 * @param bpi
	 * @return
	 */
	public BpiRs updateBpiRate(BpiRq rq) {
		Bpi entity = bpiRepository.findByCode(rq.getCode());
		if(entity == null || rq.getRateFloat() == null) {
			return BpiRs.builder().message("更新失敗").build();
		}
		
		String rate = CommonUtil.fmtMicrometer(String.valueOf(rq.getRateFloat()));
		
		bpiRepository.updateBpiRateByCode(rate, rq.getRateFloat(), rq.getCode(), CommonUtil.getNowDate(new Date()));

		entity = bpiRepository.findByCode(rq.getCode());
		
		return BpiRs.builder().bpi(entity).message("更新成功")	.build();
	}

	/**
	 * 刪除 where code
	 * 
	 * @param code
	 * @return
	 */
	public BpiRs deleteBpiByCode(String code) {
		Bpi entity = bpiRepository.findByCode(code);
		if (entity == null) {
			return BpiRs.builder().message("刪除失敗").build();
		}
		bpiRepository.deleteBpiByCode(code);
		return BpiRs.builder().bpi(entity).message("刪除成功").build();
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
	 * rq transform entity
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
