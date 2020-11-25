package com.atguigu.orderservice.client;

import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.vo.MemberOrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-ucenter")
@Component
public interface MemberClient {

    //根据用户id获取用户信息  /ucenterservice/member
    @GetMapping("/ucenterservice/member/getMemberOrder/{id}")
    public MemberOrderInfo getMemberOrder(@PathVariable("id") String id);

}
