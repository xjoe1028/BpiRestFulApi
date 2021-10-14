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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Bpi;
import com.example.demo.entity.NewBpi;
import com.example.demo.service.CRUDService;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * @author Joe
 * 
 * @Date 2021/10/06
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/crud", produces = MediaType.APPLICATION_JSON_VALUE)
public class CRUDController {
	
	public static final String COINDESK_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";
	
	@Autowired
	private CRUDService crudService;
	
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
		List<Bpi> bpiList = crudService.findAll();
		if(bpiList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return ResponseEntity.ok(crudService.findAll());
	}
	
	/**
	 * 查詢 Bpi
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/findBpi/{id}")
	public ResponseEntity<Bpi> findBpiById(@PathVariable Long id) {
		Bpi bpi = crudService.findBpiById(id);
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
		return ResponseEntity.ok(crudService.addBpi(bpi));
	}
	
	/**
	 * 修改 Bpi 
	 * 
	 * @param bpi
	 * @return
	 */
	@PutMapping("/updateBpi")
	public ResponseEntity<Bpi> updateBpi(@RequestBody Bpi bpi) {
		return ResponseEntity.ok(crudService.updateBpi(bpi));
	}
	
	/**
	 * 刪除 Bpi
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deleteBpi/{id}")
	public ResponseEntity<Integer> deleteBpi(@PathVariable Long id) {
		return ResponseEntity.ok(crudService.deleteBpi(id));
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
	
	@GetMapping("/call/coindesk/transform")
	public ResponseEntity<NewBpi> transformNewBpi() throws Exception {
		String jsonStr = restTemplate.getForObject(COINDESK_URL, String.class);
		log.info("response : {}", jsonStr);
		return ResponseEntity.ok(crudService.transform(jsonStr));
	}
}
