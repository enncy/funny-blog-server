package cn.enncy.funny.entity;


import cn.enncy.funny.entity.User;
import cn.enncy.funny.exceptions.ServiceException;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * //TODO
 * <br/>Created in 20:20 2021/8/16
 *
 * @author: enncy
 */

@Data
@ApiModel(value = "基础实体", description = "基础实体对象")
public class BaseEntity  {

    @ApiModelProperty(value = "自增主键", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing =  ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "乐观锁", hidden = true)
    @Version
    private Integer version;

    @ApiModelProperty(value = "插入时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "更新时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

}
