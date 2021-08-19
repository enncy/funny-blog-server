package cn.enncy.funny.exceptions;


/**
 * //TODO
 * <br/>Created in 21:36 2021/8/18
 *
 * @author: enncy
 */
public class RequestException extends ServiceException{
    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, String detail) {
        super(message, detail);
    }
}
