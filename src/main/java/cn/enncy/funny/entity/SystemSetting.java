package cn.enncy.funny.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * //TODO
 * <br/>Created in 19:15 2021/12/20
 *
 * @author enncy
 */


@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统设置", description = "系统设置表")
public class SystemSetting extends  BaseEntity{

    @ApiModelProperty("名字")
    private String name;

    @ApiModelProperty("内容")
    private String content;

}
