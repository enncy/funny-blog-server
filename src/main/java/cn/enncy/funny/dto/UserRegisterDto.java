package cn.enncy.funny.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * //TODO
 * <br/>Created in 18:06 2021/12/17
 *
 * @author enncy
 */

@Data
@ApiModel(value = "注册表单", description = "注册表单")
public class UserRegisterDto {
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty("新的密码")
    private String password;
    @ApiModelProperty("重复密码")
    private String confirmPassword;
}
