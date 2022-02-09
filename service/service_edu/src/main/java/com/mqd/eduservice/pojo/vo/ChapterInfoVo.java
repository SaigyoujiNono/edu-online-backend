package com.mqd.eduservice.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mqd.eduservice.pojo.EduVideo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class ChapterInfoVo {
    @ApiModelProperty(value = "章节ID")
    private String id;

    @ApiModelProperty(value = "章节名称")
    private String title;

    @ApiModelProperty(value = "显示排序")
    private Integer sort;

    @ApiModelProperty(value = "课程ID")
    private String courseId;

    @ApiModelProperty(value = "小节")
    private List<EduVideo> children = new ArrayList<>();

    public ChapterInfoVo add(EduVideo v){
        this.children.add(v);
        return this;
    }
}
