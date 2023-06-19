package com.yxq.serviceedu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import com.yxq.servicebase.exceptionhandler.GuliException;
import com.yxq.serviceedu.entity.EduSubject;
import com.yxq.serviceedu.entity.excel.SubjectData;
import com.yxq.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

/**
 * 不能交给spring管理，需要我们手动创建对象
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    //不能实现数据库操作，我们自己注入service
    public EduSubjectService eduSubjectService;

    //利用构造注入的方式注入service
    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    public SubjectExcelListener() {
    }

    //一行一行的读取记录
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        //如果excel中没有数据，抛出异常
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }
        //一行一行读，每次读取有两个值，第一个值以及分类，第二个值二级分类
        //判断一级分类
        EduSubject oneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if (oneSubject == null) {//没有相同的一级分类，进行添加
            oneSubject = new EduSubject();
            //真正要传入的数据
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            //service调用dao层实现数据持久化
            eduSubjectService.save(oneSubject);
        }

        //判断二级分类
        String pid = oneSubject.getId(); //
        EduSubject twoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if (twoSubject == null) {//没有相同的一级分类，进行添加
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(twoSubject);
        }
    }

    //判断一级分类，不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        EduSubject one = eduSubjectService.getOne(wrapper);
        return one;
    }

    //判断二级分类，不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        EduSubject one = eduSubjectService.getOne(wrapper);
        return one;
    }


    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
