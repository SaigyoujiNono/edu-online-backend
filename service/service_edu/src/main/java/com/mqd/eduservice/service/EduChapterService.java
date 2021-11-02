package com.mqd.eduservice.service;

import com.mqd.eduservice.pojo.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mqd.eduservice.pojo.dto.ChapterAndVideo;
import com.mqd.eduservice.pojo.vo.ChapterInfoVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author mqd
 * @since 2021-10-24
 */
public interface EduChapterService extends IService<EduChapter> {
    List<ChapterInfoVo> getChapterAllByCourseId(String id);
}
