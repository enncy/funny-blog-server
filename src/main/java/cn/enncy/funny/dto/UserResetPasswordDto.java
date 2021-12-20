package cn.enncy.funny.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * //TODO
 * <br/>Created in 16:31 2021/12/17
 *
 * @author enncy
 */

@Data
@ApiModel(value = "重置密码表单DTO对象", description = "重置密码表单")
public class UserResetPasswordDto {
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty("新的密码")
    private String password;
    @ApiModelProperty("重复密码")
    private String confirmPassword;
}
