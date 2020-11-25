package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.Result;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-11-04
 */
@RestController
@RequestMapping("/cmsservice/crmbanner")
@CrossOrigin
public class CrmBannerController {

    @Autowired
    CrmBannerService bannerService;

    //查询幻灯片  首页显示
    @GetMapping("selectNewBanner")
    public Result selectNewBanner(){
        List<CrmBanner> list = bannerService.selevtIndexBanner();
        return Result.ok().data("bannerList",list);
    }


}

