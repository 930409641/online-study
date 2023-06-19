package com.yxq.serviceedu.service;

import com.yxq.serviceedu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yxq.serviceedu.entity.chapter.VideoVo;
import com.yxq.serviceedu.entity.vo.VideoInfoForm;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-06
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);

    VideoInfoForm getVideoInfoById(String id);

    Integer updateVideoInfoById(VideoInfoForm videoInfoForm);
}
