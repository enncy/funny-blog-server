package cn.enncy.funny.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Blog对象", description="文章表")
public class Blog extends BaseEntity<Blog> {

    @ApiModelProperty(value = "用户id",example = "1")
    private Long userId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "标签")
    private String tags;

    @ApiModelProperty(value = "原文链接")
    private String originUrl;

    @ApiModelProperty(value = "标题")
    private String title;


    @ApiModelProperty(value = "逻辑删除", hidden = true )
    @TableLogic
    private Integer deleted;



}
