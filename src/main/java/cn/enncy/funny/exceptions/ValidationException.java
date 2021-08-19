package cn.enncy.funny.exceptions;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 字段验证异常
 * @author: enncy
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class ValidationException extends ServiceException{

    private String paramName;
    private String exceptionCause;

    public ValidationException(String paramName,String cause) {
        super("字段"+paramName+"在验证时错误，原因 :"+cause);
        this.paramName = paramName;
        this.exceptionCause = cause;
    }



    @Override
    public String toString() {
        return this.exceptionCause;
    }
}
