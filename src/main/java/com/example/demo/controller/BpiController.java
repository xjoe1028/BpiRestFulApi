package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Bpi;
import com.example.demo.entity.NewBpi;
import com.example.demo.service.BpiService;

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
	public ResponseEntity<List<Bpi>> findAllBpis() {
		List<Bpi> bpiList = bpiService.findAll();
		if(bpiList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return ResponseEntity.ok(bpiList);
	}
	
	/**
	 * 查詢 Bpi by code
	 * 
	 * @param code
	 * @return
	 */
	@GetMapping("/findBpi/code")
	public ResponseEntity<Bpi> findBpiByCode(@RequestParam("code") String code) {
		Bpi bpi = bpiService.findBpiByCode(code);
		if(bpi == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return ResponseEntity.ok(bpi);
	}
	
	/**
	 * 查詢 Bpi by code and codeChineseName
	 * 
	 * @param code
	 * @return
	 */
	@GetMapping("/findBpi/code/codeChineseName")
	public ResponseEntity<Bpi> findBpiByCodeChineseName(@RequestParam("code") String code, @RequestParam("codeChineseName") String codeChineseName) {
		Bpi bpi = bpiService.findByCodeAndCodeChineseName(code, codeChineseName);
		if(bpi == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return ResponseEntity.ok(bpi);
	}
	
	/**
	 * 新增 Bpi
	 * 
	 * @param bpi
	 * @return
	 */
	@PostMapping("/addBpi")
	public ResponseEntity<Bpi> addBpi(@RequestBody Bpi bpi) {
		return ResponseEntity.ok(bpiService.addBpi(bpi));
	}
	
	/**
	 * 修改 Bpi 
	 * 
	 * @param bpi
	 * @return
	 */
	@PutMapping("/updateBpi")
	public ResponseEntity<Bpi> updateBpi(@RequestBody Bpi bpi) {
		return ResponseEntity.ok(bpiService.updateBpi(bpi));
	}
	
	/**
	 * 修改 Bpi 匯率
	 * 
	 * @param bpi
	 * @return
	 */
	@PutMapping("/updateBpiRate")
	public ResponseEntity<Integer> updateBpiRate(@RequestBody Bpi bpi) {
		return ResponseEntity.ok(bpiService.updateBpiRate(bpi));
	}
	
	/**
	 * 刪除 Bpi by code
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deleteBpi/code")
	public ResponseEntity<Integer> deleteBpiByCode(@RequestBody String code) {
		return ResponseEntity.ok(bpiService.deleteBpiByCode(code));
	}
	
	/**
	 * 呼叫 coindesk API
	 * 
	 * @return
	 */
	@GetMapping("/call/coindesk")
	public ResponseEntity<String> callCoindeskAPI() {
		String response = restTemplate.getForObject(COINDESK_URL, String.class);
		log.info("response : {}", response);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * 呼叫 coindesk API 在 format成自定義的資料 return
	 * 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/call/coindesk/transform")
	public ResponseEntity<NewBpi> transformNewBpi() throws Exception {
		String jsonStr = restTemplate.getForObject(COINDESK_URL, String.class);
		log.info("response : {}", jsonStr);
		return ResponseEntity.ok(bpiService.transform(jsonStr));
	}
}
