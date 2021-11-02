package com.mqd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqd.eduservice.mapper.EduChapterMapper;
import com.mqd.eduservice.mapper.EduCourseMapper;
import com.mqd.eduservice.pojo.EduChapter;
import com.mqd.eduservice.pojo.EduCourse;
import com.mqd.eduservice.pojo.EduVideo;
import com.mqd.eduservice.mapper.EduVideoMapper;
import com.mqd.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mqd.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author mqd
 * @since 2021-10-30
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private EduCourseMapper courseMapper;

    @Autowired
    private EduChapterMapper chapterMapper;

    @Override
    public boolean saveVideo(EduVideo video) throws CustomException {
        EduCourse eduCourse = courseMapper.selectById(video.getCourseId());
        if (eduCourse == null) {
            throw new CustomException("课程不存在");
        }
        QueryWrapper<EduChapter> chapterQuery = new QueryWrapper<>();
        chapterQuery.eq("id",video.getChapterId())
                .eq("course_id",video.getCourseId());
        Integer integer = chapterMapper.selectCount(chapterQuery);
        if (integer == 0) {
            throw new CustomException("章节不存在");
        }
        int insert = baseMapper.insert(video);
        return insert > 0;
    }
}
