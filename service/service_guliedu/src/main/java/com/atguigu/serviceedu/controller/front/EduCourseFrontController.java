package com.atguigu.serviceedu.controller.front;

import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.vo.CourseOrderInfo;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.chapterVo.ChapterVo;
import com.atguigu.serviceedu.entity.frontVo.CourseInfoVo;
import com.atguigu.serviceedu.entity.frontVo.CourseQueryVo;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/serviceedu/frontcourse")
public class EduCourseFrontController {

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduChapterService chapterService;

    //根据课程id 查询课程信息  /serviceedu/frontcourse/getCourseInfoOrder/{courseId}
    @GetMapping("getCourseInfoOrder/{courseId}")
    public CourseOrderInfo getCourseInfoOrder(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseBaseInfo(courseId);
        CourseOrderInfo courseOrderInfo = new CourseOrderInfo();
        BeanUtils.copyProperties(courseInfoVo,courseOrderInfo);
        return courseOrderInfo;
    }

    //根据课程id 查询课程的详细信息 做前台课程详情显示
    @GetMapping("getCourseInfo/{courseid}")
    public Result getCourseINfo(@PathVariable String courseid){
        //根据课程id 查询课程基本信息
        CourseInfoVo courseInfoVo = courseService.getCourseBaseInfo(courseid);

        //根据课程id 查询课程的章节 和 小节
        List<ChapterVo> allChapterVideo = chapterService.getAllChapterVideo(courseid);

        return Result.ok().data("courseInfoVo",courseInfoVo)       //课程基本信息
                          .data("allChapterVideo",allChapterVideo);//章节和小节
    }

    //条件查询 带分页
    @PostMapping("getPageQueryCourse/{current}/{limit}")
    public Result getPageQueryCourse(
            @PathVariable long current,   //当前页
            @PathVariable long limit,     //每页记录数
            @RequestBody(required = false) CourseQueryVo courseQueryVo  //条件对象  //可以传可以不传
            ){
        String subjectParentId = courseQueryVo.getSubjectParentId(); //一级分类id
        String subjectId = courseQueryVo.getSubjectId();             //二级分类id
        String priceSort = courseQueryVo.getPriceSort();             //价格排序
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();     //最新 按时间排序
        String buyCountSort = courseQueryVo.getBuyCountSort();       //按销量排序

        Page<EduCourse> pageParam = new Page<>(current,limit);
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(subjectParentId)){
            wrapper.eq("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)){
            wrapper.eq("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(priceSort)){
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(gmtCreateSort)){
            wrapper.orderByDesc("price");
        }
        if (!StringUtils.isEmpty(buyCountSort)){
            wrapper.orderByDesc("gmt_create");
        }

        courseService.page(pageParam, wrapper);

        //把分页的所有数据 都返回
        List<EduCourse> records = pageParam.getRecords();

        long currentPage = pageParam.getCurrent(); //当前页
        long pages = pageParam.getPages();  //总页数
        long size = pageParam.getSize();    //每页显示记录数
        long total = pageParam.getTotal();  //总记录数
        boolean hasNext = pageParam.hasNext();  //是否有下一页
        boolean hasPrevious = pageParam.hasPrevious();  //是否有上一页

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
