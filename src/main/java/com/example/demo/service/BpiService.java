package com.example.demo.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Bpi;
import com.example.demo.entity.Coindesk;
import com.example.demo.entity.NewBpi;
import com.example.demo.entity.pk.BpiPK;
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
	 * select by bpiId
	 * 
	 * @param id
	 * @return
	 */
	@Deprecated
	public Bpi findBpiById(Long id) {
		return bpiRepository.findByBpiId(id);
//		return bpiRepository.getById(BpiPK.builder().bpiId(id).build());
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
	public Bpi addBpi(Bpi bpi) {
		return bpiRepository.save(bpi);
	}

	/**
	 * 修改
	 * 
	 * @param bpi
	 * @return
	 */
	public Bpi updateBpi(Bpi bpi) {
		Bpi entity = new Bpi();
		BeanUtils.copyProperties(bpi, entity);
		entity.setUpdated(getNowDate(new Date()));
		return bpiRepository.save(entity);
	}
	
	/**
	 * 修改 匯率 by code
	 * 
	 * @param bpi
	 * @return
	 */
	public Integer updateBpiRate(Bpi bpi) {
		Bpi entity = bpiRepository.findByCode(bpi.getCode());
		if(entity == null) {
			return 0;
		}
		
		if(bpi.getRateFloat() == null || bpi.getRate() == null) {
			return 0;
		} else if(bpi.getRate() == null && bpi.getRateFloat() != null) {
			bpi.setRate(String.valueOf(bpi.getRateFloat()));
		} else if(bpi.getRate() != null && bpi.getRateFloat() == null) {
			bpi.setRateFloat(Double.parseDouble(bpi.getRate()));
		}
		
		bpiRepository.updateBpiRateByCode(bpi.getRate(), bpi.getRateFloat(), bpi.getCode());
		return 1;
	}

	/**
	 * 刪除 where id
	 * 
	 * @param id
	 * @return
	 */
	@Deprecated
	public Integer deleteBpi(Long id) {
		Optional<Bpi> entity = bpiRepository.findById(BpiPK.builder().bpiId(id).build());
		if (entity == null) {
			return 0;
		}
		bpiRepository.deleteById(BpiPK.builder().bpiId(id).build());
		return 1;
	}
	
	/**
	 * 刪除 where code
	 * 
	 * @param code
	 * @return
	 */
	public Integer deleteBpiByCode(String code) {
		Optional<Bpi> entity = bpiRepository.findById(BpiPK.builder().code(code).build());
		if (entity == null) {
			return 0;
		}
		bpiRepository.deleteBpiByCode(code);
		return 1;
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
		
		newBpi.setUpdated(updatedFormat(coindesk.getTime().getUpdatedISO().substring(0,19)));
		newBpi.setNewBpi(map);
		return newBpi;
	}
	
	private String getNowDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		return sdf.format(date);
	}

	private String updatedFormat(String updated) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = dateFormat.parse(updated);// You will get date object relative to server/client
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); // If you need time just put specific format for time
		String dateStr = formatter.format(date);
		return dateStr;
	}

}
