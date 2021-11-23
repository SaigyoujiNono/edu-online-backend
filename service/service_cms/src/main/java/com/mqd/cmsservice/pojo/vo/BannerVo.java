package com.mqd.cmsservice.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "banner查询视图")
@Data
public class BannerVo {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "是否按照排序字段排序，true升序，false降序，null不按照排序字段")
    private Boolean sort;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间开始")
    private Date startCreate;

    @ApiModelProperty(value = "创建时间结束")
    private Date endCreate;

    @ApiModelProperty(value = "更新时间开始")
    private Date startModified;

    @ApiModelProperty(value = "更新时间结束")
    private Date endModified;

    @ApiModelProperty(value = "单页大小")
    private Long pageSize;

    @ApiModelProperty(value = "当前页")
    private Long current;
}
