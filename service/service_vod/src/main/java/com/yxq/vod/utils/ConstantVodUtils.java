package com.yxq.vod.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean { //初始化服务，在组件初识化之后，进行赋值

    @Value("${aliyun.vod.file.keyid}")
    private String keyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    public static String ACCESS_KEY_SECRET;

    public static String ACCESS_KEY_ID;
    @Override
    public void afterPropertiesSet() throws Exception { //初始化完成后进行赋值
        ACCESS_KEY_ID = keyId;
        ACCESS_KEY_SECRET = keySecret;
    }
}
