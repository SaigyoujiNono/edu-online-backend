package com.mqd.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String avatarUpload(MultipartFile file);
}
