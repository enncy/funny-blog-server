package cn.enncy.funny.entity;

import cn.enncy.funny.pojo.BaseEntity;
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
public class User extends BaseEntity<User> {

    @ApiModelProperty("密码")
    private String password;


    @ApiModelProperty("账号")
    private String account;


    @ApiModelProperty("邮箱")
    private String email;


    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty(value = "权限", hidden = true)
    private String role;


    @Override
    public User filter() {
        this.setPassword(null);
        return super.filter();
    }
}
