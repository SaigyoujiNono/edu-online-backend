package com.mqd.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqd.eduservice.pojo.EduChapter;
import com.mqd.eduservice.pojo.EduVideo;
import com.mqd.eduservice.pojo.vo.ChapterInfoVo;
import com.mqd.eduservice.service.EduChapterService;
import com.mqd.eduservice.service.EduVideoService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-10-24
 */
@RestController
@RequestMapping("/api/edu/admin")
public class EduChapterController {

    @Resource
    private EduChapterService chapterService;

    @Resource
    private EduVideoService videoService;

    @ApiOperation(value = "添加一个章节", notes = "添加一个章节，并且返回该章节的所有信息。")
    @PostMapping("/chapter")
    public Result addChapter(@RequestBody EduChapter eduChapter) throws CustomException {
        boolean save = chapterService.save(eduChapter);
        if (save){
            return Result.ok().addData("chapter",eduChapter);
        }
        throw new CustomException("添加章节失败");
    }

    @ApiOperation(value = "删除一个章节", notes = "删除一个章节")
    @DeleteMapping("/chapter/{id}")
    public Result deleteChapter(@PathVariable String id) throws CustomException {
        // 先查询是否包含小节 如果包含小节需要删除所有小节之后才能删除章节
        QueryWrapper<EduVideo> videoQuery = new QueryWrapper<>();
        videoQuery.eq("chapter_id",id);
        List<EduVideo> list = videoService.list(videoQuery);
        if (list.isEmpty()){
            boolean b = chapterService.removeById(id);
            if (b) {
                return Result.ok();
            }
            throw new CustomException("删除失败");
        }
        throw new CustomException("存在小节未删除，无法删除该章节");
    }

    @ApiOperation(value = "更新一个章节信息", notes = "根据id更新一个章节信息")
    @PutMapping("/chapter")
    public Result updateChapter(@RequestBody EduChapter chapter) throws CustomException {
        boolean b = chapterService.updateById(chapter);
        if (b){
            return Result.ok();
        }
        throw new CustomException("更新失败");
    }

    @ApiOperation(value = "获取所有章节和小节", notes = "获取所有章节和小节,id为课程的id")
    @GetMapping("/chapter/{id}")
    public Result getChapterAllByCourseId(@PathVariable String id) {
        List<ChapterInfoVo> chapterInfo = chapterService.getChapterAllByCourseId(id);
        return Result.ok().addData("chapterInfo", chapterInfo);
    }
}

