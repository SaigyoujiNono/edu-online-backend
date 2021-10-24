package com.mqd.oss.controller;

import com.mqd.oss.service.OssService;
import com.mqd.result.Result;
import com.mqd.result.Status;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传")
@RestController
@RequestMapping(("/oss/file"))
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

//    @ApiParam(type = "file")
    @PostMapping("/avatar")
    public Result avatarUpload(@RequestPart("file") MultipartFile file) throws AddException {
        String url = ossService.avatarUpload(file);
        if (url == null){
            throw new AddException("图片上传失败");
        }
        return Result.ok().setCode(Status.OK).addData("url",url);
    }
}
