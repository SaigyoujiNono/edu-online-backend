package com.mqd.eduuser.controller;


import com.mqd.eduuser.pojo.UcenterMember;
import com.mqd.eduuser.service.UcenterMemberService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import com.mqd.utils.JWTUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private UcenterMemberService memberService;

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

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result registerUser(@Validated @RequestBody UcenterMember member) throws CustomException {
        boolean save = memberService.save(member);
        if (save){
            return Result.ok();
        }
        throw new CustomException("注册失败");
    }
}

