package com.bpi.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bpi.model.RqType;
import com.bpi.model.rq.BpiRateRq;
import com.bpi.model.rq.BpiRq;
import com.bpi.model.rs.ApiResponse;
import com.bpi.model.rs.BpiRs;
import com.bpi.model.rs.Coindesk;
import com.bpi.model.rs.NewBpiRs;
import com.bpi.service.BpiService;
import com.bpi.util.BpiRsUtil;
import com.bpi.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Bpi Controller
 * 
 * @author Joe
 * 
 */
@Slf4j
@Tag(name = "幣別", description = "幣別 RestFul API Controller")
@CrossOrigin(origins = "*", allowedHeaders = "*") // 跨域的問題
@RequestMapping(value = "/api/bpi", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class BpiController {

	public static final String COINDESK_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

	final BpiService bpiService;

	final RestTemplate restTemplate;

	/**
	 * select All
	 * 
	 * @return
	 */
	@Operation(summary = "查詢所有幣別")
	@GetMapping("/findAllBpis")
	public ApiResponse<List<BpiRs>> findAllBpis() {
		return bpiService.findAll();
	}

	/**
	 * 查詢 Bpi by code
	 * 
	 * @param code
	 * @return
	 */
	@Operation(summary = "查詢單一幣別")
	@GetMapping("/findBpi/code")
	public ApiResponse<BpiRs> findBpiByPk(
		@Parameter(name = "code", description = "英文幣別", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = String.class))
		@RequestParam(name = "code", defaultValue = "") String code) {
		return bpiService.findBpiByPk(code);
	}

	/**
	 * 查詢 Bpi by codeChineseName
	 * 
	 * @param codeChineseName
	 * @return
	 */
	@Operation(summary = "查詢單一幣別")
	@GetMapping("/findBpi/codeChineseName")
	public ApiResponse<BpiRs> findBpiByCodeChineseName(
		@Parameter(name = "codeChineseName", description = "中文幣別", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = String.class))
		@RequestParam(name = "codeChineseName", defaultValue = "") String codeChineseName) {
		return bpiService.findBpiByCodeChineseName(codeChineseName);
	}

	/**
	 * 新增 Bpi
	 * 
	 * @param bpi
	 * @return
	 */
	@Operation(summary = "新增幣別")
	@RqType(BpiRq.class)
	@PostMapping("/addBpi")
	public ApiResponse<BpiRs> addBpi(@RequestBody BpiRq rq) {
		return bpiService.addBpi(rq);
	}

	/**
	 * 修改 Bpi
	 * 
	 * PUT: 替換資源
	 * 
	 * @param rq
	 * @return
	 */
	@Operation(summary = "修改幣別")
	@RqType(BpiRq.class)
	@PutMapping("/updateBpi")
	public ApiResponse<BpiRs> updateBpi(@RequestBody BpiRq rq) {
		return bpiService.updateBpi(rq);
	}

	/**
	 * 修改 Bpi 匯率
	 * 
	 * PATCH: 更新資源部份內容
	 * 
	 * @param rq
	 * @return
	 */
	@Operation(summary = "修改幣別匯率")
	@RqType(BpiRateRq.class)
	@PatchMapping("/updateBpiRate")
	public ApiResponse<BpiRs> updateBpiRate(@RequestBody BpiRateRq rq) {
		return bpiService.updateBpiRate(rq);
	}

	/**
	 * 刪除 Bpi by code
	 * 
	 * @param rq
	 * @return
	 */
	@Operation(summary = "刪除幣別")
	@RqType(BpiRq.class)
	@DeleteMapping("/deleteBpi/code")
	public ApiResponse<BpiRs> deleteBpi(@RequestBody BpiRq rq) {
		return bpiService.deleteBpiByCode(rq.getCode());
	}

	/**
	 * 呼叫 coindesk API
	 * 
	 * @return
	 */
	@Operation(summary = "呼叫外部coindesk API")
	@GetMapping("/call/coindesk")
	public ApiResponse<Coindesk> callCoindeskAPI() {
		Coindesk coindesk = JsonUtils.getObject(restTemplate.getForObject(COINDESK_URL, String.class), Coindesk.class);
		log.info("call coindesk api res : {}", coindesk);
		return BpiRsUtil.getSuccess(coindesk);
	}

	/**
	 * 呼叫 coindesk API 在 format成自定義的資料 return
	 * 
	 * @return
	 * @throws ParseException
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Operation(summary = "呼叫外部coindesk API 後進行資料處理 return")
	@GetMapping("/call/coindesk/transform")
	public ApiResponse<NewBpiRs> transformNewBpi() {
		String jsonStr = restTemplate.getForObject(COINDESK_URL, String.class);
		log.info("call coindesk api res : {}", jsonStr);
		return BpiRsUtil.getSuccess(bpiService.transform(jsonStr));
	}

}
