package cn.enncy.funny.aspect;


import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 全局跨域配置
 * <br/>Created in 17:07 2021/12/19
 *
 * @author enncy
 */
public class CorsInterceptor  implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String origin = Optional.ofNullable(request.getHeader("Origin")).orElse(Optional.ofNullable(request.getHeader("origin")).orElse("*"));
        response.addHeader("Access-Control-Allow-Origin", origin);
        response.addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "time,token,content-type,accept");
        response.addHeader("Access-Control-Allow-Credentials","true"); // 允许携带验证信息
        return true;
    }
}
