package cn.enncy.funny.dto;


import cn.enncy.funny.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * //TODO
 * <br/>Created in 10:38 2021/8/18
 *
 * @author: enncy
 */

@Data
@ApiModel(value = "基础DTO对象",description = "基础DTO对象")
public class BaseDto<T extends BaseEntity<T>> {

    @ApiModelProperty(value = "唯一id字段",example = "0")
    private Long id;

    @ApiModelProperty("传输对象")
    private T target;


}
