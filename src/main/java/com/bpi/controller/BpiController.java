package com.bpi.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bpi.common.BpiRsUtil;
import com.bpi.model.ApiResponse;
import com.bpi.model.BpiRateRq;
import com.bpi.model.BpiRq;
import com.bpi.model.NewBpiRs;
import com.bpi.model.RqType;
import com.bpi.model.entity.BpiEntity;
import com.bpi.service.BpiService;
import com.fasterxml.jackson.core.JsonProcessingException;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Joe
 * 
 * @Date 2021/10/06
 */
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*") // 跨域的問題
@RestController
@RequestMapping(value = "/api/bpi", produces = MediaType.APPLICATION_JSON_VALUE)
public class BpiController {

	public static final String COINDESK_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

	@Autowired
	private BpiService bpiService;

	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * select All
	 * 
	 * @param bpi
	 * @return
	 */
	@ApiOperation(value = "查詢所有幣別")
	@GetMapping("/findAllBpis")
	public ApiResponse<List<BpiEntity>> findAllBpis() {
		return bpiService.findAll();
	}

	/**
	 * 查詢 Bpi by code
	 * 
	 * @param code
	 * @return
	 */
	@ApiOperation(value = "查詢單一幣別")
	@ApiImplicitParam(paramType = "query", name = "code", value = "英文幣別", required = true, dataType = "String")
	@GetMapping("/findBpi/code")
	public ApiResponse<BpiEntity> findBpiByPk(@RequestParam(name = "code", defaultValue = "") String code) {
		return bpiService.findBpiByPk(code);
	}

	/**
	 * 查詢 Bpi by codeChineseName
	 * 
	 * @param code
	 * @return
	 */
	@ApiOperation(value = "查詢單一幣別")
	@ApiImplicitParam(paramType = "query", name = "codeChineseName", value = "中文幣別", required = true, dataType = "String")
	@GetMapping("/findBpi/codeChineseName")
	public ApiResponse<BpiEntity> findBpiByCodeChineseName(@RequestParam(name = "codeChineseName", defaultValue = "") String codeChineseName) {
		return bpiService.findBpiByCodeChineseName(codeChineseName);
	}

	/**
	 * 新增 Bpi
	 * 
	 * @param bpi
	 * @return
	 */
	@ApiOperation(value = "新增幣別")
	@RqType(BpiRq.class)
	@PostMapping("/addBpi")
	public ApiResponse<BpiEntity> addBpi(@RequestBody BpiRq rq) {
		return bpiService.addBpi(rq);
	}

	/**
	 * 修改 Bpi
	 * 
	 * PUT: 替換資源
	 * 
	 * @param bpi
	 * @return
	 */
	@ApiOperation(value = "修改幣別")
	@RqType(BpiRq.class)
	@PutMapping("/updateBpi")
	public ApiResponse<BpiEntity> updateBpi(@RequestBody BpiRq rq) {
		return bpiService.updateBpi(rq);
	}

	/**
	 * 修改 Bpi 匯率
	 * 
	 * PATCH: 更新資源部份內容
	 * 
	 * @param bpi
	 * @return
	 */
	@ApiOperation(value = "修改幣別匯率")
	@RqType(BpiRateRq.class)
	@PatchMapping("/updateBpiRate")
	public ApiResponse<BpiEntity> updateBpiRate(@RequestBody BpiRateRq rq) {
		return bpiService.updateBpiRate(rq);
	}

	/**
	 * 刪除 Bpi by code
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "刪除幣別")
	@RqType(BpiRq.class)
	@DeleteMapping("/deleteBpi/code")
	public ApiResponse<BpiEntity> deleteBpi(@RequestBody BpiRq rq) {
		return bpiService.deleteBpiByCode(rq.getCode());
	}

	/**
	 * 呼叫 coindesk API
	 * 
	 * @return
	 */
	@ApiOperation(value = "呼叫外部coindesk API")
	@GetMapping("/call/coindesk")
	public ApiResponse<String> callCoindeskAPI() {
		String response = restTemplate.getForObject(COINDESK_URL, String.class);
		log.info("call coindesk api res : {}", response);
		return BpiRsUtil.getSuccess(response);
	}

	/**
	 * 呼叫 coindesk API 在 format成自定義的資料 return
	 * 
	 * @return
	 * @throws ParseException 
	 * @throws JsonProcessingException 
	 * @throws Exception
	 */
	@ApiOperation(value = "呼叫外部coindesk API 後進行資料處理 return")
	@GetMapping("/call/coindesk/transform")
	public ApiResponse<NewBpiRs> transformNewBpi() throws JsonProcessingException, ParseException {
		String jsonStr = restTemplate.getForObject(COINDESK_URL, String.class);
		log.info("call coindesk api res : {}", jsonStr);
		return BpiRsUtil.getSuccess(bpiService.transform(jsonStr));
	}

}
