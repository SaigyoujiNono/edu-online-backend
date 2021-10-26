package com.mqd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqd.eduservice.mapper.EduCourseDescriptionMapper;
import com.mqd.eduservice.mapper.EduSubjectMapper;
import com.mqd.eduservice.mapper.EduTeacherMapper;
import com.mqd.eduservice.pojo.EduCourse;
import com.mqd.eduservice.mapper.EduCourseMapper;
import com.mqd.eduservice.pojo.EduCourseDescription;
import com.mqd.eduservice.pojo.EduSubject;
import com.mqd.eduservice.pojo.EduTeacher;
import com.mqd.eduservice.pojo.vo.CourseInfoVo;
import com.mqd.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mqd.exception.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private EduCourseDescriptionMapper desMapper;

    @Autowired
    private EduTeacherMapper teacherMapper;

    @Autowired
    private EduSubjectMapper subjectMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)//开启事务
    public CourseInfoVo saveCourseInfo(CourseInfoVo courseInfo) throws CustomException {
        //先判断老师是否存在
        String teacherId = courseInfo.getTeacherId();
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<EduTeacher>();
        wrapper.eq("id",teacherId);
        Integer teacherCount = teacherMapper.selectCount(wrapper);
        if (teacherCount != 1){
            throw new CustomException("老师不存在");
        }

        //判断课程分类id是否正确
        QueryWrapper<EduSubject> wrapperSub = new QueryWrapper<EduSubject>();
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
}
