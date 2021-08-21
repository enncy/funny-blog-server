package cn.enncy.funny.pojo;


import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 邮箱验证码验证器
 * @author: enncy
 */

@Data
@Component
public class EmailValidator {


    /** 验证码 */
    private String code;
    /** 时间 */
    private Long time;
    /** 默认的超时时间 */
    public static final long DEFAULT_LIMIT_TIME = 60 * 1000;

    public EmailValidator() {
        this.time = System.currentTimeMillis();
    }


    /** 验证码是否超出 limit 指定的时间 */
    public boolean isOutOfTime(long limit){
        return System.currentTimeMillis() - this.time > limit;
    }
}
