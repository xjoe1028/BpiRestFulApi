package com.example.demo.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import com.example.demo.common.BpiRsUtil;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.BpiRateRq;
import com.example.demo.model.BpiRq;
import com.example.demo.model.CodeRq;
import com.example.demo.model.NewBpiRs;
import com.example.demo.model.RqType;
import com.example.demo.model.entity.Bpi;
import com.example.demo.service.BpiService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Joe
 * 
 * @Date 2021/10/06
 */
@Slf4j
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
	@GetMapping("/findAllBpis")
	public ApiResponse<List<Bpi>> findAllBpis() {
		return bpiService.findAll();
	}

	/**
	 * 查詢 Bpi by code
	 * 
	 * @param code
	 * @return
	 */
	@GetMapping("/findBpi/code")
	public ApiResponse<Bpi> findBpiByPk(@RequestParam(name = "code", defaultValue = "") String code) {
		return bpiService.findBpiByPk(code);
	}

	/**
	 * 查詢 Bpi by code
	 * 
	 * @param code
	 * @return
	 */
	@GetMapping("/findBpi/codeChineseName")
	public ApiResponse<Bpi> findBpiByCodeChineseName(@RequestParam(name = "codeChineseName", defaultValue = "") String codeChineseName) {
		return bpiService.findBpiByCodeChineseName(codeChineseName);
	}

	/**
	 * 查詢 Bpi by code and codeChineseName
	 * 
	 * @param code
	 * @return
	 */
	@GetMapping("/findBpi/pk")
	public ApiResponse<Bpi> findBpiByPk(@RequestParam(name = "code", defaultValue = "") String code, @RequestParam(name = "codeChineseName", defaultValue = "") String codeChineseName) {
		return bpiService.findBpiByCodeAndCodeChineseName(code, codeChineseName);
	}

	/**
	 * 新增 Bpi
	 * 
	 * @param bpi
	 * @return
	 */
	@RqType(BpiRq.class)
	@PostMapping("/addBpi")
	public ApiResponse<Bpi> addBpi(@RequestBody BpiRq rq) {
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
	@RqType(BpiRq.class)
	@PutMapping("/updateBpi")
	public ApiResponse<Bpi> updateBpi(@RequestBody BpiRq rq) {
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
	@RqType(BpiRateRq.class)
	@PatchMapping("/updateBpiRate")
	public ApiResponse<Bpi> updateBpiRate(@RequestBody BpiRateRq rq) {
		return bpiService.updateBpiRate(rq);
	}

	/**
	 * 刪除 Bpi by code
	 * 
	 * @param id
	 * @return
	 */
	@RqType(CodeRq.class)
	@DeleteMapping("/deleteBpi/code")
	public ApiResponse<Bpi> deleteBpi(@RequestBody CodeRq rq) {
		return bpiService.deleteBpiByCode(rq.getCode());
	}

	/**
	 * 呼叫 coindesk API
	 * 
	 * @return
	 */
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
	@GetMapping("/call/coindesk/transform")
	public ApiResponse<NewBpiRs> transformNewBpi() throws JsonProcessingException, ParseException {
		String jsonStr = restTemplate.getForObject(COINDESK_URL, String.class);
		log.info("call coindesk api res : {}", jsonStr);
		return BpiRsUtil.getSuccess(bpiService.transform(jsonStr));
	}

}
