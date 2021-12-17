package cn.enncy.funny.aspect;


import cn.enncy.funny.annotation.Roles;
import cn.enncy.funny.constant.Role;
import cn.enncy.funny.entity.User;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.task.TokenTask;
import cn.enncy.funny.utils.SecurityUtils;
import cn.enncy.funny.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * //TODO
 * <br/>Created in 17:25 2021/8/20
 *
 * @author: enncy
 */
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServiceException, IOException {

        if (handler instanceof HandlerMethod) {
            Method method = ((HandlerMethod) handler).getMethod();

            if (ErrorController.class.isAssignableFrom(method.getDeclaringClass())) {
                return true;
            } else {
                // 获取 post 参数
                Map<String, Object> postParams = parsePostRequest(request);
                // 获取 get 参数
                Map<String, Object> getParams = parseGetRequest(request);

                String token = Stream.of( request.getHeader("token"),
                        getParams.get("token"),
                        postParams.get("token"))
                        .filter(Objects::nonNull)
                        .map(Object::toString).findAny().orElse(null);
                String time = Stream.of( request.getHeader("time"),
                        getParams.get("time"),
                        postParams.get("time"))
                        .filter(Objects::nonNull)
                        .map(Object::toString).findAny().orElse(null);

                // 跟前端一样合并 get 和 post 参数，并排除 token 和 time 字段
                postParams.putAll(getParams);
                postParams.remove("token");
                postParams.remove("time");
                // 检测 token 信息
                if (!checkToken(token, time, postParams)) {
                    throw new ServiceException("请求异常");
                }

                if (method.isAnnotationPresent(Roles.class)) {
                    Roles annotation = method.getAnnotation(Roles.class);
                    Role[] value = annotation.value();
                    ;
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


        }

        return true;
    }



    // 解析 get 请求参数，并生成 map
    public static Map<String, Object> parseGetRequest(HttpServletRequest request) {
        Map<String, Object> map = new LinkedHashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String element = parameterNames.nextElement();
            map.put(element, request.getParameter(element));
        }
        return map;
    }

    // 解析 post 请求参数，并生成 map
    public static Map<String, Object> parsePostRequest(HttpServletRequest req) throws IOException {
        //包装成自定义的Request, 就可以多次的获取requestBody
        RequestWrapper requestWrapper = new RequestWrapper(req);
        BufferedReader reader = new BufferedReader(new InputStreamReader(requestWrapper.getInputStream()));
        return JSON.parseObject(Optional.ofNullable(reader.readLine()).orElse("{}"));
    }


    /**
     * 请求检验
     */
    public static boolean checkToken(String token, String time, Map<String, Object> map) throws IOException {
        if (StringUtils.notEmpty(time) && StringUtils.notEmpty(token)) {

            // 判断请求是否超时
            long l = Long.parseLong(time);
            if (System.currentTimeMillis() - l > TimeUnit.MINUTES.toMillis(10)) {
                return false;
            }

            // 升序排序 json 对象
            String encryption = SecurityUtils.MD5.encryption(JSON.toJSONString(map, SerializerFeature.MapSortField), l);
            // 判断是否篡改数据
            if (token.equals(encryption)) {
                // 全部都不一样 则不是重放攻击
                if (TokenTask.TOKENS.stream().noneMatch(t -> t.equals(token))) {
                    // 添加 token 防止重放攻击
                    TokenTask.TOKENS.add(token);
                    return true;
                }
            }
        }
        return false;
    }


}
