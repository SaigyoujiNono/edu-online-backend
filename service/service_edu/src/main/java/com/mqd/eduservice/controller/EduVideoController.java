package com.mqd.eduservice.controller;


import com.mqd.eduservice.pojo.EduVideo;
import com.mqd.eduservice.service.EduVideoService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-10-30
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;


    @ApiOperation(value = "添加一个小节")
    @PostMapping("/video")
    public Result addVideo(EduVideo video) throws CustomException {
        boolean save = videoService.saveVideo(video);
        if (save) {
            return Result.ok();
        }
        throw new CustomException("添加失败");
    }

    @ApiOperation(value = "删除一个小节")
    @DeleteMapping("/video/{id}")
    public Result deleteVideo(@PathVariable String id) throws CustomException {
        boolean b = videoService.removeById(id);
        if (b) {
            return Result.ok();
        }
        throw new CustomException("删除失败");
    }

    @ApiOperation(value = "更新一个小节")
    @PutMapping("/video")
    public Result deleteVideo(EduVideo video) throws CustomException {
        boolean b = videoService.updateById(video);
        if (b) {
            return Result.ok();
        }
        throw new CustomException("更新失败");
    }
}

