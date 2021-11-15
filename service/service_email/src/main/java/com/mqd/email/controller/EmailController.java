package com.mqd.email.controller;

import com.mqd.email.service.EmailService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import com.mqd.utils.ValidateCode;
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

    @GetMapping("/valid/{to}")
    public Result sendValid(@PathVariable String to) throws MessagingException, CustomException {
        String content = "<span style='font-size:24px;font-weight:600'>验证码为:&nbsp;</span><span style='font-size:24px;font-weight:800'>"+ ValidateCode.getValidate()+"</span>";
        boolean flag = emailService.sendValidByMail(to, "在线教育验证码", content);
        if (flag){
            return Result.ok();
        }
        throw new CustomException("发送失败");
    }
}
