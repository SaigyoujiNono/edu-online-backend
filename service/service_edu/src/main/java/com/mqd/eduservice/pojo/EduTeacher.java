package com.mqd.eduservice.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 讲师
 * </p>
 *
 * @author mqd
 * @since 2021-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="EduTeacher对象", description="讲师")
public class EduTeacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @NotBlank
    @Size(min = 2,max = 20,message = "字符长度必须在2-20")
    @ApiModelProperty(value = "讲师姓名")
    private String name;

    @NotBlank
    @Size(min = 3,max = 500,message = "字符长度必须在3-500")
    @ApiModelProperty(value = "讲师简介")
    private String intro;

    @NotBlank
    @Size(max = 500,message = "字符长度必须在1-500")
    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String career;

    @NotBlank
    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @NotBlank
    @ApiModelProperty(value = "讲师头像")
    private String avatar;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic //逻辑删除时一定要加这个注解
    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    private Boolean isDeleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
