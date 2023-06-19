package com.yxq.serviceedu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.yxq.serviceedu.entity.EduSubject;
import com.yxq.serviceedu.entity.excel.SubjectData;
import com.yxq.serviceedu.entity.subject.OneSubject;
import com.yxq.serviceedu.entity.subject.TwoSubject;
import com.yxq.serviceedu.listener.SubjectExcelListener;
import com.yxq.serviceedu.mapper.EduSubjectMapper;
import com.yxq.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-08-05
 */
@Transactional
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        //获取输入流
        try {
            InputStream is = file.getInputStream();
            EasyExcel.read(is, SubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead(); //监听器中完成核心业务

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现思路
     * 1. 获取所有一级分类，封装到一个List集合中，再获取所有二级分类封装到一个List集合中
     * 2. 封装一级分类，获取一级分类，根据每一个一级分类再遍历二级分类，获取这个一级分类中的二级分类，然后封装到一级分类的child属性当中
     * 3. 依次遍历一级分类，重复2
     * @return
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1查询出所有一级分类 parent_id=0

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapper);
        //2查询出所有二级分类 parent_id!=0

        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapper2);

        //3封装一级分类
        List<OneSubject> finnalList = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            EduSubject eduSubject = oneSubjectList.get(i);

            //new OneSubject设置值，add加入list
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //复制操作
            BeanUtils.copyProperties(eduSubject, oneSubject);

            finnalList.add(oneSubject);
            //4封装二级分类
            //创建list集合封装每一个一级分类的二级分类
            ArrayList<TwoSubject> twoFinnalList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                EduSubject eduSubject2 = twoSubjectList.get(j);
                if (eduSubject.getId().equals(eduSubject2.getParentId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    //如过一级分类的id==二级分类的parent_id,进行封装
                    BeanUtils.copyProperties(eduSubject2, twoSubject);
                    twoFinnalList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinnalList);
        }

        return finnalList;
    }
}
