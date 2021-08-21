package cn.enncy.funny.controller;


import cn.enncy.funny.annotation.ResponseHandler;
import cn.enncy.funny.config.HttpErrorStateConverter;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.handler.SqlExceptionHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * //TODO
 * <br/>Created in 11:16 2021/8/18
 *
 * @author: enncy
 */
@RestController
@ResponseHandler
@RestControllerAdvice
@Api(tags = "統一错误请求控制")
public class MyErrorController implements ErrorController {

    SqlExceptionHandler sqlExceptionHandler;

    public MyErrorController(SqlExceptionHandler sqlExceptionHandler) {
        this.sqlExceptionHandler = sqlExceptionHandler;
    }

    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Throwable error(Exception e) {
        return findCause(e);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String notReadable() {
        return "错误的参数!";
    }


    /**
     *  sql 异常捕获
     * @return: java.lang.String
     */
    @ExceptionHandler(value = SQLException.class)
    @ResponseStatus
    public String sqlError(SQLException e) {
        return sqlExceptionHandler.handle(e);
    }

    /**
     * 递归查找原因
     */
    public <T extends Throwable> Throwable findCause(T throwable) {
        return throwable.getCause() != null ? findCause(throwable.getCause()) : throwable;
    }


    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    @ApiOperation("错误显示")
    public String handle(@RequestParam("status") int status) {

        HttpStatus httpStatus = HttpStatus.resolve(status);
        if (httpStatus != null) {
            // 返回转换过的响应信息
            HttpErrorStateConverter resolver = HttpErrorStateConverter.resolve(httpStatus.value());
            return resolver == null ? httpStatus.toString() : resolver.description;
        } else {
            // 返回 404 信息
            return HttpErrorStateConverter.NOT_FOUND.description;
        }
    }


}
