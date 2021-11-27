package com.mqd.cmsservice.service;

import com.mqd.cmsservice.pojo.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mqd.exception.CustomException;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author mqd
 * @since 2021-11-08
 */
public interface CrmBannerService extends IService<CrmBanner> {
    List<CrmBanner> getHomeBanner() throws CustomException;
}
