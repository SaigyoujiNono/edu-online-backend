package com.mqd.eduservice.service;

import com.mqd.eduservice.pojo.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author mqd
 * @since 2021-10-12
 */
public interface EduTeacherService extends IService<EduTeacher> {

    /**
     * 获取热门讲师，以sort排序
     * @return  返回6条热门讲师数据
     */
    List<EduTeacher> getHotTeacher();
}
