package com.mqd.eduservice.service;

import com.mqd.eduservice.pojo.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mqd.exception.CustomException;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author mqd
 * @since 2021-10-30
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean saveVideo(EduVideo video) throws CustomException;

    //删除一个小节的同时也删除视频
    boolean removeVideoAll(String id) throws CustomException;

    boolean deleteVideoSingle(String id) throws CustomException;
}
