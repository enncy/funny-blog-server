package cn.enncy.funny.aspect;


import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.entity.User;
import cn.enncy.funny.exceptions.ServiceException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * 请求过滤器
 * <br/>Created in 17:25 2021/8/20
 *
 * @author: enncy
 */
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServiceException, IOException {

        // 如果是接口请求
        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();

            // 如果需要权限认证
            if (method.isAnnotationPresent(Roles.class) || method.getDeclaringClass().isAnnotationPresent(Roles.class)) {
                Roles annotation = Optional.ofNullable(method.getDeclaringClass().getAnnotation(Roles.class)).orElse(method.getAnnotation(Roles.class));
                Role[] value = annotation.value();

                // 如果不存在游客权限，则代表此接口需要验证
                if (Arrays.stream(value).anyMatch(r -> r == Role.VISITOR)) {
                    return true;
                } else {
                    User user = (User) request.getSession().getAttribute("user");
                    if (user == null) {
                        throw new ServiceException("未登录！");
                    } else {
                        Role role = Role.parseToRole(user.getRole());
                        System.out.println(role.value + "|" + Arrays.toString(value));
                        if (!Role.verifyRole(value, role)) {
                            throw new ServiceException("权限不足,禁止访问！");
                        }
                        return true;
                    }
                }

            }

        }

        return true;
    }


}
