package com.atguigu.cmsservice.service.impl;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.mapper.CrmBannerMapper;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-11-04
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    RedisTemplate redisTemplate;
    //首先显示3张最新幻灯片

    @Override
    public List<CrmBanner> selevtIndexBanner() {

        //1 先查询缓存  没有再查数据库
        //redis 数据库 key  banner
        List<CrmBanner> bannerList = (List<CrmBanner>)redisTemplate.opsForValue().get("banner");
        if (bannerList != null){
            System.out.println("========从缓存加载");
            System.out.println("========从缓存加载");
            return bannerList;

        }else {
            QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
            //根据id排序  查询前三条记录
            wrapper.orderByDesc("id");
            wrapper.last("limit 3"); //sql 语句拼接
            List<CrmBanner> bannerList2 = baseMapper.selectList(wrapper);
            redisTemplate.opsForValue().set("banner",bannerList2);
            System.out.println("========从数据库加载");
            System.out.println("========从数据库加载");
            return bannerList2;
        }

    }
}
