package com.mqd.email.controller;

import com.mqd.email.service.EmailService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import com.mqd.utils.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Null;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/emailservice")
@Slf4j
public class EmailController {
    @Resource
    private EmailService emailService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/valid/{to}")
    public Result sendValid(@PathVariable String to) throws MessagingException, CustomException {
        log.info(to+"发送验证码");
        String regex = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern compile = Pattern.compile(regex);
        if (!compile.matcher(to).matches()) {
            throw new CustomException("邮箱格式不正确");
        }
        String code = ValidateCode.getValidate();
        MimeMessage mime = emailService.sendValidByMail(to, "在线教育验证码", code);
        emailService.sendMime(mime,to,code);
        return Result.ok();
    }

    @GetMapping("/getValid/{email}")
    public Result getValid(@PathVariable String email){
        log.info(email+"获取验证码");
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String s = ops.get("code::"+email);
        return Result.ok().addData("valid",s);
    }
}
