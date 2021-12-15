package com.mqd.eduservice.controller.front;

import com.mqd.eduservice.service.EduCourseDescriptionService;
import com.mqd.eduservice.service.EduCourseService;
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

    @Resource
    private EduCourseDescriptionService descService;
}
