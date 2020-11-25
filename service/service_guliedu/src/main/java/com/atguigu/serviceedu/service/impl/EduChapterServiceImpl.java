package com.atguigu.serviceedu.service.impl;

import com.atguigu.eduoos.servicebase.config.exception.GuliException;
import com.atguigu.serviceedu.client.VodClient;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.entity.chapterVo.ChapterVo;
import com.atguigu.serviceedu.entity.chapterVo.VideoVo;
import com.atguigu.serviceedu.mapper.EduChapterMapper;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {







    @Autowired
    private EduVideoService videoService;

    @Autowired
    VodClient vodClient;

    //根据课程id返回 章节和课程的信息
    @Override
    public List<ChapterVo> getAllChapterVideo(String courseId) {
        //1 根据课程id 查询课程里的章节
        QueryWrapper<EduChapter> w1 = new QueryWrapper<>();
        w1.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(w1);

        //2 再根据课程id 查询课程里的小节
        QueryWrapper<EduVideo> w2 = new QueryWrapper<>();
         w2.eq("course_id",courseId);
         List<EduVideo> eduVideosList = videoService.list(w2);

         //new 出返回结果的vo集合
        List<ChapterVo> finalChapterVoList = new ArrayList<>();

        //遍历所有的章节  封装数据
        for (EduChapter chapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            finalChapterVoList.add(chapterVo);

            List<VideoVo> videoList = new ArrayList<>();
            for (int i = 0;i<eduVideosList.size();i++){

                EduVideo eduVideo = eduVideosList.get(i);
                if (eduVideo.getChapterId().equals(chapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }

            }
            System.out.println("++++++============"+videoList);
            chapterVo.setChildren(videoList);
        }

        return finalChapterVoList;

    }

    //根据id删除章节
    @Override
    public void deleteById(String id) {
        //先查看章节中有没有小节  有小节必须先删除小节
        //章节的id == 小节的chapterId
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        //根据章节id得到所有的小节
        List<EduVideo> list = videoService.list(wrapper);
        System.out.println(list);
        List ids = new ArrayList();
        //遍历所有的小节 取出所有的阿里云视频地址 存入集合中
        for (EduVideo eduVideo : list) {
            String videoSourceId = eduVideo.getVideoSourceId();
            ids. add(videoSourceId);
            System.out.println("======"+videoSourceId);
        }

        System.out.println(ids);


        if (!StringUtils.isEmpty(ids)){
            System.out.println("==============先删除视频");
            //远程调用批量删除
            vodClient.deleteVideos(ids);
        }
        System.out.println("==============再删除章节");
            //再删除章节
            baseMapper.deleteById(id);

    }
}

//        [{
//            id:1
//            title: 第一章
//            children：[
//                        {
//                            id:1-1
//                            title:第一节
//                        }，
//                        {
//                            id:1-2
//                            title:第二节
//                        }，
//
//                    ]
//
//        }]
