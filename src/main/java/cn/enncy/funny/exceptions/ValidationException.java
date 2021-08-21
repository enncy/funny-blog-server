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

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, String detail) {
        super(message, detail);
    }
}
