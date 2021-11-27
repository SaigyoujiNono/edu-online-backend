package com.mqd.eduservice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.pojo.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mqd.eduservice.pojo.dto.CourseInfoDto;
import com.mqd.eduservice.pojo.vo.CourseInfoVo;
import com.mqd.eduservice.pojo.vo.CourseQuery;
import com.mqd.exception.CustomException;

import java.util.List;

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

    CourseInfoDto getBeforePublishCourse(String id);

    IPage<CourseInfoDto> getCourseListByQuery(CourseQuery query, Page<CourseInfoDto> page);

    /**
     * 获取热门课程，根据播放量降序排序
     * @return  返回9条数据
     */
    List<EduCourse> getHotCourse();
}
