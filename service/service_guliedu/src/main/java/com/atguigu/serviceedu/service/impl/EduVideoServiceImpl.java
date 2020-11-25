package com.atguigu.serviceedu.service.impl;

import com.atguigu.eduoos.servicebase.config.exception.GuliException;
import com.atguigu.serviceedu.client.VodClient;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.mapper.EduVideoMapper;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-29
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {


    @Autowired
    private VodClient vodClient;

    //根据 小节id删除小节  同时删除视频
    @Override
    public void deleteVideoById(String id) {

        //先查小节 删除视频
        System.out.println("======================" + id);
        EduVideo eduVideo = baseMapper.selectById(id);
        String courseId = eduVideo.getVideoSourceId();
        System.out.println("======================" + courseId);

        if (!StringUtils.isEmpty(courseId)){
            System.out.println("=============删除视频");
            //远程调用进行删除
            vodClient.deleteVideo(courseId);
        }

        //再删小节
        System.out.println("=============再删小节");
        int i = baseMapper.deleteById(id);
        if (i <= 0) {
           throw new GuliException(20001,"删除失败");
        }

    }
}
