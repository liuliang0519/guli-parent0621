package com.atguigu.serviceedu.mapper;

import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.frontVo.CourseInfoVo;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2020-10-29
 */
@Repository
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据id查询特定课程vo信息  发布课程页面展示
    public CoursePublishVo getCoursePublishVoById(@Param("courseId") String id);

    //根据课程id  查询课程的基本信息（前台课程列表显示）
    CourseInfoVo getCourseBaseInfo(String courseid);
}
