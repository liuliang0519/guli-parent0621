package com.atguigu.orderservice.client;

import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.vo.CourseOrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface CourseClient {

    //根据课程id 查询课程信息  /serviceedu/frontcourse/getCourseInfoOrder/{courseId}
    @GetMapping("/serviceedu/frontcourse/getCourseInfoOrder/{courseId}")
    public CourseOrderInfo getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
