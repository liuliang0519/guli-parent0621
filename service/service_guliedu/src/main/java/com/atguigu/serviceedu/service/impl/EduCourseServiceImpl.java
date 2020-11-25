package com.atguigu.serviceedu.service.impl;

import com.atguigu.eduoos.servicebase.config.exception.GuliException;
import com.atguigu.serviceedu.entity.EduChapter;
import com.atguigu.serviceedu.entity.EduCourse;
import com.atguigu.serviceedu.entity.EduCourseDescription;
import com.atguigu.serviceedu.entity.EduVideo;
import com.atguigu.serviceedu.entity.vo.CourseInfoVo;
import com.atguigu.serviceedu.entity.vo.CoursePublishVo;
import com.atguigu.serviceedu.mapper.EduCourseMapper;
import com.atguigu.serviceedu.service.EduChapterService;
import com.atguigu.serviceedu.service.EduCourseDescriptionService;
import com.atguigu.serviceedu.service.EduCourseService;
import com.atguigu.serviceedu.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {




    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    EduChapterService eduChapterService;
    @Autowired
    EduVideoService eduVideoService;

    @Override
    public com.atguigu.serviceedu.entity.frontVo.CourseInfoVo getCourseBaseInfo(String courseid) {

        return baseMapper.getCourseBaseInfo(courseid);
    }



    //根据课程id删除课程（同时删除 关联表数据）
    //删除课程时  需要删除课程本身，删除描述，删除章节，删除小节，删除小节里的视屏
    @Override
    public void removeCourse(String id) {
        //TODO 删除
        //删除小节  根据课程id删除小节
        QueryWrapper<EduVideo> w1 = new QueryWrapper<>();
        w1.eq("course_id",id);
        eduVideoService.remove(w1);

        //删除章节
        QueryWrapper<EduChapter> w2 = new QueryWrapper<>();
        w2.eq("course_id",id);
        eduChapterService.remove(w2);

        //删除描述
        eduCourseDescriptionService.removeById(id);
        //删除本身
        int i = baseMapper.deleteById(id);
        if (i <= 0){
            throw new GuliException(20001,"删除课程失败");
        }

    }

    //获取热门课程显示到主页
    @Cacheable(value = "course",key = "'eduCourses'")
    @Override
    public List<EduCourse> getHotCourse(){
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduCourses = baseMapper.selectList(wrapper);
        return eduCourses;
    }


//    @Autowired
//    private EduCourseMapper eduCourseMapper;



    //根据id查询课程信息 查俩张表 组合成CourseInfoVo类返回
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        //根据id获取课程的基本信息（不包括描述及）
        EduCourse eduCourse = baseMapper.selectById(courseId);

        //根据id获取课程描述
        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);

        //创建返回的vo对象
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        //属性copy
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        //赋值 课程描述类容
        courseInfoVo.setDescription(description.getDescription());

        return courseInfoVo;
    }

    //根据id 修改课程信息
    @Override
    public String updateCourseInfo(CourseInfoVo courseInfoVo) {

        //改俩张表
        //1 修改课程信息表EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update <= 0){ //如果失败了就没有修改描述表的必要

            throw new GuliException(20001,"修改失败！");
        }else {
            //2 修改课程描述表
            EduCourseDescription eduCourseDescription = new EduCourseDescription();
            eduCourseDescription.setId(courseInfoVo.getId());
            eduCourseDescription.setDescription(courseInfoVo.getDescription());
            eduCourseDescriptionService.updateById(eduCourseDescription);

        }


        return courseInfoVo.getId();
    }

    //根据id查询  显示最终发布课程的vo信息
    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
//        CoursePublishVo coursePublishVoById = eduCourseMapper.getCoursePublishVoById(id);
//        return coursePublishVoById;

        return baseMapper.getCoursePublishVoById(id);
    }




    //添加基本课程信息 到俩张表中
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        System.out.println("===============22222222222");

        //1 添加课程信息到课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int rows = baseMapper.insert(eduCourse);
        if (rows <= 0){
            System.out.println("===============1111111111111");
            throw new GuliException(20001,"添加课程失败！");

        }

        //2 添加描述信息到课程描述表


        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //设置主键1对1关联
        eduCourseDescription.setId(eduCourse.getId());

        eduCourseDescription//设置描述
                .setDescription(courseInfoVo.getDescription());

        eduCourseDescriptionService.save(eduCourseDescription);

        //返回课程的id
        return eduCourse.getId();

    }



}
