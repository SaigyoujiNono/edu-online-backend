package com.mqd.eduservice.service.impl;

import com.mqd.eduservice.pojo.EduChapter;
import com.mqd.eduservice.mapper.EduChapterMapper;
import com.mqd.eduservice.pojo.EduVideo;
import com.mqd.eduservice.pojo.dto.ChapterAndVideo;
import com.mqd.eduservice.pojo.vo.ChapterInfoVo;
import com.mqd.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                    EduVideo eduVideo = new EduVideo().setId(chapterAndVideo.getVideoId())
                            .setTitle(chapterAndVideo.getVideoTitle())
                            .setSort(chapterAndVideo.getVideoSort());
                    cur.add(eduVideo);
                }
            } else {
                EduVideo eduVideo = new EduVideo().setId(chapterAndVideo.getVideoId())
                        .setTitle(chapterAndVideo.getVideoTitle())
                        .setSort(chapterAndVideo.getVideoSort());
                if (cur != null) {
                    cur.add(eduVideo);
                }
            }
        }

        return res;
    }
}
