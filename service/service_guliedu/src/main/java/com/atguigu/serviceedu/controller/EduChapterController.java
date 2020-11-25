package com.atguigu.serviceedu.controller;


import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.commonutils.Result;
import com.atguigu.eduoos.servicebase.config.exception.GuliException;
import com.atguigu.serviceedu.controller.front.initObject;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.entity.chapterVo.ChapterVo;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-10-29
 */
@RestController
@RequestMapping("/serviceedu/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private initObject initObject;

    @Autowired
    private EduVideoService videoService;



    //查询指定课程里面所有的 章节和 小节数据
    @GetMapping("getChapterVideo/{courseId}")
    private Result getChapterVideoByCourseId(@PathVariable String courseId){

        List<ChapterVo> list = chapterService.getAllChapterVideo(courseId);
        return Result.ok().data("chapterVideo",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter eduChapter) {
        chapterService.save(eduChapter);
        return Result.ok();
    }

    //删除章节
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable String id){
        chapterService.deleteById(id);
        return Result.ok();
    }


    //根据id查询章节
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable String id){
        EduChapter eduChapter = chapterService.getById(id);
        System.out.println("=========="+eduChapter);
        return Result.ok().data("chapter",eduChapter);
    }

    //修改章节
    @PutMapping("updateChapter")
    public Result updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return Result.ok();
    }

    //通过视频id 获取视频凭证
    @GetMapping("getPlayerAuth/{videoId}")
    public Result getVideoAuth(@PathVariable String videoId){
        try {
            //创建 初始化对象
            DefaultAcsClient client = initObject.initVodClient("LTAI4G3JqujYo4jFAaEqmDRJ", "ueWlv3zQDVYxBiqkPQUxgJ7XCo0Tai");
            //创建获取视频播放凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            //request设置视频id
            request.setVideoId(videoId);
            //调用初始化对象的方法
            response = client.getAcsResponse(request);
            //通过response对象获取凭证
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"获取失败");
        }


    }


    //根据小节id获取视频id
    @GetMapping("getPlayerId/{id}")
    public Result getPlayerId(@PathVariable String id){
        EduVideo video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        return Result.ok().data("videoSourceId",videoSourceId);
    }
}

