package com.mqd.eduservice.client;

import com.mqd.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-vod",fallback = VodHystrix.class)
@Component
public interface VodClient {

    @DeleteMapping(value = "/api/vod/video/{videoId}")
    Result removeVideo(@PathVariable String videoId);

}
