package com.mqd.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqd.eduservice.pojo.EduSubject;
import com.mqd.eduservice.pojo.vo.SubjectQuery;
import com.mqd.eduservice.service.EduSubjectService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-10-23
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;
    @GetMapping("/tree")
    public Result getTree() {
        return Result.ok().addData("tree",subjectService.getSubjectTree());
    }

    @PostMapping("/subject")
    public Result addSubject(@RequestBody EduSubject sub) throws CustomException {
        boolean save = subjectService.save(sub);
        if (save){
            return Result.ok();
        }
        throw new CustomException(sub.getTitle()+"添加失败!");
    }

    @GetMapping("/subject")
    public Result getSubject(SubjectQuery sub){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<EduSubject>();
        String parentId = sub.getParentId();
        if (StringUtils.hasText(parentId)){
            wrapper.eq("parent_id",parentId);
        }
        List<EduSubject> list = subjectService.list(wrapper);
        return Result.ok().addData("primarySubList",list);
    }

    @DeleteMapping("/subject/{id}")
    public Result delSubject(@PathVariable String id) throws CustomException {
        if (subjectService.delSubject(id)){
            return Result.ok();
        }
        throw  new CustomException("删除失败!");
    }

}

