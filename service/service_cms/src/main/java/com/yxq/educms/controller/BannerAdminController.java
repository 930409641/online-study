package com.yxq.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yxq.commonutils.R;
import com.yxq.educms.entity.CrmBanner;
import com.yxq.educms.service.CrmBannerService;
import com.yxq.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-16
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    //分页查询banner
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable Long page, @PathVariable Long limit) {
        Page<CrmBanner> bannerPage = new Page<>(page,limit);
        bannerService.page(bannerPage,null);
        return R.ok().data("items",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }

    //增加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner) {
        boolean save = bannerService.save(crmBanner);
        if(!save) {
            throw  new GuliException(20001,"添加失败");
        }
        return R.ok();
    }

    //根据id删除banner
    @DeleteMapping("deleteBannerById/{id}")
    public R deleteBannerById(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }
    //修改banner
    @PostMapping("updateBannerById")
    public R updateBannerById(@RequestBody CrmBanner crmBanner) {
        bannerService.updateById(crmBanner);
        return R.ok();
    }

    //根据id查询banner
    @GetMapping("getBannerById/{id}")
    public R getBannerById(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item",banner);
    }
}