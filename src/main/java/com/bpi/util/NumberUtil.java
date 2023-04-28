package com.bpi.util;

import java.text.DecimalFormat;

/**
 * Number util
 * 
 * @author Joe
 * 
 */
public class NumberUtil {
	
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
