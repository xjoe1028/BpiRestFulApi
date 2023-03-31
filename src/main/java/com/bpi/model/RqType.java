package com.bpi.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RqType Annotation
 * 
 * @author Joe
 * 
 * @Date 2022/02/14
 *
 * @Retention
 * 	SOURCE: 代表的是這個Annotation類型的信息只會保留在程序源碼里，源碼如果經過了編譯之后，Annotation的數據就會消失,并不會保留在編譯好的.class文件里面。
 * 	ClASS: 的意思是這個Annotation類型的信息保留在程序源碼里，同時也會保留在編譯好的.class文件里面，在執行的時候，并不會把這一些信息加載到虛擬機(JVM)中去.注意一下，當你沒有設定一個Annotation類型的Retention值時，系統默認值是CLASS.
 * 	RUNTIME: 表示在源碼、編譯好的.class文件中保留信息，在執行的時候會把這一些信息加載到JVM中去的．
 * 
 * @Target : 限定註解的使用位置
 * 	ANNOTATION_TYPE: 註釋型態
 * 	FIELD: 用於資料成員
 *  METHOD: 方法
 *  PARAMETER: 方法參數
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
public @interface RqType {

	Class<?> value();
	
}
