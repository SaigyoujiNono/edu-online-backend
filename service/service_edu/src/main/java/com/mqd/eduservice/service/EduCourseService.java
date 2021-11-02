package com.mqd.eduservice.service;

import com.mqd.eduservice.pojo.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mqd.eduservice.pojo.vo.CourseInfoVo;
import com.mqd.exception.CustomException;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author mqd
 * @since 2021-10-24
 */
public interface EduCourseService extends IService<EduCourse> {

    CourseInfoVo saveCourseInfo(CourseInfoVo courseInfo) throws CustomException;

    CourseInfoVo updateCourseInfo(CourseInfoVo courseInfo) throws CustomException;
}
