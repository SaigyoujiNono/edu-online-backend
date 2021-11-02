package com.mqd.eduservice.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChapterAndVideo {
    @ApiModelProperty(value = "章节ID")
    private String id;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    @ApiModelProperty(value = "视频ID")
    private String videoId;

    @ApiModelProperty(value = "节点名称")
    private String videoTitle;

    @ApiModelProperty(value = "排序字段")
    private Integer videoSort;

}
