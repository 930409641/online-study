package com.yxq.serviceedu.service.impl;


import com.yxq.servicebase.exceptionhandler.GuliException;
import com.yxq.serviceedu.entity.EduCourse;
import com.yxq.serviceedu.entity.EduCourseDescription;
import com.yxq.serviceedu.entity.frontVo.CourseQueryVo;
import com.yxq.serviceedu.entity.frontVo.CourseWebVo;
import com.yxq.serviceedu.entity.vo.CourseInfoVo;
import com.yxq.serviceedu.entity.vo.CoursePublishVo;
import com.yxq.serviceedu.mapper.EduCourseMapper;
import com.yxq.serviceedu.service.EduChapterService;
import com.yxq.serviceedu.service.EduCourseDescriptionService;
import com.yxq.serviceedu.service.EduCourseService;
import com.yxq.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-06
 */
@Transactional
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

    //添加课程信息
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加课程信息，从CourseInfoVo获取课程信息，封装到EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }
        //获取保存后的id，与课程描述建立关系
        String CourseId = eduCourse.getId();
        //向课程简介添加课程简介 ，从CourseInfoVo中获取描述信息封装到EduCourseDescription
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo, description);
        description.setId(CourseId);
        eduCourseDescriptionService.save(description);

        return CourseId;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1查询课程表类容
        EduCourse eduCourse = baseMapper.selectById(courseId);

        //封装到CourseInfoVo中
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);

        //2查询描述表
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo coursePublishVo = baseMapper.getPublishCourseInfo(id);
        return coursePublishVo;
    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //根据课程id删除小节和视频
        eduVideoService.removeByCourseId(courseId);
        //根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //根据课程id删除课程描述
        eduCourseDescriptionService.removeById(courseId);
        //根据课程id删除课程本身
        int i = baseMapper.deleteById(courseId);
        if (i == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }

    @Override
    public Map<String, Object> getTeacherInfo(Page<EduCourse> queryVoPage, CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        //判断条件是否为空
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())){//一级分类
            queryWrapper.eq("subject_parent_id",courseQueryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())){//二级分类
            queryWrapper.eq("subject_id",courseQueryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) {//销量排序
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) {//时间排序
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getPriceSort())) {//价格排序
            queryWrapper.orderByDesc("price");
        }

        //封装到page里面
        baseMapper.selectPage(queryVoPage, queryWrapper);

        long total = queryVoPage.getTotal();
        List<EduCourse> records = queryVoPage.getRecords();
        long current = queryVoPage.getCurrent();
        long size = queryVoPage.getSize();
        boolean hasNext = queryVoPage.hasNext();
        boolean hasPrevious = queryVoPage.hasPrevious();
        long pages = queryVoPage.getPages();

        HashMap<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String subjectParentId = courseQueryVo.getSubjectParentId(); //一级分类
        String subjectId = courseQueryVo.getSubjectId(); //二级分类
        String buyCountSort = courseQueryVo.getBuyCountSort(); //关注都
        String priceSort = courseQueryVo.getPriceSort(); //价格
        String gmtCreateSort = courseQueryVo.getGmtCreateSort(); //时间
        if(!StringUtils.isEmpty(subjectParentId)) {  //判断一级分类是否为空
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(subjectId)) {  //判断二级分类是否为空
            wrapper.eq("subject_id",subjectId);
        }

        if(!StringUtils.isEmpty(buyCountSort)) {  //判断关注度是否为空
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(priceSort)) {  //判断价格
            wrapper.orderByDesc("price");
        }
        if(!StringUtils.isEmpty(gmtCreateSort)) {  //判断时间
            wrapper.orderByDesc("gmt_create");
        }

        baseMapper.selectPage(pageParam,wrapper);


        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
