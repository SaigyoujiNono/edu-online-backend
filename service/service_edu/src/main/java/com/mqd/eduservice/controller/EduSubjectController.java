package com.mqd.eduservice.controller;


import com.mqd.eduservice.pojo.EduSubject;
import com.mqd.eduservice.pojo.dto.SubjectDto;
import com.mqd.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/eduservice")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;
    @GetMapping("/tree")
    public List<SubjectDto> getTree() {
        return subjectService.getSubjectTree();
    }


}

