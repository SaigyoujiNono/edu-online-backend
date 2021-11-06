package com.mqd.vod.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VodService {
    String uploadVideo(MultipartFile file) throws IOException;

    //删除视频
    Boolean deleteVideo(String id);
}
