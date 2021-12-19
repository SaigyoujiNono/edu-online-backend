package com.mqd.eduservice.controller;

import com.mqd.result.Result;
import com.mqd.result.Status;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "管理登录接口")
@RestController
@RequestMapping("/api/edu/admin")
@Slf4j
public class LoginController {

    @ApiOperation(value = "登录接口")
    @PostMapping("/user/login")
    public Result login(@RequestBody String username){
        log.info(username + ":登录");
        return Result.ok().setCode(Status.OK)
                .addData("token","admin-token");
    }

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/user/info")
    public Result getInfo(String token){
        log.info(token + ":获取信息");
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        return Result.ok().setCode(Status.OK)
                .addData("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .addData("introduction","超级管理员")
                .addData("name","超级管理员-张三")
                .addData("roles",roles);
    }

    @ApiOperation(value = "注销登录")
    @PostMapping("/user/logout")
    public Result logout(){
        return Result.ok().setCode(Status.OK);
    }
}
