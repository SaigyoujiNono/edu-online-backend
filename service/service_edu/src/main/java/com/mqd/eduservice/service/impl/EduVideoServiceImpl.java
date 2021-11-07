package com.mqd.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqd.eduservice.client.VodClient;
import com.mqd.eduservice.mapper.EduChapterMapper;
import com.mqd.eduservice.mapper.EduCourseMapper;
import com.mqd.eduservice.pojo.EduChapter;
import com.mqd.eduservice.pojo.EduCourse;
import com.mqd.eduservice.pojo.EduVideo;
import com.mqd.eduservice.mapper.EduVideoMapper;
import com.mqd.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author mqd
 * @since 2021-10-30
 */
@Service
@Slf4j
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Resource
    private EduCourseMapper courseMapper;

    @Resource
    private EduChapterMapper chapterMapper;

    @Resource
    private VodClient vodClient;

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

    @Override
    public boolean removeVideoAll(String id) throws CustomException {
        EduVideo eduVideo = baseMapper.selectById(id);
        if (eduVideo == null){
            throw new CustomException("id:"+id+"不存在");
        }
        String videoSourceId = eduVideo.getVideoSourceId();
        if (StringUtils.hasText(videoSourceId)){
            Result result = vodClient.removeVideo(videoSourceId);
            if (result.getCode()!=20000){
                log.warn("视频 "+videoSourceId+" 不存在!");
            }
        }
        int i = baseMapper.deleteById(id);
        return i > 0;
    }

    @Override
    public boolean deleteVideoSingle(String id) throws CustomException {
        //单独删除视频
        //先获取视频信息
        EduVideo eduVideo = baseMapper.selectById(id);
        if (eduVideo == null) {
            throw new CustomException("小节 "+ id + "不存在");
        }
        if (!StringUtils.hasText(eduVideo.getVideoSourceId())){
            throw new CustomException("小节 "+ id + "的视频不存在");
        }
        Result result = vodClient.removeVideo(eduVideo.getVideoSourceId());
        if (result.getCode()!=20000){
            log.warn("小节:"+ id + result.getMessage());
        }
        EduVideo updateVideo = new EduVideo().setId(id).setDuration(0f).
                setVideoOriginalName("").setVideoSourceId("").setSize(0L);
        int i = baseMapper.updateById(updateVideo);
        return i>0;
    }
}
