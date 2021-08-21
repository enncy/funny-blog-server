package cn.enncy.funny.annotation;


import cn.enncy.funny.constant.Role;

import java.lang.annotation.*;

/**
 * //TODO
 * <br/>Created in 16:14 2021/8/20
 *
 * @author: enncy
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Roles {
    Role[] value() default Role.VISITOR;
}
