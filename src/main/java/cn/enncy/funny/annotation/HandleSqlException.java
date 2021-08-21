package cn.enncy.funny.annotation;


/**
 * //TODO
 * <br/>Created in 9:31 2021/8/20
 *
 * @author: enncy
 */

import java.lang.annotation.*;
import java.sql.SQLException;

/**
 * @author enncy
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HandleSqlException {
    Class<? extends SQLException> value();
}
