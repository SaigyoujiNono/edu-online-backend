package com.mqd.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.pojo.EduTeacher;
import com.mqd.eduservice.pojo.vo.TeacherQuery;
import com.mqd.eduservice.service.EduTeacherService;
import com.mqd.result.Result;
import com.mqd.result.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-10-12
 */
@Api(tags = "老师管理")
@RestController
@RequestMapping("/eduservice")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有的老师信息
     * @return  返回所有老师的信息
     */
    @ApiOperation(value = "查询老师", notes = "查询所有老师信息。")
    @GetMapping("/teacher/{page}")
    public Result getTeacher(@PathVariable Long page,
                             Long pageSize,
                             TeacherQuery query){
        Result res = Result.ok().setCode(Status.OK.code).setMessage(Status.OK.msg);
        //判断pageSize参数，如果超出设定直接返回空或者其他设定
        long size;
        if (pageSize==null){
            size = 10L;
        }else if (pageSize>20){
            size = 20L;
        }else if (pageSize<=0){
            return res;
        }else {
            size = pageSize;
        }
        Page<EduTeacher> pages = new Page<>(page,size);

        Map<String,Object> resMap = new HashMap<>();


        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<EduTeacher>();
        //构建条件
        String name = query.getName();
        Integer level = query.getLevel();
        Date start = query.getStart();
        Date end = query.getEnd();
        if (StringUtils.hasText(name)){
            wrapper.like("name",name);
        }
        if (!ObjectUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!ObjectUtils.isEmpty(start)){
            wrapper.ge("gmt_create",start);
        }
        if (!ObjectUtils.isEmpty(end)){
            wrapper.lt("gmt_create",end);
        }
        wrapper.orderByDesc("gmt_create");

        Page<EduTeacher> pageRes = eduTeacherService.page(pages, wrapper);
        if (pageRes.getCurrent()>pageRes.getPages()){
            return res;
        }
        resMap.put("teachers",pageRes.getRecords());
        resMap.put("pages",pageRes.getPages());
        resMap.put("total", pageRes.getTotal());
        resMap.put("current",pageRes.getCurrent());
        return res.addData("teacherInfo",resMap);
    }
    @GetMapping("/teacher")
    public Result getTeacher(Long pageSize,TeacherQuery query){
        return getTeacher(1L,pageSize,query);
    }

    @ApiOperation(value = "新增一个老师", notes = "查询所有老师信息。")
    @PostMapping(value = "/teacher")
    public Result addTeacher(@RequestBody EduTeacher teacher){
        System.out.println(teacher);
        if (teacher.getId()!=null || teacher.getIsDeleted()!=null || teacher.getGmtCreate()!=null ||
        teacher.getGmtModified()!=null){
            //返回一个结果集，参数不正确
            return Result.failed().setCode(Status.PARAMETER_ERROR.code).setMessage(Status.PARAMETER_ERROR.msg);
        }
        boolean save = eduTeacherService.save(teacher);
        if (save){
            return Result.ok().setCode(Status.OK.code).setMessage(Status.OK.msg);
        }else{
            return Result.failed().setCode(Status.ADD_FAILED.code).setMessage(Status.ADD_FAILED.msg);
        }
    }

    @DeleteMapping("/teacher/{id}")
    public Result removeTeacher(@PathVariable String id){
        boolean remove = eduTeacherService.removeById(id);
        if (remove){
            return Result.ok().setCode(Status.OK.code).setMessage(Status.OK.msg);
        }else{
            return Result.failed().setCode(Status.REMOVE_FAILED.code).setMessage(Status.REMOVE_FAILED.msg);
        }
    }

    @PutMapping("/teacher")
    public Result updateTeacher(@RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.updateById(teacher);
        if (flag){
            return Result.ok().setCode(Status.OK.code).setMessage(Status.OK.msg);
        }else{
            return Result.failed().setCode(Status.UPDATE_FAILED.code).setMessage(Status.UPDATE_FAILED.msg);
        }
    }
}

