package cn.enncy.funny.entity;

import cn.enncy.funny.constant.EmailType;
import cn.enncy.funny.exceptions.ValidationException;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author enncy
 * @since 2021-08-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "User对象", description = "用户表")
public class User extends BaseEntity {

    @JSONField(serialize = false)
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("qq邮箱")
    private String email;

    @ApiModelProperty("昵称")
    private String nickName;


    public void setEmail(String email) throws Exception {
        if(email.contains(EmailType.Domain.QQ.value)){
            this.email = email;
        }else{
            throw new ValidationException("email","不支持的邮箱后缀名");
        }

    }
}
