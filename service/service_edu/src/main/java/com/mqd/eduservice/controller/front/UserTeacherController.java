package com.mqd.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.pojo.EduTeacher;
import com.mqd.eduservice.service.EduTeacherService;
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

/**
 * 获取教师
 */
@Api(tags = "前台教师api")
@RestController
@RequestMapping("/api/edu/service")
public class UserTeacherController {

    @Resource
    private EduTeacherService teacherService;

    @ApiOperation(value = "获取教师")
    @GetMapping("/teacher")
    public Result getTeacher(EduTeacher teacher, Long current, Long size) throws CustomException {
        //如果teacher带有id直接返回
        String id = teacher.getId();
        if (StringUtils.hasText(id)){
            EduTeacher byId = teacherService.getById(id);
            if (byId == null){
                throw new CustomException("教师"+id+"不存在");
            }
            return Result.ok().addData("teacher",byId);
        }
        //无id
        QueryWrapper<EduTeacher> teacherQuery = new QueryWrapper<>();
        if (current == null || current < 1){
            current = 1L;
        }
        if (size == null || size<10 || size>50){
            size = 20L;
        }
        teacherQuery.orderByDesc("sort");
        IPage<EduTeacher> page = new Page<>(current,size);
        page = teacherService.page(page, teacherQuery);
        return Result.ok().addData("teacherList",page.getRecords()).addData("pageInfo",new PageInfo(page));
    }

}
