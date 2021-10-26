package com.mqd.eduservice.pojo.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@ApiModel("subject查询类")
@Data
public class SubjectQuery {

    @ApiParam("父id")
    private String parentId;
}
