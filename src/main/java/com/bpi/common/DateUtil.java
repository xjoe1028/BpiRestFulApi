package com.bpi.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Date util
 * 
 * @author Joe
 * 
 * @Date 2023/04/10
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

	public static String updatedFormat(String updated) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_T_HHMMSS);
		Date date = dateFormat.parse(updated);// You will get date object relative to server/client
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_HHMMSS); // If you need time just put specific format for time
		return formatter.format(date);
	}
	
}
