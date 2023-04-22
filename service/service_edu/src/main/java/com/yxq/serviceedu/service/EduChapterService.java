package com.yxq.serviceedu.service;

import com.yxq.serviceedu.entity.EduChapter;
import com.yxq.serviceedu.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-06
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVoByCourseId(String courseId);

    boolean deleteChapterById(String chapterId);

    void removeChapterByCourseId(String courseId);

}
