package com.atguigu.serviceedu.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.vo.SubjectReadVo;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Repository
public class ExcelListener extends AnalysisEventListener<SubjectReadVo> {



    @Autowired
    EduSubjectService eduSubjectService;


    //一行一行的读取excel内容 封装到SubjectReadVo对象里面
    @Override
    public void invoke(SubjectReadVo subjectReadVo, AnalysisContext analysisContext) {

        //添加一级分类
        //1.1 subjectReadVo获取每一个一级分类的名称
        String oneLevelSubject = subjectReadVo.getOneLevelSubject();

        //1.1.1 判断一级分类是否重复
        EduSubject eduSubjectExist = this.existOneSubject(oneLevelSubject);
        if(eduSubjectExist == null){ //等于空 说明数据库中没有 就添加
            //1.2 调用service方法 添加一级分类到数据中
            eduSubjectExist = new EduSubject();
            eduSubjectExist.setTitle(oneLevelSubject);
            eduSubjectExist.setParentId("0");
            eduSubjectExist.setSort(0);
            //保存到数据库
            eduSubjectService.save(eduSubjectExist);

        }
        //获取添加完成之后的一级分类id
        String pid = eduSubjectExist.getId();



        //添加二级分类，有层级关系
        //2.1 subjectReadVo获取每一个二级分类的名称
        String twoLevelSubject = subjectReadVo.getTwoLevelSubject();


        //判断二级分类是否重复
        EduSubject eduSubjectExist2 = this.existTwoSubject(twoLevelSubject,pid);

        if (eduSubjectExist2 == null){

            //2.2 调用service方法 添加二级分类到数据中
            eduSubjectExist2 = new EduSubject();
            eduSubjectExist2.setTitle(twoLevelSubject);
            //上面添加之后一级分类id值
            eduSubjectExist2.setParentId(pid);

            eduSubjectExist2.setSort(0);
            eduSubjectService.save(eduSubjectExist2);

        }



    }

    //判断一级分类是否重复
    private EduSubject existOneSubject(String oneName){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",oneName);
        wrapper.eq("parent_id",0);
        return eduSubjectService.getOne(wrapper);

    }

    //判断二级分类是否重复
    private EduSubject existTwoSubject(String twoName,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",twoName);
        wrapper.eq("parent_id",pid);
        return eduSubjectService.getOne(wrapper);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    }


}
