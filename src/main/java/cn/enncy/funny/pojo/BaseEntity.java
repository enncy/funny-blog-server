package cn.enncy.funny.pojo;


import cn.enncy.funny.entity.User;
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
@ApiModel(value = "基础实体对象", description = "基础实体对象")
public class BaseEntity<T> {

    @ApiModelProperty(value = "自增主键", hidden = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing =  ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    public Long id;


    @ApiModelProperty(value = "乐观锁", hidden = true)
    @Version
    public Integer version;

    @ApiModelProperty(value = "逻辑删除", hidden = true)
    @TableLogic
    public Integer deleted;

    @ApiModelProperty(value = "插入时间", hidden = true)
    @TableField(fill = FieldFill.INSERT)
    public Long createTime;

    @ApiModelProperty(value = "更新时间", hidden = true)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    public Long updateTime;

    public T filter(){
        this.setVersion(null);
        this.setDeleted(null);
        this.setId(null);
        return (T) this;
    }
}
