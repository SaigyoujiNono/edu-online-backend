package com.mqd.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class MD5Utils {

    /**
     * md5盐值加密
     * @param pwd   密码
     * @param salt  盐
     * @return  md5
     */
    public static String toMD5(String pwd, String salt){
        return DigestUtils.md5DigestAsHex((pwd+salt).getBytes(StandardCharsets.UTF_8));
    }
}
