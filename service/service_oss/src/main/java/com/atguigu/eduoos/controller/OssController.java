package com.atguigu.eduoos.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.eduoos.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/oss")
@CrossOrigin
public class OssController {

    @Autowired
    OssService ossService;

    //上传讲师头像
    @PostMapping("/upload")
    public Result upload(MultipartFile file){

        //获取要上传的文件 MultipartFile
        //调用service方法 实现上传头像到oss
        //返回 上传之后阿里云里面图片地址
        String url = ossService.uploadFIleOss(file);
        return Result.ok().data("url",url);
    }
}
