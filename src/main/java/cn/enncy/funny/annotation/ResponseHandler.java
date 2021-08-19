package cn.enncy.funny.annotation;


import java.lang.annotation.*;

/**
 * 统一响应处理注解
 * @author: enncy
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseHandler {
}
