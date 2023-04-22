package com.yxq.serviceedu.mapper;

import com.yxq.serviceedu.entity.EduCourse;
import com.yxq.serviceedu.entity.frontVo.CourseWebVo;
import com.yxq.serviceedu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2020-08-06
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getPublishCourseInfo(String id);

    CourseWebVo getBaseCourseInfo(String courseId);
}
