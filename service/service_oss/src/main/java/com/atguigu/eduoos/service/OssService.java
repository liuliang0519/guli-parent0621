package com.atguigu.eduoos.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    //上传图片到阿里云OSS
    String uploadFIleOss(MultipartFile file);
}
