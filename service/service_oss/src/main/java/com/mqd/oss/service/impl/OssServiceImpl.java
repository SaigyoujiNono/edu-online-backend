package com.mqd.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.mqd.oss.service.OssService;
import com.mqd.oss.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String avatarUpload(MultipartFile file) {
        //获取文件后缀
        int indexOf = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf(".");
        String postfix = file.getOriginalFilename().substring(indexOf);
        String format = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        String filename = format + "/" + uuid + postfix;
        OSS ossClient = null;
        try  {
            InputStream inputStream = file.getInputStream();
            ossClient = new OSSClientBuilder().
                    build(ConstantPropertiesUtils.END_POINT,
                            ConstantPropertiesUtils.KEY_ID,
                            ConstantPropertiesUtils.KEY_SECRET);
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(ConstantPropertiesUtils.BUCKET_NAME, filename, inputStream);
            ossClient.putObject(putObjectRequest);
            return "https://" + ConstantPropertiesUtils.BUCKET_NAME + "." + ConstantPropertiesUtils.END_POINT + "/" +filename;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
