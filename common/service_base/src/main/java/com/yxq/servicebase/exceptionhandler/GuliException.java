package com.yxq.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException{  //业务中假如出现问题的地方，可以抛出自定义异常，项目做到统一处理
    private Integer code; //状态码
    private String msg; //异常信息
}
