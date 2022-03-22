package com.bpi.aspect;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.bpi.common.BpiRsUtil;
import com.bpi.common.ErrorCode;
import com.bpi.model.ApiResponse;
import com.bpi.model.RqType;

import lombok.extern.slf4j.Slf4j;

/**
 * Rq Aspect aop
 * 
 * @author Joe
 * 
 * @Date 2022/02/14
 * 
 */
@Slf4j
@Aspect
@Configuration
public class RqAspect {
	
	@Autowired
	private Validator validator;

	// 切入點為com.abc.demo.controller下的所有類別的所有方法
	@Around(value = "execution(* com.example.demo.controller.*.*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//		String wholeClassName = joinPoint.getTarget().getClass().getName(); // 取得切入點的類別名稱(含package.class)
		String className = joinPoint.getSignature().getDeclaringType().getSimpleName(); // 取得切入點的類別名稱(只有類別名稱)
		String annotatedMethodName = joinPoint.getSignature().getName(); // 取得切入點的方法名稱
		log.info("----- className.MethodName : {}.{} start -----", className, annotatedMethodName);
		Object[] args = joinPoint.getArgs(); // 取得輸入參數值
		
		// 取得有含RqType的 annotation
		RqType rqType = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RqType.class);
		
		// 判斷有無 @RqType annotation
		if(rqType != null) {
			log.info("-----validate rq-----");
			Object rq = args[0];
			// validate rq
			for (ConstraintViolation<Object> violation : validator.validate(rq)) {
				if (StringUtils.isNotBlank(violation.getMessage())) {
					log.info(violation.getMessage());
					return BpiRsUtil.getFailed(ErrorCode.VALIDATION_ERROR, violation.getMessage());
				}
			}
		}
		
		log.info("----- className.MethodName : {}.{} end -----", className, annotatedMethodName);
		ApiResponse<?> apiRs = (ApiResponse<?>) joinPoint.proceed();
		return apiRs;
	}
	
}
