package com.yxq.educenter.controller;


import com.yxq.educenter.utils.ConstantPropertiesUtil;
import com.yxq.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    //生成微信扫描二维码
    @GetMapping("login")
    public String getWxCode() {
        //固定地址，后面拼接参数
        String url = "https://open.weixin.qq.com/connect/qrconnect"+
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        String.format(
                url,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "atguigu"
        );
        //重定向到请求微信地址
        return "redirect:"+url;
    }
}
