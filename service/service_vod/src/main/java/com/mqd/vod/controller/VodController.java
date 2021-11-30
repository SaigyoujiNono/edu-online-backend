package com.mqd.vod.controller;

import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import com.mqd.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@RestController
@RequestMapping("/api/vod")
@Slf4j
public class VodController {

    @Resource
    private VodService vodService;


    @ApiOperation(value = "上传视频到阿里云存储")
    @PostMapping("/video")
    public Result uploadAliyunVideo(@RequestPart("file") MultipartFile file) throws IOException, CustomException {
        String videoId = vodService.uploadVideo(file);
        log.info(videoId+":上传成功");
        if (videoId == null){
            throw new CustomException("上传失败");
        }
        return Result.ok().addData("videoId",videoId);
    }

    @ApiOperation(value = "根据视频id删除视频")
    @DeleteMapping("/video/{id}")
    public Result deleteAliyunVideo(@PathVariable String id) throws CustomException {
        Boolean flag = vodService.deleteVideo(id);
        log.info(id+":删除成功");
        if (flag){
            return Result.ok();
        }
        throw new CustomException("视频删除失败");
    }


}
