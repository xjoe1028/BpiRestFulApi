package com.bpi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
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

import com.bpi.common.CommonUtil;
import com.bpi.model.BpiRateRq;
import com.bpi.model.BpiRq;
import com.bpi.model.entity.BpiEntity;
import com.bpi.repository.BpiRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Bpi api test
 * 
 * @author Joe
 *
 * @Date 2021/12/22
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BpiTest {

	@Autowired
	private BpiRepository bpiRepository;
	
	@Autowired
	private ObjectMapper jsonMapper;

	@Autowired
	private MockMvc mockMvc;
	
	public final static String URL = "/api/bpi";
	public final static String TODAY = CommonUtil.getNowDate();

	public static List<String> codes = Arrays.asList("USD", "GBP", "EUR", "CNY", "JPY", "KRW");
	public static List<String> codesName = Arrays.asList("美元", "英镑", "歐元", "人民幣", "日元", "韓元");
	public static List<String> symbols = Arrays.asList("$", "£", "€", "¥", "¥", "₩");
	public static List<String> descriptions = Arrays.asList("United States Dollar", "British Pound Sterling", "Euro", "Chinese yuan", "Japanese Yen", "Korea Hwan");
	public static List<Double> ratesFloat = Arrays.asList(27.85, 37.85, 31.49, 4.39, 0.24, 0.023);
	public static List<String> createdDates = Arrays.asList(TODAY, TODAY, TODAY, TODAY, TODAY, TODAY);
	
	/**
	 * 資料初始化
	 * init data不須有更新日期
	 * 
	 */
	@Disabled("skip")
	@Test
	void beforeInit() throws Exception {
		for (int i = 0; i < codes.size(); i++) {
			BpiEntity rq = new BpiEntity();
			rq.setCode(codes.get(i));
			rq.setCodeChineseName(codesName.get(i));
			rq.setRate(CommonUtil.fmtMicrometer(ratesFloat.get(i).toString()));
			rq.setDescription(descriptions.get(i));
			rq.setRateFloat(ratesFloat.get(i));
			rq.setSymbol(symbols.get(i));
			rq.setCreated(createdDates.get(i));
			bpiRepository.save(rq);
		}
		log.info("testData : {}", bpiRepository.findAll().toString());
	}
	
	@Disabled("skip")
	@Test
	void deleteAllDataTest() throws Exception {
		bpiRepository.deleteAll();
		log.info("delete all success!!");
	}

	/**
	 * select all
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void findAllBpisTest() throws Exception {
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
	 * Select by code
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void findBipByCodeTest() throws Exception {
		ResultActions resultActions = this.mockMvc.perform(
			get(URL + "/findBpi/code") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.param("code", "USD")
		);
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * Select by codeChineseName
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void findBipByCodeChineseNameTest() throws Exception {
		ResultActions resultActions = this.mockMvc.perform(
			get(URL + "/findBpi/codeChineseName") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.param("codeChineseName", "人民幣")
		);
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * Select by primary key
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void findBipByCodeAndCodeChineseNameTest() throws Exception {
		ResultActions resultActions = this.mockMvc.perform(
			get(URL + "/findBpi/pk") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.param("code", "USD")
			.param("codeChineseName", "美元")
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
	void addBipTest() throws Exception {
		BpiRq rq = BpiRq.builder()
				.code("TWD")
				.symbol("$")
				.codeChineseName("新台幣")
				.description("New Taiwan Dollar")
				.rateFloat(100.2)
				.build();
		
		ResultActions resultActions = this.mockMvc.perform(
				post(URL + "/addBpi") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
				.content(jsonMapper.writeValueAsString(rq))
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
	void updateBipTest() throws Exception {
		List<BpiEntity> bpis = bpiRepository.findAll();
		BpiEntity bpi = bpis.get(0);
		bpi.setDescription("test update");
		bpi.setRateFloat(123.123);
		
		BpiRq rq = BpiRq.builder()
				.code(bpi.getCode())
				.codeChineseName(bpi.getCodeChineseName())
				.symbol(bpi.getSymbol())
				.rateFloat(bpi.getRateFloat())
				.description(bpi.getDescription())
				.build();
		
		ResultActions resultActions = this.mockMvc.perform(
				put(URL + "/updateBpi") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
				.content(jsonMapper.writeValueAsString(rq))
			);
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * update rate by code
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void updateBipRateTest() throws Exception {
		BpiRateRq rq = new BpiRateRq();
		rq.setCode("TWD");
		rq.setRate(741987.12);
		
		ResultActions resultActions = this.mockMvc.perform(
				patch(URL + "/updateBpiRate") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
				.content(jsonMapper.writeValueAsString(rq))
			);
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * delete by code
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void deleteBpiByCodeTest() throws Exception {
		BpiRq rq = new BpiRq();
		rq.setCode("TWD");
		ResultActions resultActions = this.mockMvc.perform(
				delete(URL + "/deleteBpi/code") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
				.content(jsonMapper.writeValueAsString(rq))
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
		ResultActions resultActions = this.mockMvc.perform(
				get(URL + "/call/coindesk") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			); 
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * call outside api and transform own data
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void callTransFormTest() throws Exception {
		ResultActions resultActions = this.mockMvc.perform(
				get(URL + "/call/coindesk/transform") // url
				.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			); 
		resultActions.andReturn().getResponse().setCharacterEncoding("UTF-8"); // 解决打印中文亂碼問題
		String response = resultActions.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
                .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
                .andReturn().getResponse().getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
}
