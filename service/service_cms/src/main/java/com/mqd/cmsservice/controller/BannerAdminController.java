package com.mqd.cmsservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mqd.cmsservice.pojo.CrmBanner;
import com.mqd.cmsservice.pojo.vo.BannerVo;
import com.mqd.cmsservice.service.CrmBannerService;
import com.mqd.exception.CustomException;
import com.mqd.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * banner管理
 */
@Api("banner管理")
@RestController
@RequestMapping("/cmsservice/banner")
@CrossOrigin
public class BannerAdminController {

    @Resource
    private CrmBannerService bannerService;

    @ApiOperation(value = "条件查询banner+分页")
    @GetMapping("/banner")
    public Result getBanner(BannerVo bannerVo){
        Long current;   //当前页
        Long pageSize;  //页面大小
        if (bannerVo.getCurrent() != null && bannerVo.getCurrent() > 0){
            current = bannerVo.getCurrent();
        }else{
            current = 1L;
        }
        //分页大小
        if (bannerVo.getPageSize() != null && bannerVo.getPageSize() > 0 && bannerVo.getPageSize() <= 30){
            pageSize = bannerVo.getPageSize();
        }else{
            pageSize = 15L;
        }
        IPage<CrmBanner> page = new Page<>(current, pageSize);
        //构建条件查询
        QueryWrapper<CrmBanner> bannerQuery = new QueryWrapper<>();
        String title = bannerVo.getTitle();
        Boolean sort = bannerVo.getSort();
        Date startCreate = bannerVo.getStartCreate();
        Date endCreate = bannerVo.getEndCreate();
        Date startModified = bannerVo.getStartModified();
        Date endModified = bannerVo.getEndModified();
        if (StringUtils.hasText(title)){
            bannerQuery.like("title",title);
        }

        if (sort!=null){
            if (sort){
                bannerQuery.orderByAsc("sort");
            }else {
                bannerQuery.orderByDesc("sort");
            }
        }
        bannerQuery.orderByDesc("gmt_create");

        if (startCreate!=null){
            bannerQuery.ge("gmt_create",startCreate);
        }
        if (endCreate!=null){
            bannerQuery.le("gmt_create",endCreate);
        }
        if (startModified!=null){
            bannerQuery.ge("gmt_modified",startModified);
        }
        if (endModified!=null){
            bannerQuery.le("gmt_modified",endModified);
        }
        //查询

        page = bannerService.page(page, bannerQuery);
        return Result.ok().addData("bannerList",page.getRecords());
    }

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
