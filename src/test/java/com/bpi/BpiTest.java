package com.bpi;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bpi.model.assembler.BpiAssembler;
import com.bpi.model.entity.BpiEntity;
import com.bpi.model.rq.BpiRateRq;
import com.bpi.model.rq.BpiRq;
import com.bpi.mybatis.BpiMapper;
import com.bpi.repository.BpiRepository;
import com.bpi.util.DateUtil;
import com.bpi.util.NumberUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Bpi api test
 * 
 * Junit 不能使用 @RequiredArgsConstructor 去做注入 
 * 
 * @author Joe
 *
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class BpiTest {

	@Autowired
	private BpiRepository bpiRepository;
	
	@Autowired
	private BpiMapper bpiMapper;
	
	@Autowired
	private BpiAssembler bpiAssembler;
	
	@Autowired
	private ObjectMapper jsonMapper;

	@Autowired
	private MockMvc mockMvc;
	
	public final static String URL = "/api/bpi";
	public final static String TODAY = DateUtil.getNowDate();
	public final static String CHARACTER_ENCODING = "UTF-8";
	
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
			BpiEntity rq = BpiEntity.builder()
				.code(codes.get(i))
				.codeChineseName(codesName.get(i))
				.rate(NumberUtil.fmtMicrometer(ratesFloat.get(i).toString()))
				.description(descriptions.get(i))
				.rateFloat(ratesFloat.get(i))
				.symbol(symbols.get(i))
				.created(createdDates.get(i))
				.build();
			
			bpiRepository.save(rq);
		}
		
		List<BpiEntity> bpiList = bpiRepository.findAll();
		log.info("testData : {}", bpiList.toString());
		Assertions.assertEquals(codes.size(), bpiRepository.findAll().size());
	}
	
	@Disabled("skip")
	@Test
	void deleteAllDataTest() throws Exception {
		bpiRepository.deleteAll();
		Assertions.assertEquals(0, bpiRepository.findAll().size());
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
		MockHttpServletResponse mockRes = this.mockMvc.perform(
			get(URL + "/findAllBpis")
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.accept(MediaType.APPLICATION_JSON_VALUE)
		)
		.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
        .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
        .andReturn().getResponse(); // 將相應的資料轉換為字串
		mockRes.setCharacterEncoding(CHARACTER_ENCODING); // 解决打印中文亂碼問題
		String response = mockRes.getContentAsString();
		log.info("response : {}", response);
	}
	
	/**
	 * Select by code
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void findBpiByCodeTest() throws Exception {
		MockHttpServletResponse mockRes = this.mockMvc.perform(
			get(URL + "/findBpi/code") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.accept(MediaType.APPLICATION_JSON)
			.param("code", "USD")
		)
		.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
		.andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
		.andReturn().getResponse();
		mockRes.setCharacterEncoding(CHARACTER_ENCODING); // 解决打印中文亂碼問題
		String response = mockRes.getContentAsString(); // 將相應的資料轉換為字串
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
		MockHttpServletResponse mockRes = this.mockMvc.perform(
			get(URL + "/findBpi/codeChineseName") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.param("codeChineseName", "人民幣")
		)
		.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
	    .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
	    .andReturn().getResponse();
		mockRes.setCharacterEncoding(CHARACTER_ENCODING); // 解决打印中文亂碼問題
		String response = mockRes.getContentAsString(); // 將相應的資料轉換為字串
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
		MockHttpServletResponse mockRes = this.mockMvc.perform(
			get(URL + "/findBpi/pk") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.accept(MediaType.APPLICATION_JSON)
			.param("code", "USD")
			.param("codeChineseName", "美元")
		)
		.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
	    .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
	    .andReturn().getResponse();
		mockRes.setCharacterEncoding(CHARACTER_ENCODING); // 解决打印中文亂碼問題
		String response = mockRes.getContentAsString(); // 將相應的資料轉換為字串
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
		
		MockHttpServletResponse mockRes = this.mockMvc.perform(
			post(URL + "/addBpi") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.accept(MediaType.APPLICATION_JSON)
			.content(jsonMapper.writeValueAsString(rq))
		)
		.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
	    .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
	    .andReturn().getResponse(); 
		mockRes.setCharacterEncoding(CHARACTER_ENCODING); // 解决打印中文亂碼問題
		String response = mockRes.getContentAsString(); // 將相應的資料轉換為字串
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
		bpi.setDescription("update jpa save");
		bpi.setRateFloat(1234.123);
		
		BpiRq rq = BpiRq.builder()
			.code(bpi.getCode())
			.codeChineseName(bpi.getCodeChineseName())
			.symbol(bpi.getSymbol())
			.rateFloat(bpi.getRateFloat())
			.description(bpi.getDescription())
			.build();
		
		// 前端在update 會送 舊幣別 讓後端知道是否新增還是更新
		rq.setOldCode(bpi.getCode()); 
		
		MockHttpServletResponse mockRes = this.mockMvc.perform(
			put(URL + "/updateBpi") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.accept(MediaType.APPLICATION_JSON)
			.content(jsonMapper.writeValueAsString(rq))
		)
		.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
	    .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
	    .andReturn().getResponse();
		mockRes.setCharacterEncoding(CHARACTER_ENCODING); // 解决打印中文亂碼問題
		String response = mockRes.getContentAsString(); // 將相應的資料轉換為字串
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
		
		MockHttpServletResponse mockRes = this.mockMvc.perform(
			patch(URL + "/updateBpiRate") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.accept(MediaType.APPLICATION_JSON)
			.content(jsonMapper.writeValueAsString(rq))
		)
		.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
	    .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
	    .andReturn().getResponse();
		mockRes.setCharacterEncoding(CHARACTER_ENCODING); // 解决打印中文亂碼問題
		String response = mockRes.getContentAsString(); // 將相應的資料轉換為字串
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
		MockHttpServletResponse mockRes = this.mockMvc.perform(
			delete(URL + "/deleteBpi/code") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.accept(MediaType.APPLICATION_JSON)
			.content(jsonMapper.writeValueAsString(rq))
		)
		.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
	    .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
	    .andReturn().getResponse();
		mockRes.setCharacterEncoding(CHARACTER_ENCODING); // 解决打印中文亂碼問題
		String response = mockRes.getContentAsString(); // 將相應的資料轉換為字串
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
		MockHttpServletResponse mockRes = this.mockMvc.perform(
			get(URL + "/call/coindesk") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
        .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
        .andReturn().getResponse(); 
		mockRes.setCharacterEncoding(CHARACTER_ENCODING); // 解决打印中文亂碼問題
		String response = mockRes.getContentAsString(); // 將相應的資料轉換為字串
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
		MockHttpServletResponse mockRes = this.mockMvc.perform(
			get(URL + "/call/coindesk/transform") // url
			.contentType(MediaType.APPLICATION_JSON) // 資料的格式
			.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk()) // 期待狀態OK
	    .andDo(MockMvcResultHandlers.print()) // 打印出請求和相應的內容
	    .andReturn().getResponse(); 
		mockRes.setCharacterEncoding(CHARACTER_ENCODING); // 解决打印中文亂碼問題
		String response = mockRes.getContentAsString(); // 將相應的資料轉換為字串
		log.info("response : {}", response);
	}
	
	/**
	 * MyBatis test update bpi
	 * 
	 * @throws Exception
	 */
	@Disabled("skip")
	@Test
	void updateBpiForMyBatisTest() throws Exception {
		var bpis = bpiRepository.findAll();
		var entity = bpis.get(0);
		entity.setDescription("test mybatis update");
		entity.setRateFloat(1234.123);
		
		var bpiForMyBatis = bpiAssembler.entityToBpiForMyBatis(entity);
		bpiForMyBatis.setOldCode(entity.getCode());
		
		var result = bpiMapper.updateByCode(bpiForMyBatis);
		log.info("update success : {} 筆", result);
	}
	
}
