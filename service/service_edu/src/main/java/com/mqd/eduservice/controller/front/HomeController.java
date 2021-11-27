package com.mqd.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.pojo.EduCourse;
import com.mqd.eduservice.pojo.EduTeacher;
import com.mqd.eduservice.service.EduCourseService;
import com.mqd.eduservice.service.EduTeacherService;
import com.mqd.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 首页数据展示
 */
@RestController
@RequestMapping("/eduservice/home")
@CrossOrigin
public class HomeController {
    @Resource
    private EduTeacherService teacherService;

    @Resource
    private EduCourseService courseService;

    @ApiOperation(value="获取9条热门课程数据和6条讲师数据",notes = "获取9条根据观看量排序热门课程数据，6条根据sort字段排序的讲师数据")
    @GetMapping("/home")
    public Result getHomeData(){
        return Result.ok().addData("hotCourse",courseService.getHotCourse())
                .addData("hotTeacher",teacherService.getHotTeacher());
    }
}
