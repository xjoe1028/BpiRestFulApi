package com.bpi.common;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * Json utils
 * 
 * @author Joe
 * 
 * @Date 2022/10/26
 * 
 */
@Slf4j
public class JsonUtils {

	/**
	 * Json字串轉換為物件
	 * 
	 * @param <P>
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <P> P getObject(String json, Class<P> clazz) {
		try {
            Gson gson = new Gson();
            return gson.fromJson(json, clazz);
        } catch (Exception ex) {
            log.error("JsonWriter Error", ex);
        }

		 return null;
	}
	
	/**
	 * 物件轉換為Json字串
	 * 
	 * @param object
	 * @return
	 */
	public static String getJson(Object object) {
		try {
            Gson gson = new Gson();
			return gson.toJson(object);
		} catch (Exception ex) {
			log.error("JsonWriter Error", ex);
		}

		return "";
	}
	
}
