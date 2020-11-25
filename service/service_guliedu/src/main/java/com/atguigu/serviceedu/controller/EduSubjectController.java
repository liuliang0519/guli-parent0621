package com.atguigu.serviceedu.controller;


import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.Result;
import com.atguigu.serviceedu.entity.subject.OneSubjectVo;
import com.atguigu.serviceedu.entity.vo.SubjectReadVo;
import com.atguigu.serviceedu.handler.ExcelListener;
import com.atguigu.serviceedu.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    @Autowired
    EduSubjectService eduSubjectService;

    //课程分类列表  url:/serviceedu/subject/getSubjectList
    @GetMapping("/getSubjectList")
    public Result getSubjectList(){

        //返回list集合 list集合泛型 一级分类的vo类

        List<OneSubjectVo> list =
                eduSubjectService.getAllSubjectList();


        return Result.ok().data("subjectList",list);
    }

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

