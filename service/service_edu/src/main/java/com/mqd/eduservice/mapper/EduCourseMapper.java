package com.mqd.eduservice.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.pojo.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mqd.eduservice.pojo.dto.CourseInfoDto;
import com.mqd.eduservice.pojo.vo.CourseQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author mqd
 * @since 2021-10-24
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    // 获取课程发布前的信息
    CourseInfoDto getBeforePublishCourse(String id);

    //根据条件获取课程列表
    IPage<CourseInfoDto> getCourseListByQuery(Page<?> page,@Param(Constants.WRAPPER) QueryWrapper<CourseQuery> query);
}
