package com.yxq.serviceedu.service.impl;

import com.yxq.commonutils.R;
import com.yxq.servicebase.exceptionhandler.GuliException;
import com.yxq.serviceedu.client.VodClient;
import com.yxq.serviceedu.entity.EduVideo;
import com.yxq.serviceedu.entity.vo.VideoInfoForm;
import com.yxq.serviceedu.mapper.EduVideoMapper;
import com.yxq.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-06
 */
@Transactional
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String courseId) {
        //根据课程id查出所有视频的id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        //封装video_source_id  1,2,3
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < eduVideos.size(); i++) {
            EduVideo eduVideo = eduVideos.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!videoSourceId.isEmpty()) {
                list.add(videoSourceId);
            }
        }
        //list不为空
        if (list.size() > 0) {
            //删除小节里的所有视频
            R result = vodClient.deleteBatch(list);
            if (result.getCode() == 20001) {
                throw new GuliException(20001, "删除视频失败，熔断器...");
            }
        }
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        baseMapper.delete(queryWrapper);
    }

    // 根据创获取小节信息
    @Override
    public VideoInfoForm getVideoInfoById(String id) {
        EduVideo eduVideo = baseMapper.selectById(id);
        if(StringUtils.isEmpty(eduVideo)) {
            throw new GuliException(20001,"数据不存在");
        }

        //创建VideoInfoForm对象
        VideoInfoForm videoInfoForm = new VideoInfoForm();
        BeanUtils.copyProperties(eduVideo,videoInfoForm);
        return videoInfoForm;
    }

    @Override
    public Integer updateVideoInfoById(VideoInfoForm videoInfoForm) {
        //创建一个Video
        EduVideo eduVideo = new EduVideo();
        //将videoInfoForm中的数据封装到video中
        BeanUtils.copyProperties(videoInfoForm,eduVideo);

        //根据id更新video
        int i = baseMapper.updateById(eduVideo);

        return i;
    }
}
