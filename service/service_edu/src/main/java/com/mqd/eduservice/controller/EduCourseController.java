package com.mqd.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqd.eduservice.pojo.EduCourse;
import com.mqd.eduservice.pojo.EduCourseDescription;
import com.mqd.eduservice.pojo.vo.CourseInfoVo;
import com.mqd.eduservice.service.EduCourseDescriptionService;
import com.mqd.eduservice.service.EduCourseService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-10-24
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduCourseDescriptionService descService;

    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("/courseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfo) throws CustomException {
        CourseInfoVo courseInfoVo = courseService.saveCourseInfo(courseInfo);
        return Result.ok().addData("courseInfo",courseInfoVo);
    }

    @ApiOperation(value = "更新课程基本信息")
    @PutMapping("/courseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfo) throws CustomException {
        CourseInfoVo courseInfoVo = courseService.updateCourseInfo(courseInfo);
        return Result.ok().addData("courseInfo", courseInfoVo);
    }

    @ApiOperation(value = "根据条件获取课程信息")
    @GetMapping("/courseInfo")
    public Result getCourseInfo(CourseInfoVo courseInfo) throws CustomException {
        CourseInfoVo res = new CourseInfoVo();
        String id = courseInfo.getId();
        if (StringUtils.hasText(id)){
            EduCourse byId = courseService.getById(courseInfo.getId());
            EduCourseDescription byId1 = descService.getById(courseInfo.getId());
            BeanUtils.copyProperties(byId,res);
            BeanUtils.copyProperties(byId1,res);
            return Result.ok().addData("courseInfo",res);
        }
        throw new CustomException("暂时不要这么查询");
    }

}

