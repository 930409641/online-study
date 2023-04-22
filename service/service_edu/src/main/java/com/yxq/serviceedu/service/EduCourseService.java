package com.yxq.serviceedu.service;

import com.yxq.serviceedu.entity.EduCourse;
import com.yxq.serviceedu.entity.frontVo.CourseQueryVo;
import com.yxq.serviceedu.entity.frontVo.CourseWebVo;
import com.yxq.serviceedu.entity.vo.CourseInfoVo;
import com.yxq.serviceedu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-06
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    void removeCourse(String courseId);


    Map<String, Object> getTeacherInfo(Page<EduCourse> queryVoPage, CourseQueryVo courseQueryVo);

    CourseWebVo getBaseCourseInfo(String courseId);


    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseQueryVo courseQueryVo);
}
