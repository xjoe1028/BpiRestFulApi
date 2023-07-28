package com.bpi.cconstant;

import java.util.Arrays;

import jdk.jfr.Description;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Message
 * 
 * @author Joe
 * 
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// error message
	FAILED("1000", "系統錯誤"),
	SELECT_EMPTY("1001", "查無資料"),
	INSERT_FAILED_PK_ONLY("1002", "新增失敗：此幣別已存在"),
	UPDATE_FAILED_PK_ONLY("1003", "更新失敗：此幣別不存在"),
	UPDATE_RATE_NULL("1004", "檢核錯誤：rate is null"),
	UPDATE_CODE_NULL("1005", "檢核錯誤：code is null"),
	UPDATE_CODE_AND_RATE_NULL("1006", "檢核錯誤：code and rate all null"),
	DELETE_FAILED_DATA_NOT_EXIST("1007", "刪除失敗：無此資料"),
	VALIDATION_ERROR("1008", "欄位檢核錯誤")
	;
	
	@Description("錯誤代碼")
	final String code;
	
	@Description("錯誤訊息")
	final String message;
	
	public static ErrorCode findByCode(String code) {
		return Arrays.stream(ErrorCode.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(FAILED);
	}
	
	public static String findErrMsg(String code) {
		return Arrays.stream(ErrorCode.values()).filter(e -> e.getCode().equals(code)).findFirst()
			.map(ErrorCode::getMessage).orElse(FAILED.getMessage());
	}

}
