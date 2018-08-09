package com.xuehai.exception;

import com.xhtech.arch.ddd.interfaces.http.dto.ApiErrors;
import com.xhtech.arch.ddd.interfaces.http.exception.WithSecurityExceptionController;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;


/**
 * @author: zhangcong
 * @date: 2018/04/11 18:30
 * @describe: 自定义参数校验异常处理
 */
@RestControllerAdvice
@Slf4j
public class ValidatorController extends WithSecurityExceptionController {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiErrors> serverFails(ConstraintViolationException ex, HttpServletRequest request) {
        if (log.isInfoEnabled()) {
            log.info("服务端错误 [method={}\turl={}\tquery={}]", new Object[]{request.getMethod(), request.getRequestURI(), request.getQueryString(), ex});
        }
        Set<ConstraintViolation<?>> set = ex.getConstraintViolations();
        Iterator iterator = set.iterator();
        String message = "参数校验错误";
        while (iterator.hasNext()) {
            ConstraintViolationImpl violationImpl = (ConstraintViolationImpl) iterator.next();
            message = violationImpl.getMessage();
        }
        ApiErrors errors = ApiErrors.builder().setCode(500L).setMsg(message).build();
        return new ResponseEntity(errors, HttpStatus.OK);
    }

    @ExceptionHandler(HearException.class)
    public ResponseEntity<ApiErrors> hearError(HearException ex, HttpServletRequest request) {
        if (log.isInfoEnabled()) {
            log.info("服务端错误 [method={}\turl={}\tquery={}]", new Object[]{request.getMethod(), request.getRequestURI(), request.getQueryString(), ex});
        }
        ApiErrors errors = ApiErrors.builder().setCode(ex.getCode()).setMsg(ex.getErrMsg()).build();
        return new ResponseEntity(errors, HttpStatus.OK);
    }
}
