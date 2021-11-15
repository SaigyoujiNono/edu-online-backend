package com.mqd.cmsservice.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "banner查询视图")
@Data
public class BannerVo {

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "排序")
    private Integer sort;

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
}
