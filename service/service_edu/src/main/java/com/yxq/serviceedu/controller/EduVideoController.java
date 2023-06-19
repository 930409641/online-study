package com.yxq.serviceedu.controller;


import com.yxq.commonutils.R;
import com.yxq.servicebase.exceptionhandler.GuliException;
import com.yxq.serviceedu.client.VodClient;
import com.yxq.serviceedu.entity.EduVideo;
import com.yxq.serviceedu.entity.vo.VideoInfoForm;
import com.yxq.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-08-06
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //刪除小节
    //删除小节同时把小节中的视频删除
    //删除是视频使用openFeign进行方法调用
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        System.out.println(id);
        //根据小节id查询出视频id，进行删除
        EduVideo eduVideobyId = eduVideoService.getById(id);
        String videoSourceId = eduVideobyId.getVideoSourceId();
        //判断是否有视频,有就删除
        if (!StringUtils.isEmpty(videoSourceId)) {
            //远程调用vod删除视频
            vodClient.removeVideo(videoSourceId);
        }
        //删除完视频后再删除小节
        eduVideoService.removeById(id);
        return R.ok();
    }


    //根据id获取课时信息

    @GetMapping("video/info/{id}")
    public R getVideoInfoById(@PathVariable String id) {

        VideoInfoForm videoInfoById = eduVideoService.getVideoInfoById(id);

        return R.ok().data("item",videoInfoById);
    }


    @PutMapping("")
    public R updateVideoInfoById(@RequestBody VideoInfoForm videoInfoForm) {

        Integer count = eduVideoService.updateVideoInfoById(videoInfoForm);
        if(count < 0) {
            throw new GuliException(20001,"更新失败");
        }

        return R.ok();
    }


}

