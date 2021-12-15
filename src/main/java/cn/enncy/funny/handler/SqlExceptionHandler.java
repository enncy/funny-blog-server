package cn.enncy.funny.handler;


import cn.enncy.funny.annotation.HandleSqlException;
import cn.enncy.funny.entity.BaseEntity;
import cn.enncy.funny.utils.ClassScanner;
import cn.enncy.funny.utils.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql 错误异常处理
 *
 * @author enncy
 */
@Component
public class SqlExceptionHandler {

    /**
     * 分发异常处理
     *
     * @return: void
     */
    public String handle(SQLException e) {
        // 获取当前类的方法
        Optional<Method> first = Arrays.stream(SqlExceptionHandler.class.getDeclaredMethods())
                // 寻找有 HandleSqlException 注解的方法
                .filter(m -> m.isAnnotationPresent(HandleSqlException.class))
                // 寻找和参数 e 的类相等的 HandleSqlException
                .filter(m -> m.getAnnotation(HandleSqlException.class).value() == e.getClass())
                .findFirst();

        // 如果存在，则分发给指定的方法
        if (first.isPresent()) {
            try {
                return (String) first.get().invoke(this, e);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                return "服务器内部出现异常";
            }
        } else {
            return "服务器内部出现异常";
        }
    }


    /**
     * 完整性约束被破坏处理
     *
     * @return: java.lang.String
     */
    @HandleSqlException(SQLIntegrityConstraintViolationException.class)
    public String integrityConstraint(SQLException e) {
        // 匹配数据库报错信息
        String regex = "Duplicate entry '(.*?)' for key '(.*?)\\..*?_(.*?)_uindex'";
        Matcher matcher = Pattern.compile(regex).matcher(e.getMessage());
        // 如果存在信息
        if (matcher.find()) {
            // 获取被占用的值
            String value = matcher.group(1);
            // 获取数据库表
            String table = matcher.group(2);
            // 获取发生冲突的约束键
            String keys = matcher.group(3);
            // 扫描实体类
            List<Class<?>> scan = ClassScanner.scan("cn.enncy.funny.entity");
            // 寻找冲突的字段的注解描述
            String description = scan.stream()
                    .filter(BaseEntity.class::isAssignableFrom)
                    .filter(c -> c.getName().toLowerCase().contains(table))
                    .map(c -> {
                        // 获取实体类的属性值
                        Field[] declaredFields = c.getDeclaredFields();
                        for (Field declaredField : declaredFields) {
                            // 驼峰转下划线
                            String name = StringUtils.humpToUnderline(declaredField.getName());
                            // 如果属性包含在 keys 的值里面
                            if (keys.contains(name)) {
                                // 返回属性上面的 注解信息
                                return declaredField.getAnnotation(ApiModelProperty.class).value();
                            }
                        }
                        return "";
                    }).findFirst().orElse("");

            return description + " " + value + " 已经被占用";
        }
        return "信息已经被占用";
    }


}


