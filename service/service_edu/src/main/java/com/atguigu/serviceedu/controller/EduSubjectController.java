package com.atguigu.serviceedu.controller;


import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.Result;
import com.atguigu.serviceedu.entity.vo.SubjectReadVo;
import com.atguigu.serviceedu.handler.ExcelListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/serviceedu/subject")
@CrossOrigin //允许跨域访问
public class EduSubjectController {

    @Autowired
    private ExcelListener excelListener;

    //添加课程分类
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file){


        try {
            //1.获取上传 文件的 输入流
            //2.创建 和excel表对应的实体类
            //3.写一个监听器
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream,   //流
                           SubjectReadVo.class,//实体类
                           excelListener) //监听器
                    .sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return Result.ok();
    }



}

