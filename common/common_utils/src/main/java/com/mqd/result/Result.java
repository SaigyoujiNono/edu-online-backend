package com.mqd.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
@ApiModel(description = "返回结果格式")
public class Result {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "状态消息")
    private String message;

    @ApiModelProperty(value = "数据集")
    private final Map<String, Object> data = new HashMap<>();

    private Result() {
    }

    public Result addData(String key, Object val){
        data.put(key, val);
        return this;
    }

    public static Result ok(){
        return new Result().setSuccess(true);
    }

    public static Result failed(){
        return new Result().setSuccess(false);
    }
}
