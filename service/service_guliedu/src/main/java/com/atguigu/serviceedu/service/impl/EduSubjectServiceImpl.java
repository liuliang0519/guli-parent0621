package com.atguigu.serviceedu.service.impl;

import com.atguigu.serviceedu.entity.EduSubject;
import com.atguigu.serviceedu.entity.subject.OneSubjectVo;
import com.atguigu.serviceedu.entity.subject.TwoSubjectVo;
import com.atguigu.serviceedu.mapper.EduSubjectMapper;
import com.atguigu.serviceedu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-27
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {



    //课程分类列表
    @Override
    public List<OneSubjectVo> getAllSubjectList() {

        //需要返回的  封装最终数据
        ArrayList<OneSubjectVo> finalList = new ArrayList<>();

        //1  查询所有的一级分类 parent_id = 0
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id","0");
        List<EduSubject> list1 = baseMapper.selectList(wrapper1);

        //2  查询2级所有分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper1.ne("parent_id","0");
        List<EduSubject> list2 = baseMapper.selectList(wrapper2);



        //3  封装一级分类
        for (int i= 0;i < list1.size();i++){
            //得到所有的 一级分类 进行封装
            EduSubject eduSubject1 = list1.get(i);
            //把每个 eduSubject 对象转换成 OneSubjectVo 对象
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
            BeanUtils.copyProperties(eduSubject1,oneSubjectVo);

            //把转换成功的vo对象  放到final集合中
            finalList.add(oneSubjectVo);


            // 用于封装一级里面所有二级分类
            ArrayList<TwoSubjectVo> twoList = new ArrayList<>();

            //封装二级分类
            //遍历所有的二级分类
            for (int m = 0; m < list2.size();m++){
                //得到所有的二级分类
                EduSubject eduSubject2 = list2.get(m);
                //判断获取到的二级分类  是不是 属于当前的一级分类
                if (eduSubject1.getId().equals(eduSubject2.getParentId())){
                    //把符合条件的二级分类 放到list集合里
                    //把每个 eduSubject2 对象转换成 TwoSubjectVo 对象
                    TwoSubjectVo TwoSubjectVo2 = new TwoSubjectVo();
                    BeanUtils.copyProperties(eduSubject2,TwoSubjectVo2);
                    //eduSubject2 放到  twoList集合里
                    twoList.add(TwoSubjectVo2);
                }

            }

            //把封装好的二级分类的集合  放到 一级分类中去
            oneSubjectVo.setChildren(twoList);

        }
//


        return finalList;
    }
}
