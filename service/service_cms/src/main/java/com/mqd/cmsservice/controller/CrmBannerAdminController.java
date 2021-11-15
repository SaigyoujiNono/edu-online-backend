package com.mqd.cmsservice.controller;

import com.mqd.cmsservice.pojo.CrmBanner;
import com.mqd.cmsservice.service.CrmBannerService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.Resources;

/**
 * banner管理
 */
@Api("banner管理")
@RestController
@RequestMapping("/cmsservice/banner")
@CrossOrigin
public class CrmBannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "通过id来删除banner")
    @DeleteMapping("/banner/{id}")
    public Result deleteBanner(@PathVariable Integer id) throws CustomException {
        boolean b = bannerService.removeById(id);
        if (b){
            return Result.ok();
        }
        throw new CustomException("删除失败");
    }

    @ApiOperation(value = "通过id来修改banner信息")
    @PutMapping("/banner")
    public Result updateBanner(CrmBanner banner) throws CustomException {
        boolean b = bannerService.updateById(banner);
        if (b){
            return Result.ok();
        }
        throw new CustomException("修改失败");
    }

    @ApiOperation(value = "添加一个banner信息")
    @PostMapping("/banner")
    public Result addBanner(CrmBanner banner) throws CustomException {
        boolean b = bannerService.save(banner);
        if (b){
            return Result.ok();
        }
        throw new CustomException("添加失败");
    }
}
