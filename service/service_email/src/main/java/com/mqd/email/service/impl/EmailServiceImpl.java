package com.mqd.email.service.impl;

import com.mqd.email.service.EmailService;
import com.mqd.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

@Service
public class EmailServiceImpl implements EmailService {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public MimeMessage sendValidByMail(String to, String subject, String valid) throws MessagingException, CustomException {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        //先获取时间
        Long expire = redisTemplate.getExpire(to);
        if (expire!=null && expire > 840){
            throw new CustomException(to+":当前不能发送新的验证码，请稍后!");
        }
        String content = "<span style='font-size:24px;font-weight:600'>验证码为:&nbsp;</span><span style='font-size:24px;" +
                "font-weight:800'>"+ valid+"</span></br><span style='font-size:24px;font-weight:600'>有效时间为15分钟！</span>";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        ops.set(to,valid,15, TimeUnit.MINUTES);
        return mimeMessage;
    }

    @Async
    public void sendMime(MimeMessage mimeMessage){
        mailSender.send(mimeMessage);
    }
}
