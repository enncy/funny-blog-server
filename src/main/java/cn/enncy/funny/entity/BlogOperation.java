package cn.enncy.funny.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户操作文章信息表
 * </p>
 *
 * @author enncy
 * @since 2021-08-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="BlogOperation对象", description="用户操作文章信息表")
public class BlogOperation extends BaseEntity<BlogOperation> {

    @ApiModelProperty(value = "文章id",example = "1")
    private Long blogId;

    @ApiModelProperty(value = "操作的用户id",example = "1")
    private Long userId;

    @ApiModelProperty(value = "点赞")
    private Boolean favor;

    @ApiModelProperty(value = "反对")
    private Boolean oppose;

    @ApiModelProperty(value = "收藏")
    private Boolean collect;

    @ApiModelProperty(value = "观看")
    private Boolean watch;

}
