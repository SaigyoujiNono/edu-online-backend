package com.mqd.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.pojo.EduCourse;
import com.mqd.eduservice.service.EduCourseService;
import com.mqd.result.PageInfo;
import com.mqd.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 课程获取
 */
@RestController
@RequestMapping("/api/edu/service")
public class UserCourseController {
    @Resource
    private EduCourseService courseService;

    @ApiOperation(value = "获取课程")
    @GetMapping("/course")
    public Result getSubject(EduCourse course,Long current, Long size){
        String subjectParentId = course.getSubjectParentId();
        String subjectId = course.getSubjectId();
        QueryWrapper<EduCourse> courseQuery = new QueryWrapper<>();
        if (current == null || current < 1){
            current = 1L;
        }
        if (size == null || size<10 || size>50){
            size = 20L;
        }
        IPage<EduCourse> page = new Page<>(current,size);
        //只能获取已发布课程
        courseQuery.eq("status","Normal");
        if(StringUtils.hasText(subjectParentId)){
            courseQuery.eq("subject_parent_id",subjectParentId);
        }
        if(StringUtils.hasText(subjectId)){
            courseQuery.eq("subject_id",subjectId);
        }
        page = courseService.page(page,courseQuery);
        return Result.ok().addData("courseList",page.getRecords()).addData("pageInfo",new PageInfo(page));
    }
}
