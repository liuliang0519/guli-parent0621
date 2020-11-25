package com.atguigu.eduvod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.Result;
import com.atguigu.eduvod.service.VodService;
import com.atguigu.eduvod.utils.AliyunVideoUploadUtil;
import com.atguigu.eduvod.utils.ConstantPropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/eduvod/vod")
@CrossOrigin
public class VodController {

    @Autowired
    VodService vodService;

    //删除视频接口（单个删除）
    @DeleteMapping("deleteVideo/{id}")
    public Result deleteVideo(@PathVariable String id){
//        vodService.deleteVideoByVideoId(id);
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
        return Result.ok();
    }


    //删除视频接口 （批量删除）
    @DeleteMapping("deleteVideos")
    public Result deleteVideos(@RequestParam("ids") List<String> ids){
        try {
            System.out.println("====调用了远程批量删除方法");
            System.out.println("====调用了远程批量删除方法");
            //1 创建初始化对象
            DefaultAcsClient client =
                    AliyunVideoUploadUtil
                            .initVodClient(ConstantPropertiesUtil.ACCESS_KEY_ID,
                                    ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            //创建删除需要的对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            String videoIds = StringUtils.join(ids.toArray(), ",");
            request.setVideoIds(videoIds); //设置要删除的 视频id 可以写多个id 用‘，’隔开

            client.getAcsResponse(request);

        } catch (ClientException e) {
            e.printStackTrace();
        }
        return Result.ok();

    }



     //上传视频
    @PostMapping("/uploadVideo")
    public Result uploadVideoAliyun(MultipartFile file){

        String videoId = vodService.uploadVideo(file);

        return Result.ok().data("videoId",videoId);
    }
}
