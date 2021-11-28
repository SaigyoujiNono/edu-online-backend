package com.mqd.eduuser.service;

import com.mqd.eduuser.pojo.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mqd.exception.CustomException;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author mqd
 * @since 2021-11-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {
    //邮箱登录
    UcenterMember loginByEmail(String email, String password) throws CustomException;

    //手机登录
    UcenterMember loginByMobile(String mobile, String password) throws CustomException;

    //注册
    UcenterMember register(UcenterMember member) throws CustomException;
}
