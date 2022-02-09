package com.mqd.eduservice.pojo.vo;

import com.mqd.eduservice.pojo.EduTeacher;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
@ApiModel("返回给前端的课程基本信息与描述")
public class CourseFront {

    @ApiModelProperty(value = "课程价格")
    private BigDecimal price;

    @ApiModelProperty(value = "课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "课程ID")
    private String id;

    @ApiModelProperty(value = "课程讲师信息")
    private EduTeacher teacher;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("课程目录")
    private List<ChapterInfoVo> chapter;

    @ApiModelProperty(value = "课程封面的url")
    private String cover;
}
