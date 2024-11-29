package com.bpi.controller;

import java.util.List;

import com.bpi.feign.CoindeskFeign;
import com.bpi.util.BpiRsUtil;
import com.bpi.util.JsonUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bpi.model.rq.BpiRateRq;
import com.bpi.model.rq.BpiRq;
import com.bpi.model.rs.ApiResponse;
import com.bpi.model.rs.BpiRs;
import com.bpi.model.rs.Coindesk;
import com.bpi.model.rs.NewBpiRs;
import com.bpi.service.BpiService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Bpi Controller
 * 
 * @author Joe
 * 
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class BpiController implements BpiApi {

	final BpiService bpiService;

	final CoindeskFeign coindeskFeign;

	@Operation(summary = "查詢所有幣別")
	@Override
	public ApiResponse<List<BpiRs>> findAllBpis() {
		return bpiService.findAll();
	}

	@Operation(summary = "查詢單一幣別")
	@Override
	public ApiResponse<BpiRs> findBpiByPk(String code) {
		return bpiService.findBpiByPk(code);
	}

	@Operation(summary = "查詢單一幣別")
	@Override
	public ApiResponse<BpiRs> findBpiByCodeChineseName(String codeChineseName) {
		return bpiService.findBpiByCodeChineseName(codeChineseName);
	}

	@Operation(summary = "新增幣別")
	@Override
	public ApiResponse<BpiRs> addBpi(BpiRq rq) {
		return bpiService.addBpi(rq);
	}

	/**
	 * 修改 Bpi
	 * PUT: 替換資源
	 */
	@Operation(summary = "修改幣別")
	@Override
	public ApiResponse<BpiRs> updateBpi(BpiRq rq) {
		return bpiService.updateBpi(rq);
	}

	/**
	 * 修改 Bpi 匯率
	 * PATCH: 更新資源部份內容
	 */
	@Operation(summary = "修改幣別匯率")
	@Override
	public ApiResponse<BpiRs> updateBpiRate(BpiRateRq rq) {
		return bpiService.updateBpiRate(rq);
	}

	@Operation(summary = "刪除幣別")
	@Override
	public ApiResponse<BpiRs> deleteBpi(BpiRq rq) {
		return bpiService.deleteBpiByCode(rq.getCode());
	}

	@Operation(summary = "呼叫外部coindesk API")
	@Override
	public ApiResponse<Coindesk> callCoindeskAPI() {
		Coindesk coindesk = JsonUtils.getObject(coindeskFeign.getCurrentPrice(), Coindesk.class);
		log.info("call coindesk api res : {}", coindesk);
		return BpiRsUtil.getSuccess(coindesk);
	}

	@Operation(summary = "呼叫外部coindesk API 後進行資料處理 return")
	@Override
	public ApiResponse<NewBpiRs> transformNewBpi() {
		String jsonStr = coindeskFeign.getCurrentPrice();
		log.info("call coindesk api res : {}", jsonStr);
		return BpiRsUtil.getSuccess(bpiService.transform(jsonStr));
	}

}
