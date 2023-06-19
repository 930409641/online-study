package com.yxq.serviceedu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形结构一级分类
 */
@Data
public class OneSubject {
    private String id;
    private String title;

    private List<TwoSubject> children = new ArrayList<>();
}
