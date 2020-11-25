package com.atguigu.serviceedu.controller.front;

import com.atguigu.commonutils.Result;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.service.EduCourseService;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/serviceedu/frontteacher")
public class EduTeacherFrontController {

    @Autowired
    EduTeacherService teacherService;

    @Autowired
    EduCourseService courseService;

    //讲师详情接口   /serviceedu/frontteacher/getTeacherInfo/{id}
    @GetMapping("getTeacherInfo/{id}")
    public Result getTeacherInfo(@PathVariable String id){
        //根据讲师id查询讲师
        EduTeacher teacher = teacherService.getById(id);

        //根据讲师id查询课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse>  coursesList = courseService.list(wrapper);

        return Result.ok().data("teacher",teacher)
                          .data("courseList",coursesList);
    }



    //分页查询讲师的方法
    //  serviceedu/frontteacher/getPageTeacher/{current}/{limit}
    @GetMapping("getPageTeacher/{current}/{limit}")
    public Result getPageTeacher(
            @PathVariable long current, //当前页
            @PathVariable long limit){  //每页记录数

        Page<EduTeacher> pageParam = new Page<>(current,limit);
        teacherService.page(pageParam,null);

        //把分页的所有数据 都返回
        List<EduTeacher> records = pageParam.getRecords();
        long currentPage = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", currentPage);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return Result.ok().data("map",map);
    }



}
