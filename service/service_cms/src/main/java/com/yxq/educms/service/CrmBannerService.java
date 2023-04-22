package com.yxq.educms.service;

import com.yxq.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-16
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectListBanner();
}
