package com.mqd.eduservice.controller;


import com.mqd.eduservice.pojo.vo.CourseInfoVo;
import com.mqd.eduservice.service.EduCourseService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-10-24
 */
@RestController
@RequestMapping("/eduservice/edu-course")
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;


    @PostMapping("/courseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfo) throws CustomException {
        CourseInfoVo courseInfoVo = courseService.saveCourseInfo(courseInfo);
        return Result.ok().addData("courseInfo",courseInfoVo);
    }

}

