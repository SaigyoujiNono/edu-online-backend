package com.mqd.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect
@Slf4j
@Component
public class SysLogAspect {


    @Pointcut("execution(public * com.mqd.*.controller..*.*(..))")
    public void exec(){}

    @Around(value = "exec()")
    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        Object o = null;
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                .getRequest();
        String ipAdrress = getIpAdrress(request);
        try {
            long t1 = System.currentTimeMillis();

            o = proceedingJoinPoint.proceed();
            long t2 = System.currentTimeMillis();
            long procTime = t2-t1;
            if (procTime<200){
                log.info("from {}:exec <{}>-<{}>:{}ms",ipAdrress,proceedingJoinPoint.getTarget(),proceedingJoinPoint.getSignature().getName(),procTime);
            }else {
                log.warn("from {}:exec <{}>-<{}>:{}ms",ipAdrress,proceedingJoinPoint.getTarget(),proceedingJoinPoint.getSignature().getName(),procTime);
            }
        } catch (Throwable e) {
            log.error("from {}:exec <{}>-<{}> ERROR",ipAdrress,proceedingJoinPoint.getTarget(),proceedingJoinPoint.getSignature().getName());
            e.printStackTrace();
        }
        return o;
    }

    public String getIpAdrress(HttpServletRequest request) {
        String ip = null;

        //X-Forwarded-For：Squid 服务代理
        String ipAddresses = request.getHeader("X-Forwarded-For");
        String unknown = "unknown";
        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //Proxy-Client-IP：apache 服务代理
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //WL-Proxy-Client-IP：weblogic 服务代理
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //HTTP_CLIENT_IP：有些代理服务器
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddresses == null || ipAddresses.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            //X-Real-IP：nginx服务代理
            ipAddresses = request.getHeader("X-Real-IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr();获取
        if (ip == null || ip.length() == 0 || unknown.equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
