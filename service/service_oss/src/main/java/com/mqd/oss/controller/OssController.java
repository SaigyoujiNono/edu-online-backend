package com.mqd.oss.controller;

import com.mqd.exception.CustomException;
import com.mqd.oss.service.OssService;
import com.mqd.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Api(tags = "文件上传")
@RestController
@RequestMapping(("/oss/file"))
@CrossOrigin
public class OssController {

    @Resource
    private OssService ossService;

//    @ApiParam(type = "file")
    @PostMapping("/imgUpload")
    public Result imgUpload(@RequestPart("file") MultipartFile file) throws CustomException {
        String url = ossService.avatarUpload(file);
        if (url == null){
            throw new CustomException("图片上传失败");
        }
        return Result.ok().addData("url",url);
    }
}
