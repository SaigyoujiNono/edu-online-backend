package com.mqd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.eduservice.pojo.EduTeacher;
import com.mqd.eduservice.mapper.EduTeacherMapper;
import com.mqd.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author mqd
 * @since 2021-10-12
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {


    @Cacheable(key = "'teacher'", value = "home")
    @Override
    public List<EduTeacher> getHotTeacher() {
        QueryWrapper<EduTeacher> teacherQuery = new QueryWrapper<>();
        IPage<EduTeacher> teacherPage = new Page<>(1,6);
        teacherQuery.orderByDesc("sort");
        baseMapper.selectPage(teacherPage, teacherQuery);
        return teacherPage.getRecords();
    }
}
