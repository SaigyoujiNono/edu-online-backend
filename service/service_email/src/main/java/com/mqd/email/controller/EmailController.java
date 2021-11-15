package com.mqd.email.controller;

import com.mqd.email.service.EmailService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import com.mqd.utils.ValidateCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RestController
@RequestMapping("/emailservice")
public class EmailController {
    @Resource
    private EmailService emailService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/valid/{to}")
    public Result sendValid(@PathVariable String to) throws MessagingException, CustomException {
        boolean flag = emailService.sendValidByMail(to, "在线教育验证码" , ValidateCode.getValidate());
        if (flag){
            return Result.ok();
        }
        throw new CustomException("发送失败");
    }

    @GetMapping("/getValid/{email}")
    public Result getValid(@PathVariable String email){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String s = ops.get(email);
        return Result.ok().addData("valid",s);
    }
}
