package com.bpi.common;

import lombok.Getter;

/**
 * Message
 * 
 * @author Joe
 * 
 * @Date 2022/01/18
 */
@Getter
public enum ErrorCode {

	// error message
	FAILED("1000", "系統錯誤"),
	SELECT_EMPTY("1001", "查無資料"),
	INSERT_FAILED_PK_ONLY("1002", "新增失敗 : 此幣別已存在"),
	UPDATE_FAILED_DATA_NOT_EXIST("1003", "更新失敗 : 此資料不存在"),
	UPDATE_RATE_NULL("1004", "檢核錯誤 : rate is null"),
	UPDATE_CODE_NULL("1005", "檢核錯誤 : code is null"),
	UPDATE_CODE_AND_RATE_NULL("1006", "檢核錯誤 : code and rate all null"),
	DELETE_FAILED_DATA_NOT_EXIST("1007", "刪除失敗 : 無此資料"),
	VALIDATION_ERROR("1008", "欄位檢核錯誤");
	
	private String code;
	private String message;
	
	ErrorCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public static ErrorCode findByCode(String code) {
		for (ErrorCode e : ErrorCode.values()) {
			if (e.name().equals(code)) {
				return e;
			}
		}
		return FAILED;
	}
	
	
}
