package com.yxq.eduorder.controller;


import com.yxq.commonutils.R;
import com.yxq.eduorder.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-19
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class TPayLogController {

    @Autowired
    private TPayLogService payLogService;

    //生成微信支付二维码接口
    //参数是订单号
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        //返回信息，包含二维码地址，还有其他信息

        Map map = payLogService.createNative(orderNo);

        return R.ok().data(map);
    }

    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if(map == null){
            return R.error().message("支付出错");
        }
        //如果返回不为空
        if(map.get("trade_status").equals("SUCCESS")) {
            //添加记录到支付表中，更新订单信息
            payLogService.udateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().message("支付中");
    }
}