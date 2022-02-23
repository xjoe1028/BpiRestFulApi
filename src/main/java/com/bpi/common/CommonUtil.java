package com.bpi.common;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 共用 util
 * 
 * @author Joe
 * 
 * @Date 2022/01/18
 */
public class CommonUtil {
	
	public static final String DATE_FORMAT_YYYYMMDD_HHMMSS = "yyyy/MM/dd hh:mm:ss";
	public static final String DATE_FORMAT_YYYYMMDD_T_HHMMSS = "yyyy-MM-dd'T'HH:mm:ss";
	
	public static String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_HHMMSS);
		return sdf.format(new Date());
	}
	
	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_HHMMSS);
		return sdf.format(date);
	}
	
	public static String dateToFormat(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String updatedFormat(String updated) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_T_HHMMSS);
		Date date = dateFormat.parse(updated);// You will get date object relative to server/client
		DateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD_HHMMSS); // If you need time just put specific format for time
		return formatter.format(date);
	}
	
	/**
	 * @Title: fmtMicrometer
	 * @Description: 格式化數字為千分位
	 * @param text
	 * @return    設定檔案
	 * @return String    返回型別
	 */
	public static String fmtMicrometer(String text) {
		DecimalFormat df = null;
		if (text.indexOf(".") > 0) {
			if (text.length() - text.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (text.length() - text.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			number = 0.0;
		}
		return df.format(number);
	}

}
