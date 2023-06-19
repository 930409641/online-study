package com.yxq.serviceedu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//VO(view object)可视层对象,用于给前端显示的对象。(只传递有需要的参数以保障数据安全)
@Data
public class TeachQuery {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师名称,模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
