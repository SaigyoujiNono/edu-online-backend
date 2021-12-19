package com.mqd.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.pojo.EduTeacher;
import com.mqd.eduservice.pojo.vo.TeacherQuery;
import com.mqd.eduservice.service.EduTeacherService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-10-12
 */
@Api(tags = "教师管理")
@RestController
@RequestMapping("/api/edu/admin")
public class EduTeacherController {

    @Resource
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有的老师信息
     * @return  返回所有老师的信息
     */
    @ApiOperation(value = "查询老师", notes = "查询所有老师信息。")
    @GetMapping("/teacher/{page}")
    public Result getTeacher(@PathVariable Long page,
                             Long pageSize,
                             TeacherQuery query) throws CustomException {
        //判断pageSize参数，如果超出设定直接返回空或者其他设定
        long size;
        if (pageSize==null){
            size = 10L;
        }else if (pageSize>20){
            size = 20L;
        }else if (pageSize<=0){
            throw new CustomException("pageSize不能小于或等于0");
        }else {
            size = pageSize;
        }
        Page<EduTeacher> pages = new Page<>(page,size);

        Map<String,Object> resMap = new HashMap<>();


        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
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
//        if (pageRes.getCurrent()>pageRes.getPages()){
//            throw new CustomException("当前页数大于总页数!");
//        }
        resMap.put("teachers",pageRes.getRecords());
        resMap.put("pages",pageRes.getPages());
        resMap.put("total", pageRes.getTotal());
        resMap.put("current",pageRes.getCurrent());
        return Result.ok().addData("teacherInfo",resMap);
    }

    @ApiOperation(value = "根据条件获取第一页老师的信息")
    @GetMapping("/teacher")
    public Result getTeacher(Long pageSize,TeacherQuery query) throws CustomException {
        return getTeacher(1L,pageSize,query);
    }

    @ApiOperation(value = "获取所有的老师信息")
    @GetMapping("/teacherAll")
    public Result getTeacherAll() {

        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.ok().addData("teacherList",list);
    }

    @ApiOperation(value = "新增一个老师", notes = "新增教师信息。")
    @PostMapping(value = "/teacher")
    public Result addTeacher(@RequestBody @Validated EduTeacher teacher) throws CustomException {
        System.out.println(teacher);
        if (StringUtils.hasText(teacher.getId()) || teacher.getIsDeleted()!=null || teacher.getGmtCreate()!=null ||
        teacher.getGmtModified()!=null || !StringUtils.hasText(teacher.getName())){
            throw new CustomException("参数不正确!");
        }
        boolean save = eduTeacherService.save(teacher);
        if (save){
            return Result.ok();
        }else{
            throw new CustomException("未知原因添加失败!");
        }
    }

    @ApiOperation(value = "根据id删除老师")
    @DeleteMapping("/teacher/{id}")
    public Result removeTeacher(@PathVariable String id) throws CustomException {
        if (!StringUtils.hasText(id)) {
            throw new CustomException("必须输入id");
        }
        boolean remove = eduTeacherService.removeById(id);
        if (remove){
            return Result.ok();
        }else{
            throw new CustomException("id:"+id+"---> 删除失败!");
        }
    }

    @ApiOperation(value = "根据id更新老师信息")
    @PutMapping("/teacher")
    public Result updateTeacher(@RequestBody @Validated EduTeacher teacher) throws CustomException {
        if (StringUtils.hasText(teacher.getId()) || teacher.getIsDeleted()!=null || teacher.getGmtCreate()!=null ||
                teacher.getGmtModified()!=null || !StringUtils.hasText(teacher.getName())){
            throw new CustomException("参数不正确!");
        }
        boolean flag = eduTeacherService.updateById(teacher);
        if (flag){
            return Result.ok();
        }else{
            throw new CustomException("id:"+teacher.getId()+"---> 更新失败!");
        }
    }
}

