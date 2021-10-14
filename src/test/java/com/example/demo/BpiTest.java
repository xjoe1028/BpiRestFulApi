package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.entity.Bpi;
import com.example.demo.repository.CRUDRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BpiTest {

	@Autowired
	private CRUDRepository crudRepository;
	
	@Autowired
	private ObjectMapper jsonMapper;

	@Autowired
	private MockMvc mockMvc;
	
	public final static String URL = "/api/crud";

	public static String[] codes = {"USD", "GBP", "EUR" };
	public static String[] codesName = {"美元", "英镑", "歐元" };
	public static String[] symbols = {"$", "£", "€"};
	public static String[] rates = {"51,211.0422", "37,789.4474", "44,368.3764" };
	public static String[] descriptions = {"United States Dollar", "British Pound Sterling", "Euro" };
	public static Double[] ratesFloat = {51211.0422, 37789.4474, 44368.3764 };
	public static String[] updateDates = {getNowDate(new Date()), getNowDate(new Date()), getNowDate(new Date())};
	
	/**
	 * 資料初始化
	 * 
	 */
	private void beforeInit() throws Exception {
		for (int i = 0; i < codes.length; i++) {
			Bpi bpi = new Bpi();
			bpi.setCode(codes[i]);
			bpi.setCodeChineseName(codesName[i]);
			bpi.setRate(rates[i]);
			bpi.setDescription(descriptions[i]);
			bpi.setRate_float(ratesFloat[i]);
			bpi.setSymbol(symbols[i]);
			bpi.setUpdated(updateDates[i]);
			crudRepository.save(bpi);
		}
		log.info("testData : {}", crudRepository.findAll().toString());
	}

	/**
	 * select all
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void findAllBpisTest() throws Exception {
		beforeInit();
		ResultActions resultActions = this.mockMvc.perform(
				get(URL + "/findAllBpis")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
			);
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * Select by id
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void findBipByIdTest() throws Exception {
		beforeInit();
		ResultActions resultActions = this.mockMvc.perform(get(URL + "/findBpi/1") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
		);
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * insert 
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void addBip() throws Exception {
		Bpi bpi = Bpi.builder()
				.code("TWD")
				.symbol("$")
				.codeChineseName("新台幣")
				.description("New Taiwan Dollar")
				.rate("1,000.2")
				.rate_float(1000.2)
				.build();
		
		ResultActions resultActions = this.mockMvc.perform(post(URL + "/addBpi") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
				.content(jsonMapper.writeValueAsString(bpi))
			); 
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * update
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void updateBip() throws Exception {
		beforeInit();
		List<Bpi> bpis = crudRepository.findAll();
		
		Bpi bpi = bpis.get(0);
		bpi.setDescription("test update");
		bpi.setRate_float(123.123);
		
		ResultActions resultActions = this.mockMvc.perform(put(URL + "/updateBpi") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
				.content(jsonMapper.writeValueAsString(bpi))
			);
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * delete
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void deleteBpi() throws Exception {
		beforeInit();
		ResultActions resultActions = this.mockMvc.perform(delete(URL + "/deleteBpi/1") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			);
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * call outside api
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void callCoindeskTest() throws Exception {
		ResultActions resultActions = mockMvc.perform(get(URL + "/call/coindesk") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			); 
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * call outside api and transform our data
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void callTransFormTest() throws Exception {
		ResultActions resultActions = mockMvc.perform(get(URL + "/call/coindesk/transform") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			); 
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	private static String getNowDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		return sdf.format(date);
	}
}
