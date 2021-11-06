package com.mqd.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean {

    @Value("${aliyun.oss.video.address}")
    private String region;
    @Value("${aliyun.oss.video.key-id}")
    private String keyId;
    @Value("${aliyun.oss.video.key-secret}")
    private String keySecret;

    public static String REGION;
    public static String KEY_ID;
    public static String KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        REGION = this.region;
        KEY_ID = this.keyId;
        KEY_SECRET = this.keySecret;
    }
}
