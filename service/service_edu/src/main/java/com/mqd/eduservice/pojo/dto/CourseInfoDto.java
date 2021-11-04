package com.mqd.eduservice.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value="课程基本信息DTO", description="课程发布前的基本信息")
public class CourseInfoDto {

    @ApiModelProperty(value = "课程id")
    private String id;

    @ApiModelProperty(value = "课程封面的url")
    private String cover;

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "老师的名字")
    private String teacherName;

    @ApiModelProperty(value = "课程分类的父分类")
    private String subjectParentName;

    @ApiModelProperty(value = "课程分类的名称")
    private String subjectName;

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;
}
