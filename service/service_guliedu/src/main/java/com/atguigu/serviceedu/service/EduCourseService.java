package com.atguigu.serviceedu.service;

import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-29
 */
public interface EduCourseService extends IService<EduCourse> {


    //添加课程信息到俩张表中
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据id查询课程信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    String updateCourseInfo(CourseInfoVo courseInfoVo);

    //显示课程发布信息
    CoursePublishVo getCoursePublishVoById(String id);


    //根据课程id删除课程（同时删除 关联表数据）
    //删除课程时  需要删除课程本身，删除描述，删除章节，删除小节，删除小节里的视屏
    void removeCourse(String id);

    List<EduCourse> getHotCourse();

    com.atguigu.serviceedu.entity.frontVo.CourseInfoVo getCourseBaseInfo(String courseid);
}
