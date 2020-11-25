package com.atguigu.serviceedu.controller;


import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.Result;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.subject.OneSubjectVo;
import com.atguigu.serviceedu.entity.vo.SubjectReadVo;
import com.atguigu.serviceedu.handler.ExcelListener;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目
 * </p>
 *
 * @author atguigu
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/serviceedu/subject")
@CrossOrigin //允许跨域访问
public class SelectEduSubjectController {

    @Autowired
    EduSubjectService eduSubjectService;

     //递归查询分类
     @GetMapping("/getAllSubjectList")
     public Result getAllSubjectList(){

         //查询所有的分类
         List<EduSubject> listAll = eduSubjectService.list(null); //所有分类
         //传入所有分类  得到树形结构的层级分类
         List<EduSubject> result = this.build(listAll);
         return Result.ok().data("result",result);
     }



     //递归删除 指定分类和其子分类
    @DeleteMapping("/deleteSubject/{id}")
     public Result deleteSubject(@PathVariable String id){
        //递归删除分类
        this.getAllChildId(id);
        return Result.ok();
     }


     //传入一个分类id  返回包括当前id的所有子id集合
    private void getAllChildId(String id) {
        //用来封装删除分类的全部id的集合
        List<String> idsList = new ArrayList<>();

        //传入一个分类id  返回包括当前id的所有子id集合
        this.selectChildById(id,idsList);
        idsList.add(id);
        //删除分类和其子分类
        eduSubjectService.removeByIds(idsList);

    }

    //递归删除核心代码
    private void selectChildById(String subjectId, List<String> idsList) {

         QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
         wrapper.eq("parent_id",subjectId).select("id");

         List<EduSubject> list = eduSubjectService.list(wrapper);
         list.stream().forEach( node ->{
           //通过遍历node得到子分类id
            idsList.add(node.getId());
            this.selectChildById(node.getId(),idsList);
         });
    }


    //传入所有分类  通过递归得到分类树！
    private List<EduSubject> build(List<EduSubject> listAll) {
        List<EduSubject> result = new ArrayList<>();
        for (EduSubject eduSubject : listAll) {
            if ("0".equals(eduSubject.getParentId())){
                //得到一级分类
                eduSubject.setLevel(1);
                //设置二级分类
                eduSubject.setChildren(this.getChildren(eduSubject,listAll));
                //存入返回集合
                result.add(eduSubject);
            }
        }
        return result;
    }

    // 查询n级下的 n+1级
    private List<EduSubject> getChildren(EduSubject node,List<EduSubject> allSubject) {
        List<EduSubject> result = new ArrayList<>(); //返回的2级分类集合
        //遍历所有分类  所有分类的parentId == 一级分类Id的 都是2级分类
        for (EduSubject eduSubject : allSubject) {
            if(node.getId().equals(eduSubject.getParentId())){
                //得到传入分类的下级分类
                Integer level = node.getLevel();//传入分类的级别
                eduSubject.setLevel(level + 1); //子分类级别是传入分类级别+1
                //设置子分类的子分类
                eduSubject.setChildren(this.getChildren(eduSubject,allSubject));//自己调自己 形成递归
                //存入返回的集合
                result.add(eduSubject);
            }
        }
        return result;

    }

}

