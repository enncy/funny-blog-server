package cn.enncy.funny.aspect;


import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * //TODO
 * <br/>Created in 13:16 2021/8/21
 *
 * @author: enncy
 */


@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Autowired
    HttpServletRequest request;



    @Around("execution(* cn.enncy.funny.controller.service.*.*(..))")
    public Object handler(ProceedingJoinPoint pjp) throws Throwable {
        log.info("#--------------------------------#");
        log.info("# 请求路径 : " + request.getMethod() + " " + StringUtils.getRequestBaseUrl(request) + request.getServletPath());
        log.info("# 处理程序 : " + pjp.getTarget());

        Method[] methods = pjp.getTarget().getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals(pjp.getSignature().getName())) {
                String description = method.getAnnotation(ApiOperation.class).value();
                String roles = "游客";
                Roles annotation = method.getAnnotation(Roles.class);
                if(annotation!=null){
                    roles = Arrays.toString(annotation.value());
                }

                log.info("# 处理接口 : "+ "描述=" + description + ", 所需身份=" + roles + "  " + method.getName() + Arrays.toString(method.getParameters()));
            }
        }

        log.info("# 请求参数 : " + Arrays.toString(pjp.getArgs()));

        try {
            Object proceed = pjp.proceed(pjp.getArgs());
            log.info("# 返回值 :" + proceed);
            return proceed;
        } catch (Throwable e) {
            log.info("# 异常抛出 : " + e.getMessage());
            throw e;
        } finally {
            log.info("#--------------------------------#");
        }
    }

}
