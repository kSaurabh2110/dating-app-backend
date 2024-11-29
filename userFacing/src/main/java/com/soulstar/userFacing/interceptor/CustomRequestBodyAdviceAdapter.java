package com.soulstar.userFacing.interceptor;

import com.soulstar.userFacing.Utils.JsonAndGsonUtils;
import com.soulstar.userFacing.config.CorrelationIdGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.sql.Timestamp;

import static com.soulstar.userFacing.config.CorrelationIdGenerator.getUniqueRequestIdLogging;

@ControllerAdvice
public class CustomRequestBodyAdviceAdapter extends RequestBodyAdviceAdapter {
    @Autowired
    HttpServletRequest httpServletRequest;

    private Logger logger = LogManager.getLogger(getClass());
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Timestamp startTime = (Timestamp) httpServletRequest.getAttribute("startTime");
        logger.info(getUniqueRequestIdLogging()+"Request came at Time "+ startTime +" for request path "+httpServletRequest.getServletPath()+" with request body "+ body.toString());
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Timestamp startTime = (Timestamp) httpServletRequest.getAttribute("startTime");
        logger.debug(getUniqueRequestIdLogging()+"Request sent at Time "+ startTime +" for request path "+httpServletRequest.getServletPath()+httpServletRequest.getPathInfo());
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }

}
