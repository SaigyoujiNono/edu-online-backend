package com.mqd.eduuser.controller;


import com.mqd.eduuser.pojo.UcenterMember;
import com.mqd.result.Result;
import com.mqd.utils.JWTUtils;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/login")
    public Result loginUser(@RequestBody UcenterMember member){
        Map<String,Object> map = new HashMap<>();
        map.put("id",member.getId());
        map.put("nickname",member.getNickname());
        String jwt = JWTUtils.createJWT(map);
        return Result.ok().addData("token",jwt);
    }

//    @GetMapping("/info")
//    public Result infoUser(@RequestAttribute String token){
//
//    }
}

