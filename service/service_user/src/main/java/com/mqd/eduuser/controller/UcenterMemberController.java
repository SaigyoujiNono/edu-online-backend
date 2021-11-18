package com.mqd.eduuser.controller;


import com.mqd.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/eduuser/member")
public class UcenterMemberController {

    @GetMapping("/test")
    public Result test(){
        return Result.ok().addData("info","test");
    }
}

