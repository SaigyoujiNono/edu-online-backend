package com.mqd.eduuser.controller;


import com.mqd.eduuser.pojo.UcenterMember;
import com.mqd.eduuser.service.UcenterMemberService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import com.mqd.utils.JWTUtils;
import com.mqd.validate.PhoneNoConstraint;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/api/user/service")
public class UcenterMemberController {

    @Resource
    private UcenterMemberService memberService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @PostMapping("/login")
    public Result loginUser(@RequestBody Map<String,String> user) throws CustomException {
        String username = user.get("username");
        String password = user.get("password");
        String regex = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern compile = Pattern.compile(regex);
        UcenterMember member;
        // 如果是邮箱
        if (compile.matcher(username).matches()){
            member = memberService.loginByEmail(username, password);
        }else{
            //不是邮箱则是手机号
            member = memberService.loginByMobile(username, password);
        }
        if (member != null){
            return createJwtSource(member);
        }
        throw new CustomException("登录失败");
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result registerUser(@Validated @RequestBody UcenterMember member) throws CustomException {
        UcenterMember register = memberService.register(member);
        if (register!=null){
            return createJwtSource(register);
        }
        throw new CustomException("注册失败");
    }

    @ApiOperation(value = "根据jwt获取用户信息")
    @GetMapping("/info")
    public Result getMemberInfo(String token) throws CustomException {
        Claims claims = JWTUtils.parseJWT(token);
        String id = (String)claims.get("id");
        Long expire = redisTemplate.getExpire("user::" + id);
        if (expire!=null && expire!=-2){
            Object o = redisTemplate.opsForValue().get("user::" + id);
            return Result.ok().addData("userInfo",o);
        }
        UcenterMember byId = memberService.getById(id);
        if (byId == null){
            throw new CustomException("该用户不存在");
        }
        byId.setPassword(null);
        redisTemplate.opsForValue().set("user::" + id,byId,1, TimeUnit.DAYS);
        return Result.ok().addData("userInfo",byId);
    }

    /**
     * 将member包装返回
     * @param member    会员信息
     * @return  返回一个包装好的result
     */
    public Result createJwtSource(UcenterMember member){
        //生成jwt
        Map<String,Object> map = new HashMap<>();
        map.put("id",member.getId());
        String jwt = JWTUtils.createJWT(map);
        return Result.ok().addData("token",jwt).addData("userInfo",member);
    }
}

