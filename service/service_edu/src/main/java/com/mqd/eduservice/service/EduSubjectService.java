package com.mqd.eduservice.service;

import com.mqd.eduservice.pojo.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mqd.eduservice.pojo.dto.SubjectDto;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author mqd
 * @since 2021-10-23
 */
public interface EduSubjectService extends IService<EduSubject> {
    //获取所有课程的分类将其打包成前端所需要的数据格式
    public List<SubjectDto> getSubjectTree();
}
