package com.yxq.servicebase.exceptionhandler;

import com.yxq.commonutils.R;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 这个类是为那些声明了（@ExceptionHandler、@InitBinder 或 @ModelAttribute注解修饰的）
 * 方法的类而提供的专业化的@Component , 以供多个 Controller类所共享。
 *
 * 说白了，就是aop思想的一种实现，你告诉我需要拦截规则，我帮你把他们拦下来，具体你想做更细致的拦截筛选和拦截之后的处理，
 * 你自己通过@ExceptionHandler、@InitBinder 或 @ModelAttribute这三个注解以及被其注解的方法来自定义。
 */
//日志级别 error warn info debug all
@Slf4j //lombok中集成了slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//指定出现什么异常执行这个方法
    @ResponseBody//为了返回json数据
    public R error(Exception e) {
        e.printStackTrace();  //这个数据是返回给控制台的
        return R.error().message("执行了统一异常处理");  //这个数据是返回给前端的
    }

    //特定异常
    //先找特定异常，Exception兜底
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException特定异常处理");
    }



    //自定义异常 用的很多
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        log.error(e.getMessage());  //可以将错误信息打印到文件中
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());  //返回给前端的值
    }
}
