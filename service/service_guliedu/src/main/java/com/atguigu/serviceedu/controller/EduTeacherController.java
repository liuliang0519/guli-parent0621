package com.atguigu.serviceedu.controller;


import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.query.TeacherQuery;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.atguigu.serviceedu.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.atguigu.commonutils.Result;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-10-20
 */

@Api(value = "操作讲师接口")
@RestController
@RequestMapping("/serviceedu/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    EduTeacherService eduTeacherService;

    @Autowired
    TeacherService teacherService;



    //修改讲师(先查再改)
    @ApiOperation("修改讲师")
    @PostMapping("/updateEduTeacher")
    public Result updateEduTeacherById(@RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.updateById(teacher);
        if (flag){
            return Result.ok();
        }else {
            return Result.error();
        }

    }



    //根据id查询讲师
    @ApiOperation("根据id查询讲师")
    @GetMapping("/getEduTeacherById/{id}")
    public Result getEduTeacherById(@PathVariable String  id){
        EduTeacher teacher = eduTeacherService.getById(id);

//        try {
//            int i = 5/0;
//        } catch (Exception e) {
//           throw new GuliException(2432342,"出现了自定义的异常啦啦啦啦");
//        }
        if (teacher != null){
            return Result.ok().data("teacher",teacher);
        }else {
            return Result.error();
        }
    }


    //新增讲师
    @ApiOperation(value = "新增单个讲师")
    @PostMapping("/saveOne")
    public Result addEduTeacher(@RequestBody EduTeacher teacher){
        boolean flag = eduTeacherService.save(teacher);

        if (flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }





    //条件分页查询
    @PostMapping("/pageListByCondition/{page}/{limit}")
    public Result pageListByCondition(
             @PathVariable Long page,
             @PathVariable Long limit,
             @RequestBody TeacherQuery teacherQuery
    ){
        //分页
        Page<EduTeacher> pageParam = new Page<>(page, limit);

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();


        //判断条件值是否为空
        if (!StringUtils.isEmpty(name)){
            //拼接条件
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            //拼接条件
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            //拼接条件
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            //拼接条件
            wrapper.le("gmt_create",end);
        }

        //条件判断
        IPage<EduTeacher> page1 = eduTeacherService.page(pageParam, wrapper);

        List<EduTeacher> records = page1.getRecords();
        long total = page1.getTotal();
        return  Result.ok().data("total", total).data("rows", records);

    }



    //无条件分页查询
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{page}/{limit}")
    public Result pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){

        Page<EduTeacher> pageParam = new Page<>(page, limit);

        eduTeacherService.page(pageParam, null);

        List<EduTeacher> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return  Result.ok().data("total", total).data("rows", records);
    }


//    //无条件分页查询
//    @ApiOperation(value = "无条件分页讲师列表")
//    @GetMapping("/pageTeacher/{current}/{limit}")
//    public Result pageTeacher(@PathVariable Long current,@PathVariable Long limit){
//        Page<EduTeacher> pageParam = new Page<>(current,limit);
//        eduTeacherService.page(pageParam, null);
//
//        long total = pageParam.getTotal();
//        List<EduTeacher> records = pageParam.getRecords();
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//
//
//        return Result.ok().data(map);
//
//    }

    //    //条件分页查询
//    @ApiOperation(value = "分页带条件查询 讲师列表")
//    @PostMapping("pageListByCondition/{page}/{limit}")
//    public Result pageListByCondition(
//                                        @PathVariable Long page,
//                                        @PathVariable Long limit,
//                                        @RequestBody TeacherQuery teacherQuery){
//
//        Page<EduTeacher> pageParam = new Page<>(page, limit);
//        teacherService.pageQuery(pageParam,teacherQuery);
//        List<EduTeacher> records = pageParam.getRecords();
//        long total = pageParam.getTotal();
//
//
//        return  Result.ok().data("total", total).data("rows", records);
//    }


    //2 根据id   逻辑删除
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("/deleteById/{id}")
    public Result removeById(@PathVariable String id){

//        boolean flag = eduTeacherService.removeById(id);
//        if (flag){
//            return Result.ok();
//        }else {
//            return Result.error();
//        }

        //优化上面的
        boolean flag = eduTeacherService.deleteTeacherById(id);
        if (flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }


    //1  查看讲师列表
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/getAll")
    public Result getAll(){

        List<EduTeacher> list = eduTeacherService.list(null);

        return  Result.ok().data("list", list);
    }








}

