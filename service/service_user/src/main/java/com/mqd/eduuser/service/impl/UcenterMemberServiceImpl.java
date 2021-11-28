package com.mqd.eduuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqd.eduuser.pojo.UcenterMember;
import com.mqd.eduuser.mapper.UcenterMemberMapper;
import com.mqd.eduuser.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mqd.exception.CustomException;
import com.mqd.utils.MD5Utils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author mqd
 * @since 2021-11-15
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public UcenterMember loginByEmail(String email, String password) throws CustomException {
        QueryWrapper<UcenterMember> memberQuery = new QueryWrapper<>();
        memberQuery.eq("email",email);
        UcenterMember member = baseMapper.selectOne(memberQuery);
        if (member == null){
            throw new CustomException(email+"邮箱不存在");
        }
        //对password进行加密比对
        if (MD5Utils.toMD5(password,member.getId()).equals(member.getPassword())) {
            member.setPassword(null);
            //放入缓存
            saveRedis(member);
            return member;
        }
        throw new CustomException("密码错误");
    }

    @Override
    public UcenterMember loginByMobile(String mobile, String password) throws CustomException {
        QueryWrapper<UcenterMember> memberQuery = new QueryWrapper<>();
        memberQuery.eq("mobile",mobile);
        UcenterMember member = baseMapper.selectOne(memberQuery);
        if (member == null){
            throw new CustomException(mobile+"手机不存在");
        }
        //对password进行加密比对
        if (MD5Utils.toMD5(password,member.getId()).equals(member.getPassword())) {
            member.setPassword(null);
            //放入缓存
            saveRedis(member);
            return member;
        }
        throw new CustomException("密码错误");
    }

    @Transactional(rollbackFor = Exception.class)//开启事务
    public UcenterMember register(UcenterMember member) throws CustomException {
        // 判断手机号是否存在
        if (StringUtils.hasText(member.getMobile())){
            QueryWrapper<UcenterMember> memberQuery = new QueryWrapper<>();
            memberQuery.eq("mobile", member.getMobile());
            UcenterMember ucenterMember = baseMapper.selectOne(memberQuery);
            if (ucenterMember != null){
                throw new CustomException(member.getMobile()+"手机号已存在");
            }
        }
        // 判断邮箱是否存在
        QueryWrapper<UcenterMember> memberQuery = new QueryWrapper<>();
        memberQuery.eq("email", member.getEmail());
        UcenterMember ucenterMember = baseMapper.selectOne(memberQuery);
        if (ucenterMember != null){
            throw new CustomException(member.getEmail()+"邮箱已存在");
        }
        int insert = baseMapper.insert(member);
        if (insert <= 0){
            throw new CustomException("注册失败");
        }
        String md5 = MD5Utils.toMD5(member.getPassword(), member.getId());
        member.setPassword(md5);
        int i = baseMapper.updateById(member);
        if (i <= 0) {
            throw new CustomException("注册失败");
        }
        member.setPassword(null);
        return member;
    }

    public void saveRedis(UcenterMember member){
        ValueOperations<String, Object> vo = redisTemplate.opsForValue();
        //缓存时间 5分钟
        vo.set("user::"+member.getId(),member,1, TimeUnit.DAYS);
    }
}
