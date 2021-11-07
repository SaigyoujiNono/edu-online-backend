package com.mqd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.mapper.EduCourseDescriptionMapper;
import com.mqd.eduservice.mapper.EduSubjectMapper;
import com.mqd.eduservice.mapper.EduTeacherMapper;
import com.mqd.eduservice.pojo.EduCourse;
import com.mqd.eduservice.mapper.EduCourseMapper;
import com.mqd.eduservice.pojo.EduCourseDescription;
import com.mqd.eduservice.pojo.EduSubject;
import com.mqd.eduservice.pojo.EduTeacher;
import com.mqd.eduservice.pojo.dto.CourseInfoDto;
import com.mqd.eduservice.pojo.vo.CourseInfoVo;
import com.mqd.eduservice.pojo.vo.CourseQuery;
import com.mqd.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mqd.exception.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author mqd
 * @since 2021-10-24
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    private EduCourseDescriptionMapper desMapper;

    @Resource
    private EduTeacherMapper teacherMapper;

    @Resource
    private EduSubjectMapper subjectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)//开启事务
    public CourseInfoVo saveCourseInfo(CourseInfoVo courseInfo) throws CustomException {
        //先判断老师是否存在
        String teacherId = courseInfo.getTeacherId();
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.eq("id",teacherId);
        Integer teacherCount = teacherMapper.selectCount(wrapper);
        if (teacherCount != 1){
            throw new CustomException("老师不存在");
        }

        //判断课程分类id是否正确
        QueryWrapper<EduSubject> wrapperSub = new QueryWrapper<>();
        wrapperSub.eq("id",courseInfo.getSubjectId())
                .eq("parent_id",courseInfo.getSubjectParentId());
        Integer subCount = subjectMapper.selectCount(wrapperSub);
        if (subCount != 1){
            throw new CustomException("课程分类不存在");
        }

        //新建一个课程Bean
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0){
            throw new CustomException("失败了");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfo,eduCourseDescription);
        //设置课程id关联课程描述id
        eduCourseDescription.setId(eduCourse.getId());
        insert = desMapper.insert(eduCourseDescription);
        if (insert <= 0){
            throw new CustomException("失败了");
        }
        courseInfo.setId(eduCourse.getId());
        return courseInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)//开启事务
    public CourseInfoVo updateCourseInfo(CourseInfoVo courseInfo) throws CustomException {
        //先判断老师是否存在
        EduTeacher eduTeacher = teacherMapper.selectById(courseInfo.getTeacherId());
        if (eduTeacher == null){
            throw new CustomException("老师不存在");
        }

        //判断课程分类id是否正确
        EduSubject eduSubject = subjectMapper.selectById(courseInfo.getSubjectId());
        if (eduSubject == null){
            throw new CustomException("课程分类不存在");
        }

        //新建一个课程实例
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update <= 0){
            throw new CustomException("失败了");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfo,eduCourseDescription);
        //设置课程id关联课程描述id
        update = desMapper.updateById(eduCourseDescription);
        if (update <= 0){
            throw new CustomException("失败了");
        }
        return courseInfo;
    }

    @Override
    public CourseInfoDto getBeforePublishCourse(String id) {
        return baseMapper.getBeforePublishCourse(id);
    }

    @Override
    public IPage<CourseInfoDto> getCourseListByQuery(CourseQuery query, Page<CourseInfoDto> page) {
        QueryWrapper<CourseQuery> queryWrapper = new QueryWrapper<>();
        String title = query.getTitle();
        String subjectId = query.getSubjectId();
        String subjectParentId = query.getSubjectParentId();
        String teacherId = query.getTeacherId();
        BigDecimal priceMin = query.getPriceMin();
        BigDecimal priceMax = query.getPriceMax();
        Long minBuyCount = query.getMinBuyCount();
        Long maxBuyCount = query.getMaxBuyCount();
        Long minViewCount = query.getMinViewCount();
        Long maxViewCount = query.getMaxViewCount();
        String status = query.getStatus();
        queryWrapper.eq("is_deleted","0");
        //构建条件查询
        if (StringUtils.hasText(title)){
            queryWrapper.like("title",title);
        }
        if (StringUtils.hasText(subjectId)){
            queryWrapper.eq("subject_id",subjectId);
        }
        if (StringUtils.hasText(subjectParentId)){
            queryWrapper.eq("subject_parent_id",subjectParentId);
        }
        if (StringUtils.hasText(teacherId)){
            queryWrapper.eq("teacher_id",teacherId);
        }
        if (!ObjectUtils.isEmpty(priceMin)){
            queryWrapper.ge("price",priceMin);
        }
        if (!ObjectUtils.isEmpty(priceMax)){
            queryWrapper.le("price",priceMax);
        }
        if (!ObjectUtils.isEmpty(minBuyCount)){
            queryWrapper.ge("buy_count",minBuyCount);
        }
        if (!ObjectUtils.isEmpty(maxBuyCount)){
            queryWrapper.le("buy_count",maxBuyCount);
        }
        if (!ObjectUtils.isEmpty(minViewCount)){
            queryWrapper.ge("view_count",minViewCount);
        }
        if (!ObjectUtils.isEmpty(maxViewCount)){
            queryWrapper.le("view_count",maxViewCount);
        }
        if (StringUtils.hasText(status)){
            queryWrapper.eq("status",status);
        }
        return baseMapper.getCourseListByQuery(page, queryWrapper);
    }

}
