package com.mqd.eduservice.client;

import com.mqd.result.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class VodHystrix implements VodClient{
    @Override
    public Result removeVideo(@PathVariable String videoId) {
        return Result.failed().setMessage("删除视频出错了，请稍后再试");
    }
}
