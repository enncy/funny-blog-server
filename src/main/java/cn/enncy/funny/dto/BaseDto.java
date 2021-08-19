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
@ApiModel("基础DTO对象")
public class BaseDto<T extends BaseEntity> {

    @ApiModelProperty("唯一id字段")
    public Integer id;

    @ApiModelProperty("传输对象")
    public T target;

}
