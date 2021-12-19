package com.mqd.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.pojo.EduCourse;
import com.mqd.eduservice.pojo.EduCourseDescription;
import com.mqd.eduservice.pojo.vo.ChapterInfoVo;
import com.mqd.eduservice.service.EduChapterService;
import com.mqd.eduservice.service.EduCourseDescriptionService;
import com.mqd.eduservice.service.EduCourseService;
import com.mqd.exception.CustomException;
import com.mqd.result.PageInfo;
import com.mqd.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程获取
 */
@Api(tags = "前台课程api")
@RestController
@RequestMapping("/api/edu/service")
public class UserCourseController {
    @Resource
    private EduCourseService courseService;

    @Resource
    private EduCourseDescriptionService courseDescriptionService;

    @Resource
    private EduChapterService chapterService;

    @ApiOperation(value = "获取课程")
    @GetMapping("/course")
    public Result getSubject(EduCourse course,Long current, Long size) throws CustomException {
        //如果带有id则直接返回
        String id = course.getId();
        if (StringUtils.hasText(id)){
            EduCourse courseById = courseService.getById(id);
            if (courseById == null){
                throw new CustomException("课程id:"+id+" => 不存在");
            }
            return Result.ok().addData("course",courseById);
        }
        //不带id正常分页查询
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

    @ApiOperation(value = "获取课程描述")
    @GetMapping("/coursedesc")
    public Result getCourseDesc(String id) throws CustomException {
        EduCourseDescription byId = courseDescriptionService.getById(id);
        if (byId == null){
            throw new CustomException("课程描述不存在");
        }
        return Result.ok().addData("courseDesc",byId);
    }

    @ApiOperation(value = "根据id获取课程所有章节与小节")
    @GetMapping("/coursechapter")
    public Result getCourseChapter(String id) throws CustomException {
        List<ChapterInfoVo> chapterAllByCourseId = chapterService.getChapterAllByCourseId(id);
        if (chapterAllByCourseId == null){
            throw new CustomException("课程id"+id+"不存在");
        }
        if (chapterAllByCourseId.size() == 0){
            throw new CustomException("课程id"+id+"不存在");
        }
        return Result.ok().addData("courseChapter",chapterAllByCourseId);
    }

}
