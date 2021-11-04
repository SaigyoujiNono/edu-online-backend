package com.mqd.eduservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.pojo.EduCourse;
import com.mqd.eduservice.pojo.EduCourseDescription;
import com.mqd.eduservice.pojo.dto.CourseInfoDto;
import com.mqd.eduservice.pojo.vo.CourseInfoVo;
import com.mqd.eduservice.pojo.vo.CourseQuery;
import com.mqd.eduservice.service.EduCourseDescriptionService;
import com.mqd.eduservice.service.EduCourseService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String id = courseInfo.getId();
        if (StringUtils.hasText(id)){
            CourseInfoVo res = new CourseInfoVo();
            EduCourse byId = courseService.getById(courseInfo.getId());
            EduCourseDescription byId1 = descService.getById(courseInfo.getId());
            BeanUtils.copyProperties(byId,res);
            BeanUtils.copyProperties(byId1,res);
            return Result.ok().addData("courseInfo",res);
        }
        throw new CustomException("暂时不要这么查询");
    }

    @ApiOperation(value = "获取课程即将发布前的基本信息")
    @GetMapping("/publishCourse/{id}")
    public Result getBeforePublishCourse(@PathVariable String id) throws CustomException {
        CourseInfoDto beforePublishCourse = courseService.getBeforePublishCourse(id);
        if (beforePublishCourse==null){
            throw new CustomException("该课程不存在");
        }
        return Result.ok().addData("beforePublish",beforePublishCourse);
    }

    @ApiOperation(value = "发布课程接口",notes = "isPub为1时则发布课程，isPub为0时取消发布")
    @PutMapping("/publish/{isPub}/{id}")
    public Result publishCourse(@PathVariable Integer isPub, @PathVariable String id) throws CustomException {
        EduCourse byId = courseService.getById(id);
        if (byId==null){
            throw new CustomException("该课程不存在");
        }
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        if (isPub == 0){
            eduCourse.setStatus("Draft");
        }else {
            eduCourse.setStatus("Normal");
        }
        courseService.updateById(eduCourse);
        return Result.ok();
    }

    @ApiOperation(value = "获取课程列表的接口，每页10条信息，可以传入查询参数",notes = "page表示当前页")
    @GetMapping("/getCourseList")
    public Result getCourseList(Long current, Long pageSize, CourseQuery courseQuery) {
        Page<CourseInfoDto> pageInfo = new Page<>(1,10);
        if (current != null){
            pageInfo.setCurrent(current);
        }
        if ( pageSize != null){
            pageInfo.setSize(pageSize);
        }
        IPage<CourseInfoDto> courseListByQuery = courseService.getCourseListByQuery(courseQuery, pageInfo);
        List<CourseInfoDto> records = courseListByQuery.getRecords();
        long total = courseListByQuery.getTotal();
        long pages = courseListByQuery.getPages();
        current = courseListByQuery.getCurrent();
        long size = courseListByQuery.getSize();
        Map<String,Object> info = new HashMap<>();
        info.put("total",total);
        info.put("pages",pages);
        info.put("current",current);
        info.put("size",size);
        return Result.ok().addData("courseList",records)
                .addData("pageInfo",info);
    }

}

