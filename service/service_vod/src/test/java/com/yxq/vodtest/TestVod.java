package com.yxq.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {


    public static void main(String[] args) throws ClientException {
//        String accessKeyId = "LTAI5tLDVhuBiB5KvpTapzcw";
//        String accessKeySecret = "4aNcBBY2UdW14JLQGWYahRNvfbolJb";
//        //1.音视频上传-本地文件上传
//        //视频标题(必选)
//        String title = "3 - How Does Project Submission Work - upload by sdk.mp4"; //上传文件名称
//        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
//        //文件名必须包含扩展名
//        String fileName = "D:/谷粒商城资料/项目资料(1)/1-阿里云上传测试视频/6 - What If I Want to Move Faster.mp4";
//        //本地文件上传
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
//        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
//        request.setPartSize(1 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setTaskNum(1);
//    /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
//        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
//        request.setEnableCheckpoint(false);
//
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        } else {
//            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//            System.out.print("ErrorCode=" + response.getCode() + "\n");
//            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//        }
        getPlaypingzheng();
    }

    public static void getPlaypingzheng() throws ClientException {
        //根据视频id获取视频播放凭证
        //创建初始化对象
        DefaultAcsClient c1 = InitObject.initVodClient("LTAI5tLDVhuBiB5KvpTapzcw", "4aNcBBY2UdW14JLQGWYahRNvfbolJb");

        //创建获取视频凭证request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //向request设置视频id
        request.setVideoId("bf0a8a20d9f471edbfa90675b3ed0102");
        //调用初始化对象的方法得到凭证
        response = c1.getAcsResponse(request);
        System.out.println(response.getPlayAuth());

    }

    public static void getPlayUrl() throws ClientException {
        //根据视频ID获取视频播放地址
        //创建初始化对象
        DefaultAcsClient c1 = InitObject.initVodClient("LTAI5tLDVhuBiB5KvpTapzcw", "4aNcBBY2UdW14JLQGWYahRNvfbolJb");

        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象里面设置视频id
        request.setVideoId("3db29970d8f771edbfa60764a0ec0102");
        //调用初始化对象里面的方法，传递request，获取数据
        response = c1.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        //System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

    }


}
