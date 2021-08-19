package cn.enncy.funny.exceptions;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * //TODO
 * <br/>Created in 21:40 2021/8/18
 *
 * @author: enncy
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ServiceException extends Exception {

    public String detail;

    public ServiceException(String message) {
        super(message);
        this.detail = null;
    }

    public ServiceException(String message, String detail) {
        super(message);
        this.detail = detail;
    }
}
