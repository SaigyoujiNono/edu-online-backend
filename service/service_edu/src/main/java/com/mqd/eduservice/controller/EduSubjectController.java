package com.mqd.eduservice.controller;


import com.mqd.eduservice.pojo.EduSubject;
import com.mqd.eduservice.service.EduSubjectService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


}

