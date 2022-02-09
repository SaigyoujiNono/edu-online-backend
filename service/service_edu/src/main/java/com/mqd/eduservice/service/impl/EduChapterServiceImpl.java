package com.mqd.eduservice.service.impl;

import com.mqd.eduservice.pojo.EduChapter;
import com.mqd.eduservice.mapper.EduChapterMapper;
import com.mqd.eduservice.pojo.EduVideo;
import com.mqd.eduservice.pojo.dto.ChapterAndVideo;
import com.mqd.eduservice.pojo.vo.ChapterInfoVo;
import com.mqd.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author mqd
 * @since 2021-10-24
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Override
    public List<ChapterInfoVo> getChapterAllByCourseId(String id){
        List<ChapterInfoVo> res = new ArrayList<>();
        List<ChapterAndVideo> list = baseMapper.getChapterAllByCourseId(id);
        String chapterId = "";
        ChapterInfoVo cur = null;
        for (ChapterAndVideo chapterAndVideo : list) {
            if (!chapterAndVideo.getId().equals(chapterId)) {
                chapterId = chapterAndVideo.getId();
                cur = new ChapterInfoVo();
                res.add(cur);
                cur.setId(chapterId)
                        .setSort(chapterAndVideo.getSort())
                        .setTitle(chapterAndVideo.getTitle());
                if (StringUtils.hasText(chapterAndVideo.getVideoId())) {
                    EduVideo eduVideo = convertChapterToVideo(chapterAndVideo);
                    cur.add(eduVideo);
                }
            } else {
                EduVideo eduVideo = convertChapterToVideo(chapterAndVideo);
                if (cur != null) {
                    cur.add(eduVideo);
                }
            }
        }
        return res;
    }

    public EduVideo convertChapterToVideo(ChapterAndVideo c){
        return new EduVideo().setId(c.getVideoId())
                .setTitle(c.getVideoTitle())
                .setIsFree(c.getVideoIsFree())
                .setSort(c.getVideoSort())
                .setVideoOriginalName(c.getVideoOriginalName())
                .setVideoSourceId(c.getVideoSourceId())
                .setDuration(c.getDuration())
                .setPlayCount(c.getPlayCount())
                .setSize(c.getSize())
                .setStatus(c.getStatus())
                .setCourseId(c.getCourseId());
    }
}
