package com.atguigu.serviceedu.client;

import com.atguigu.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("service-vod")  //指定要找的服务名称
@Component
public interface VodClient {


    //定义调用接口的方法 和 参数
    //删除视频接口  删除小节删除阿里云视频
    @DeleteMapping("/eduvod/vod/deleteVideo/{id}")
    public Result deleteVideo(@PathVariable("id") String id);


    //删除章节 批量删除阿里云视频
    @DeleteMapping("/eduvod/vod/deleteVideos")
    public Result deleteVideos(@RequestParam("ids") List<String> ids);

}
