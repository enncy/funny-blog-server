package cn.enncy.funny.entity;

import cn.enncy.funny.constant.Role;
import cn.enncy.funny.exceptions.ServiceException;
import cn.enncy.funny.utils.StringUtils;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@ApiModel(value = "用户", description = "用户表")
public class User extends BaseEntity  {

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

    @ApiModelProperty(value = "逻辑删除", hidden = true )
    @TableLogic
    private Integer deleted;

    public void setRole(String role) {
        this.role = Role.parseToRole(role).value;
    }
}
