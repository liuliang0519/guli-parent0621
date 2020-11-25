package com.atguigu.eduvod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.eduoos.servicebase.config.exception.GuliException;
import com.atguigu.eduvod.service.VodService;
import com.atguigu.eduvod.utils.AliyunVideoUploadUtil;
import com.atguigu.eduvod.utils.ConstantPropertiesUtil;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {

    //根据视频id删除视频
    @Override
    public void deleteVideoByVideoId(String id) {

        try {
            //1 创建初始化对象
            DefaultAcsClient client =
                    AliyunVideoUploadUtil
                            .initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建删除需要的对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id); //设置要删除的 视频id 可以写多个id 用‘，’隔开
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();       //和文件名称一样，不带后缀名
            String originalFilename = file.getOriginalFilename();  //文件的实际名称
            //使用subString 从0位到一个点（.）的位置 截取
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(
                    ConstantPropertiesUtil.ACCESS_KEY_ID,
                    ConstantPropertiesUtil.ACCESS_KEY_SECRET,
                    title, originalFilename, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。
            // 其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage =
                        "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();
                if(StringUtils.isEmpty(videoId)){
                    throw new GuliException(20001, errorMessage);
                }
            }

            return videoId;
        } catch (IOException e) {
            throw new GuliException(20001, "guli vod 服务上传失败");
        }

    }

}
