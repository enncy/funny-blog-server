package cn.enncy.funny.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户信息详情表
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="UserInfo对象", description="用户信息详情表")
public class UserInfo extends BaseEntity<UserInfo> {

    @ApiModelProperty(value = "用户id",example = "1")
    private Long userId;

    @ApiModelProperty(value = "个人简介")
    private String profile;

    @ApiModelProperty(value = "感兴趣的标签")
    private String favorTags;

    @ApiModelProperty(value = "年龄",example = "18")
    private Integer age;

    @ApiModelProperty(value = "学校")
    private String school;

    @ApiModelProperty(value = "专业")
    private String major;

    @ApiModelProperty(value = "公司")
    private String company;

    @ApiModelProperty(value = "职位")
    private String position;

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
