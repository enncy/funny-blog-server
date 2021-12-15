package cn.enncy.funny.bean;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * //TODO
 * <br/>Created in 11:10 2021/8/17
 *
 * @author: enncy
 */

@Data
@ApiModel("统一数据返回模型")
public class Result<T> {

    @ApiModelProperty(value = "响应值")
    private int status;

    @ApiModelProperty(value = "请求是否成功")
    private boolean success;

    @ApiModelProperty(value = "响应信息")
    private String msg;

    @ApiModelProperty(value = "响应数据")
    private T data;
}
