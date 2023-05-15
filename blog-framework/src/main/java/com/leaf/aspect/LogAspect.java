package com.leaf.aspect;

import com.alibaba.fastjson.JSON;
import com.leaf.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 日志切面类
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.leaf.annotation.SystemLog)")
    public void pt(){

    }

    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object prd;
        try {
            handleBefore(joinPoint);
            prd = joinPoint.proceed();
            handleAfore(prd);
        } finally {
            log.info("=======End=======" + System.lineSeparator());
        }
        return prd;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {

        //获取系统对象
        ServletRequestAttributes requestAttributes
                = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取被增强的方法上面的对象
        SystemLog systemLog=getSystemLog(joinPoint);
        //打印
        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURI());
        // 打印描述信息
        log.info("BusinessName   : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    /**
     * 获取注解
     * @param joinPoint
     * @return
     */
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature();
        return methodSignature.getMethod().getAnnotation(SystemLog.class);
    }

    private void handleAfore(Object obj) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSON(obj));
    }

}
