package com.mqd.eduuser.pojo.vo;

import com.mqd.validate.Password;
import com.mqd.validate.PhoneNoConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MemberVo对象", description="会员表单参数")
public class MemberVo {
    @PhoneNoConstraint
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @Email(message = "请输入正确的邮箱地址")
    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @NotBlank
    @Size(min = 6,max = 16,message = "密码必须为6-16位")
    @Password
    @ApiModelProperty(value = "密码")
    private String password;

    @Size(min = 6,max = 16,message = "密码必须为6-16位")
    @Password
    @NotBlank(message = "请输入确认密码")
    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;

    @NotBlank(message = "请输入验证码")
    @ApiModelProperty(value = "验证码")
    private String validateCode;

}
