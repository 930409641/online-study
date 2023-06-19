package com.yxq.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * aliyun视频点播是基于阿里云OSS提供的资源存储服务，您可以在视频点播控制台的服务
 * 区域内分配独立的存储Bucket，用于存储该服务区域下的音视频和末班
 */
public class InitVodClient {
    //填入AccessKey信息
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret); //根据视频接入地域，获取accessKey，返回客户端
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
