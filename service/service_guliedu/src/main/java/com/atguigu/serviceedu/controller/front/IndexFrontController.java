package com.atguigu.serviceedu.controller.front;

import com.atguigu.commonutils.Result;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.service.EduCourseService;
import com.atguigu.serviceedu.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/serviceedu/indexFront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;


    //显示主页热门信息
    @GetMapping("getHotInfo")
    public Result getHotInfo(){
        List<EduCourse> hotCourses = courseService.getHotCourse();
        List<EduTeacher> hotTeachers = teacherService.getHotTeacher();

        return Result.ok().data("courseList",hotCourses)
                          .data("teacherList",hotTeachers);
    }




}
