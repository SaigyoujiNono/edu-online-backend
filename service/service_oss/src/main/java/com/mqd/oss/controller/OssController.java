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
    public Result avatarUpload(@RequestPart("file") MultipartFile file){
        //文件尺寸不能超过1M
        if (file.getSize()>1024*1024) {
            return Result.failed().setCode(Status.FILE_MAXSIZE_OVERFLOW.code).setMessage(Status.FILE_MAXSIZE_OVERFLOW.msg);
        }
        String url = ossService.avatarUpload(file);
        if (url == null){
            return Result.failed();
        }
        Result result = Result.ok().setCode(Status.OK.code).setMessage(Status.OK.msg);
        result.addData("url",url);
        return result;
    }
}
