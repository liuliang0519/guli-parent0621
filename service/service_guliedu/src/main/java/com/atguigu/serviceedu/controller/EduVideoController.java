package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-10-29
 */
@RestController
@RequestMapping("/serviceedu/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;


    //根据小节id删除小节  同时删除阿里云视频
    @DeleteMapping("/deleteVideoById/{id}")
    public Result deleteVideoById(@PathVariable String id){
        eduVideoService.deleteVideoById(id);
        return Result.ok();
    }



    //添加小节
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return Result.ok();
    }


    //删除小节
    @DeleteMapping("/removeVideoById/{id}")
    public Result removeVideoById(@PathVariable String id){
        eduVideoService.removeById(id);
        return Result.ok();
    }


    //修改小节
    @PutMapping("/updateVideoById")
    public Result updateVideoById(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return Result.ok();
    }



    //  /serviceedu/video/getVideoById/{id}
    //根据id查询小节
    @GetMapping("/getVideoById/{id}")
    public Result getVideoById(@PathVariable String id){
        EduVideo eduVideo = eduVideoService.getById(id);
        return Result.ok().data("eduVideo",eduVideo);
    }




}

