package com.mqd.eduservice.client;

import com.mqd.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-vod")
@Component
public interface VodClient {

    @DeleteMapping(value = "/eduvod/video/deleteVideo/{videoId}")
    Result removeVideo(@PathVariable String videoId);

}
