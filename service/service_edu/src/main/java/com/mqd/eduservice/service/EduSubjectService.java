package com.mqd.eduservice.service;

import com.mqd.eduservice.pojo.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mqd.eduservice.pojo.dto.SubjectDto;
import com.mqd.exception.CustomException;

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
    List<SubjectDto> getSubjectTree();

    /**
     * 删除一个课程
     * 当前id是次级目录的时候，直接删除
     * 当前id是父目录时，需要检查是否存在子目录，存在则删除失败，不存在则直接删除
     * @param id 需要删除的课程的id
     * @return  删除成功返回true
     */
    boolean delSubject(String id) throws CustomException;
}
