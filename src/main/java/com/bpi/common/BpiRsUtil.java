package com.bpi.common;

import com.bpi.model.ApiResponse;

/**
 * BpiRs util
 * 
 * @author Joe
 *
 * @Date 2021/07/14
 */
public class BpiRsUtil {

	/**
	 * Get success API response.
	 * 
	 * @param <T>
	 * @param data
	 * @return
	 */
	public static <T> ApiResponse<T> getSuccess(T data) {
		return ApiResponse.<T>builder(data).build();
	}
	
	/**
	 * 
	 * @param <T>
	 * @param code
	 * @return
	 */
	public static <T> ApiResponse<T> getFailed(ErrorCode code) {
		return ApiResponse.<T>builder(code.getCode(), code.getMessage()).build();
	}
	
	/**
	 * Get failed API response.
	 * 
	 * @param <T>
	 * @param code
	 * @param message
	 * @return
	 */
	public static <T> ApiResponse<T> getFailed(ErrorCode code, String message) {
		return ApiResponse.<T>builder(code.toString(), message).build();
	}

	/**
	 * 
	 * @param <T>
	 * @param ar
	 * @return
	 */
	public static <T> ApiResponse<T> getFailed(ApiResponse<?> ar) {
		return ApiResponse.<T>builder(ar.getCode(), ar.getMessage()).build();
	}
}
