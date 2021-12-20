package cn.enncy.funny.annotation;

import java.lang.annotation.*;

/**
 * 在方法上注解，表示这个接口不采用 token 检测，直接返回数据
 * <br/>Created in 15:42 2021/12/19
 *
 * @author enncy
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenApi {
}
