package com.bpi.util;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

/**
 * Json utils
 * 
 * @author Joe
 * 
 */
@Slf4j
public class JsonUtils {

	/**
	 * Gson
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
	 * Gson
	 * json to List of Object
	 * 
	 * @param <P>
	 * @param json
	 * @return
	 */
	public static <P> List<P> getListObject(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<P>>() {}.getType());
	}
 	
	/**
	 * Gson
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
	
	/**
	 * jackson
	 * Json字串轉Object
	 * 
	 * @param obj
	 * @param jsonStr
	 * @return 
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public static <P> P jsonToObject(String jsonStr, Class<P> clazz) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(jsonStr, clazz);
	}
	
	/**
	 * jackson 
	 * Object轉Json字串
	 * 
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 */
	public static String objectToJson(Object obj) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
	
	/**
	 * jackson
	 * Json字串轉List of Object
	 * 
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <P> List<P> jsonToListObj(String jsonStr, Class<P> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		CollectionType javaType =  mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		try {
			return mapper.readValue(jsonStr, javaType);
		} catch (JsonProcessingException e) {
			log.error("Jackson Writer Error : {}", e.getMessage());
			return Collections.emptyList();
		}
	}
}
