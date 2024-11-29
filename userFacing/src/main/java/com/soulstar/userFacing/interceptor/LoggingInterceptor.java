package com.soulstar.userFacing.interceptor;

import com.soulstar.userFacing.Utils.JsonAndGsonUtils;
import com.soulstar.userFacing.config.CorrelationIdGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;

import static com.soulstar.userFacing.config.CorrelationIdGenerator.getUniqueRequestIdLogging;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private Logger logger = LogManager.getLogger(getClass());
    private static final String TRACE_ID = "traceId";
    private static final String UUID = "uuid";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        request.setAttribute("startTime", startTime);
        request.setAttribute("path", request.getServletPath());
        String traceId = request.getHeader(TRACE_ID);
        String headerUuid = request.getHeader(UUID);
        String uuid= StringUtils.isNotBlank(traceId) ? traceId : (StringUtils.isNotBlank(headerUuid) ? headerUuid  : java.util.UUID.randomUUID().toString());
        CorrelationIdGenerator.setUuid(uuid);
        request.setAttribute("requestId", CorrelationIdGenerator.getUuid());
        logger.debug(getUniqueRequestIdLogging()+"Request came at Time "+startTime+" of path "+request.getServletPath());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        request.setAttribute("endTime", endTime);
        logger.debug(getUniqueRequestIdLogging()+"Response sent at Time "+ endTime+" of path "+request.getServletPath());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
        Long totalTime=null;
        try {
            Timestamp endTime = (Timestamp) request.getAttribute("endTime");
            Timestamp startTime = (Timestamp) request.getAttribute("startTime");
            totalTime = endTime.getTime() - startTime.getTime();
            request.setAttribute("total time taken", totalTime);
            request.setAttribute("status", response.getStatus());
        }catch (Exception e){
            logger.error(getUniqueRequestIdLogging()+"Error in after completion :: "+e.getMessage());
            throw new RuntimeException();
        } finally{
            logger.info(getUniqueRequestIdLogging()+"Request completion Time "+totalTime+" of path "+request.getServletPath()+ " and query string "+request.getQueryString()+" and header Fe-User-Agent:"+request.getHeader("Fe-User-Agent")+" and header Fe-User-IP:"+request.getHeader("Fe-User-IP")+" and header Fe-User-Referrer:"+request.getHeader("Fe-User-Referrer")+" and status:"+request.getAttribute("status"));
        }
    }

}
