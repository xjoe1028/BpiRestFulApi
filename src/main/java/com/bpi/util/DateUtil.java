package com.bpi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

/**
 * Date util
 * 
 * @author Joe
 * 
 */
public class DateUtil {

	public static final String DATE_FORMAT_YYYYMMDD_HHMMSS = "yyyy/MM/dd hh:mm:ss";
	public static final String DATE_FORMAT_YYYYMMDD_T_HHMMSS = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static String getNowDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT_YYYYMMDD_HHMMSS);
		return LocalDateTime.now().format(dtf);
	}
	
	public static String dateToString(LocalDateTime dateTime) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT_YYYYMMDD_HHMMSS);
		return dateTime.format(dtf);
	}
	
	public static String dateToFormat(String format, LocalDateTime dateTime) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		return dateTime.format(dtf);
	}

	/**
	 * 將 ex: yyyy-MM-dd'T'HH:mm:ss 時間格式字串 轉 yyyy/MM/dd hh:mm:ss 時間格式字串
	 * 
	 * @param updated
	 * @return
	 */
	public static String updatedFormat(String updated) {
		DateTimeFormatter sourceFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_YYYYMMDD_T_HHMMSS);
		LocalDateTime localDateTime = LocalDateTime.parse(updated, sourceFormatter);
		DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT_YYYYMMDD_HHMMSS);
		return localDateTime.format(targetFormatter);
	}
	
}
