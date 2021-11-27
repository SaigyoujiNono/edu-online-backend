package com.mqd.cmsservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.cmsservice.pojo.CrmBanner;
import com.mqd.cmsservice.pojo.vo.BannerVo;
import com.mqd.cmsservice.service.CrmBannerService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author mqd
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/cmsservice/banner")
@CrossOrigin
public class BannerFrontController {

    @Resource
    private CrmBannerService bannerService;

    @ApiOperation(value = "首页显示banner，查询排序在100以上的所有banner")
    @GetMapping("/banner/home")
    public Result getBanner() throws CustomException {
        return Result.ok().addData("bannerList",bannerService.getHomeBanner());
    }



}

