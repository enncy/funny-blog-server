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

    @ApiModelProperty(value = "逻辑删除", hidden = true )
    @TableLogic
    private Integer deleted;

    public void setPassword(String password) throws ServiceException {
        if(StringUtils.isEmpty(password)){
            throw new ServiceException("密码不能为空");
        }
        if(StringUtils.outOfRange(password,6,20)){
            throw new ServiceException("密码至少 6-20 个字符");
        }
        if(StringUtils.hasSpecialCharacters(password)){
            throw new ServiceException("密码不能存在特殊字符");
        }
        this.password = password;
    }

    public void setNickName(String nickName) throws ServiceException {
        if(StringUtils.notEmpty(password) && StringUtils.lengthConverter(nickName) > 20){
            throw new ServiceException("昵称不能大于20个字符(中文占2个)");
        }
        this.nickName = nickName;
    }

    public void setAccount(String account) throws ServiceException {

        if(StringUtils.isEmpty(account)){
            throw new ServiceException("密码不能为空");
        }
        if(StringUtils.outOfRange(account,4,16,true)){
            throw new ServiceException("账号至少 4-16 个字符 (中文占2个)");
        }
        if(StringUtils.hasSpecialCharacters(account)){
            throw new ServiceException("账号不能存在特殊字符");
        }
        this.account = account;
    }

    public void setRole(String role) {
        this.role = Role.parseToRole(role).value;
    }
}
