package com.mqd.vod.controller;

import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import com.mqd.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;


    @ApiOperation(value = "上传视频到阿里云存储")
    @PostMapping("/uploadVideo")
    public Result uploadAliyunVideo(@RequestPart("file") MultipartFile file) throws IOException, CustomException {
        String videoId = vodService.uploadVideo(file);
        if (videoId == null){
            throw new CustomException("上传失败");
        }
        return Result.ok().addData("videoId",videoId);
    }

    @ApiOperation(value = "根据视频id删除视频")
    @DeleteMapping("/deleteVideo/{id}")
    public Result deleteAliyunVideo(@PathVariable String id) throws CustomException {
        Boolean flag = vodService.deleteVideo(id);
        if (flag){
            return Result.ok();
        }
        throw new CustomException("视频删除失败");
    }


}
