package com.mqd.eduservice.mapper;

import com.mqd.eduservice.pojo.EduChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mqd.eduservice.pojo.dto.ChapterAndVideo;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author mqd
 * @since 2021-10-24
 */
public interface EduChapterMapper extends BaseMapper<EduChapter> {
    List<ChapterAndVideo> getChapterAllByCourseId(String id);
}
