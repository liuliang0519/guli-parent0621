package com.atguigu.eduoos.servicebase.config;


import com.atguigu.commonutils.Result;
import com.atguigu.eduoos.servicebase.config.exception.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)  //遇到什么异常走这里
    @ResponseBody   //返回数据是json数据时 需要加此注解
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error();
    }

    @ExceptionHandler(ArithmeticException.class) //指定了数学异常走这里  如果错误是数学异常  优先走这里
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.error().message("执行了指定异常哈哈哈").code(999);
    }

    @ExceptionHandler(GuliException.class) //指定了自定义的异常走这里  如果错误是自定义的  优先走这里
    @ResponseBody
    public Result error(GuliException e){
        e.printStackTrace();
        log.error(e.getMsg());
        return Result.error().message(e.getMsg()).code(e.getCode());
    }


}