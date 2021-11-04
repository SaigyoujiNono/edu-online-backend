package com.mqd.eduservice.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseQuery {

    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;

    @ApiModelProperty(value = "课程状态 Draft未发布  Normal已发布")
    private String status;

    @ApiModelProperty("课程分类id")
    private String subjectId;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("课程价格最小值")
    private BigDecimal priceMin;

    @ApiModelProperty("课程价格最大值")
    private BigDecimal priceMax;

    @ApiModelProperty(value = "销售数量最小值")
    private Long minBuyCount;

    @ApiModelProperty(value = "销售数量最大值")
    private Long maxBuyCount;

    @ApiModelProperty(value = "浏览数量最小值")
    private Long minViewCount;

    @ApiModelProperty(value = "浏览数量最大值")
    private Long maxViewCount;

}
