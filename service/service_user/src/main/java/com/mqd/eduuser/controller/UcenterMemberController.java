package com.mqd.eduuser.controller;


import com.mqd.eduuser.pojo.UcenterMember;
import com.mqd.eduuser.pojo.vo.MemberVo;
import com.mqd.eduuser.service.UcenterMemberService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import com.mqd.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @ApiOperation(value = "用户登录",notes = "可以直接输入邮箱或者手机号码")
    @PostMapping("/login")
    public Result loginUser(@RequestBody Map<String,String> user) throws CustomException {
        String username = user.get("username");
        String password = user.get("password");
        String regex = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        String phoneRegex = "1\\d{10}";
        UcenterMember member;
        // 如果是邮箱
        if (Pattern.compile(regex).matcher(username).matches()){
            member = memberService.loginByEmail(username, password);
        }else if (Pattern.compile(phoneRegex).matcher(username).matches()){
            //手机号
            member = memberService.loginByMobile(username, password);
        }else {
            throw new CustomException("账号格式不正确");
        }
        if (member != null){
            return Result.ok().addData("token",createJwtSource(member));
        }
        throw new CustomException("登录失败");
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result registerUser(@Validated @RequestBody MemberVo member) throws CustomException {
        //两次密码不一致
        if (!member.getPassword().equals(member.getConfirmPassword())) {
            throw new CustomException("密码不一致");
        }
        //先到redis确认是否有验证码
        String valid = (String)redisTemplate.opsForValue().get("code::" + member.getEmail());
        if (!member.getValidateCode().equals(valid)){
            throw new CustomException("验证码不一致");
        }
        UcenterMember register = new UcenterMember();
        BeanUtils.copyProperties(member,register);
        register = memberService.register(register);
        if (register!=null){
            redisTemplate.delete("code::"+member.getEmail());
            return Result.ok().addData("token",createJwtSource(register));
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
     * @return  返回jwt
     */
    public String createJwtSource(UcenterMember member){
        //生成jwt
        Map<String,Object> map = new HashMap<>();
        map.put("id",member.getId());
        return JWTUtils.createJWT(map);
    }
}

