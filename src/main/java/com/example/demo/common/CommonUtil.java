package com.example.demo.common;

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
	
	public static String getNowDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		return sdf.format(date);
	}

	public static String updatedFormat(String updated) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = dateFormat.parse(updated);// You will get date object relative to server/client
		DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); // If you need time just put specific format for time
		String dateStr = formatter.format(date);
		return dateStr;
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
