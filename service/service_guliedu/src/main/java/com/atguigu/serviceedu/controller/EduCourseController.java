package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.Result;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.serviceedu.mapper.EduCourseMapper;
import com.atguigu.serviceedu.service.EduCourseService;
import org.apache.ibatis.annotations.Update;
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
@RequestMapping("/serviceedu/course")
@CrossOrigin
public class EduCourseController {


    @Autowired
    private EduCourseService eduCourseService;





    //根据课程id删除课程（同时删除 关联表数据）
    //删除课程时  需要删除课程本身，删除描述，删除章节，删除小节，删除小节里的视屏
    @DeleteMapping("deleteCourseById/{id}")
    public Result deleteCourseById(@PathVariable String id){
        eduCourseService.removeCourse(id);
        return Result.ok();
    }


    //TODO 条件查询带分页
    //基本的课程列表功能
    @GetMapping("/getCourseList")
    public Result getCourseList(){
        List<EduCourse> list = eduCourseService.list(null);
        return Result.ok().data("courseList",list);
    }

    ///serviceedu/course/publishCourse/{id}
    //课程最终发布  修改字段status
    @PutMapping("/publishCourse/{id}")
    public Result publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return Result.ok();

    }


    //根据id修改课程的基本信息  /serviceedu/course/updateCourseInfo
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.ok().data("courseId",id);

    }



    //  /serviceedu/course/addCourseInfo
    //添加课程的基本信息
    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        String courseId = eduCourseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId",courseId);
    }

    //根据id查询课程信息
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo =
                eduCourseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfo",courseInfoVo);
    }


    ///serviceedu/course/getCoursePublishVoById/{courseId}
    //最终发布课程页面显示信息
    @GetMapping("/getCoursePublishVoById/{courseId}")
    public Result getCoursePublishVoById(@PathVariable String courseId){
        System.out.println("==============");
        CoursePublishVo coursePublishVo = eduCourseService.getCoursePublishVoById(courseId);
        return Result.ok().data("coursePublishVo",coursePublishVo);
    }

 }

