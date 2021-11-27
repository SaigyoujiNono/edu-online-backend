package com.mqd.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqd.cmsservice.pojo.CrmBanner;
import com.mqd.cmsservice.mapper.CrmBannerMapper;
import com.mqd.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mqd.exception.CustomException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author mqd
 * @since 2021-11-08
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {


    @Cacheable(key = "'banner'",value = "home")
    @Override
    public List<CrmBanner> getHomeBanner() throws CustomException {
        QueryWrapper<CrmBanner> bannerQuery = new QueryWrapper<>();
        bannerQuery.ge("sort","100");
        //选择前8个sort大于20的banner
        bannerQuery.orderByDesc("sort");
        List<CrmBanner> crmBanners = baseMapper.selectList(bannerQuery);
        if (crmBanners.isEmpty()){
            throw new CustomException("当前没有合适的数据");
        }
        return crmBanners;
    }
}
