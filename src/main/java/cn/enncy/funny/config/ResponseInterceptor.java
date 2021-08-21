package cn.enncy.funny.config;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.pojo.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 统一请求返回处理
 *
 * @author: enncy
 */

@Slf4j
@RestControllerAdvice
public class ResponseInterceptor implements ResponseBodyAdvice<Object> {

    HttpServletResponse response;

    public ResponseInterceptor(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        HttpStatus status = Optional.ofNullable(HttpStatus.resolve(response.getStatus())).orElse(HttpStatus.NOT_FOUND);

        Result<Object> result = new Result<>();
        // 设置响应码
        result.setStatus(response.getStatus());
        boolean error = status.isError();
        // 设置请求是否成功
        result.setSuccess(!error);

        if (error) {

            if(o instanceof ServiceException){
                ServiceException exception = (ServiceException) o;
                // 响应数据置空
                result.setData(exception.getDetail());
                // 响应错误信息
                result.setMsg(exception.getMessage());
            }else{
                // 响应数据置空
                result.setData(null);
                // 响应错误信息
                result.setMsg(o.toString());
            }
            return result;
        } else if (methodParameter.getDeclaringClass().isAnnotationPresent(ResponseHandler.class)) {
            Method method = methodParameter.getMethod();
            if (method == null) {
                return o;
            } else {
                // 如果存在 Api 注释，则返回注释和其他信息，否则返回简单的"请求" 描述 请求成功或者失败
                String preMsg = Optional.ofNullable(method.getAnnotation(ApiOperation.class)).map(ApiOperation::value).orElse("请求");
                //设置响应信息
                result.setMsg(preMsg + "成功");
                // 设置响应数据
                result.setData(o);

                return result;
            }

        } else {
            return o;
        }
    }
}