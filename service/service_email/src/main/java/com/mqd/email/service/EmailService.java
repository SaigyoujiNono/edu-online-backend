package com.mqd.email.service;

import com.mqd.exception.CustomException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface EmailService {

    /**
     * 发送验证码到指定邮箱
     * 先到redis库检查验证码是否存在
     * 如果存在则判断时间，默认验证码有效时间为15分钟
     *      当验证码剩余时间大于840时则抛出异常提示当前不能发送验证码
     *      当验证码时间小于840时则重新向redis插入
     * @param to    要发送的目标地址，也是key
     * @param subject   邮件标题
     * @param valid 验证码
     * @return  返回需要发送的内容
     */
    MimeMessage sendValidByMail(String to, String subject, String valid) throws MessagingException, CustomException;

    /**
     * 异步发送任务
     * @param mimeMessage   需要发送的内容
     */
    void sendMime(MimeMessage mimeMessage);
}
