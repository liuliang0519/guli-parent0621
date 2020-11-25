package com.atguigu.eduoos.service.impl;

import com.aliyun.oss.OSSClient;
import com.atguigu.eduoos.service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFIleOss(MultipartFile file) {
        //调用OSS对象象的方法实现具体操作
        //先获取上传文件的输入流
        //第一步  定义需要的固定值 地域节点 id  秘钥
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI4G3JqujYo4jFAaEqmDRJ";
        String accessKeySecret = "ueWlv3zQDVYxBiqkPQUxgJ7XCo0Tai";


        String bucketName = "java0621-teacher";
        String url = "";


        try {
            //第二步  创建OSS对象 传递三个固定值
            OSSClient ossClient
                    = new OSSClient(endpoint,accessKeyId,accessKeySecret);


            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            //得到文件路径和名称
            String filename = file.getOriginalFilename();
            //文件名称里面添加uuid值  保证每个文件名称不一样
            filename = UUID.randomUUID()
                 .toString().replace("-","") + filename;

            //根据当前的日期  创建文件夹  存储文件
            String filePath = new DateTime().toString("yyyy/MM/dd");

            // 2020/10/26/123.jpg 的格式
            filename = filePath+"/"+filename;

            //三个参数
            //第三步 调用oss对象的方法实现具体操作
            // 第一个参数bucket名称  第二个文件路径和名称   第三个输入流
            ossClient.putObject(bucketName,filename,inputStream);

            //第四步 关闭OSS对象
            ossClient.shutdown();

            //https://java0621-teacher.oss-cn-beijing.aliyuncs.com/111.jpg
            url = "https://"+bucketName +"."+ endpoint +"/"+ filename;
            System.out.println(url);
            return url;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;

//


    }
}
