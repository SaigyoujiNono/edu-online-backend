package com.mqd.eduservice.controller;

import com.mqd.result.Result;
import com.mqd.result.Status;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class LoginController {

    @PostMapping("/login")
    public Result login(@RequestBody String username){
        return Result.ok().setCode(Status.OK.code).setMessage(Status.OK.msg)
                .addData("token","admin-token");
    }

    @GetMapping("/info")
    public Result getInfo(String token){
        List<String> roles = new ArrayList<>();
        roles.add("admin");
        return Result.ok().setCode(Status.OK.code).setMessage(Status.OK.msg)
                .addData("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif")
                .addData("introduction","超级管理员")
                .addData("name","超级管理员-张三")
                .addData("roles",roles);
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.ok().setCode(Status.OK.code).setMessage(Status.OK.msg);
    }
}
