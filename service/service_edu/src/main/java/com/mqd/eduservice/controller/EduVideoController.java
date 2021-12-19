package com.mqd.eduservice.controller;


import com.mqd.eduservice.pojo.EduVideo;
import com.mqd.eduservice.service.EduVideoService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-10-30
 */
@Api(tags = "视频管理")
@RestController
@RequestMapping("/api/edu/admin")
public class EduVideoController {
    @Resource
    private EduVideoService videoService;


    @ApiOperation(value = "添加一个小节")
    @PostMapping("/video")
    public Result addVideo(@RequestBody EduVideo video) throws CustomException {
        boolean save = videoService.saveVideo(video);
        if (save) {
            return Result.ok().addData("video",video);
        }
        throw new CustomException("添加失败");
    }

    @ApiOperation(value = "删除一个小节")
    @DeleteMapping("/video/{id}")
    public Result deleteVideo(@PathVariable String id) throws CustomException {
        //删除一个小节需要把视频也删除
        boolean b = videoService.removeVideoAll(id);
        if (b) {
            return Result.ok();
        }
        throw new CustomException("删除失败");
    }

    @ApiOperation(value = "单独删除视频")
    @DeleteMapping("/videoSingle/{id}")
    public Result deleteVideoSingle(@PathVariable String id) throws CustomException {
        boolean flag = videoService.deleteVideoSingle(id);
        if (flag){
            return Result.ok();
        }
        throw new CustomException("删除失败");
    }

    @ApiOperation(value = "更新一个小节")
    @PutMapping("/video")
    public Result updateVideo(@RequestBody EduVideo video) throws CustomException {
        boolean b = videoService.updateById(video);
        if (b) {
            return Result.ok();
        }
        throw new CustomException("更新失败");
    }
}

