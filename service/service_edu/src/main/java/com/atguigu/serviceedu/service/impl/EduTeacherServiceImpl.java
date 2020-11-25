package com.atguigu.serviceedu.service.impl;

import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.mapper.EduTeacherMapper;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-10-20
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Autowired
    EduTeacherMapper eduTeacherMapper;


    //删除讲师
    @Override
    public boolean deleteTeacherById(String id) {
        Integer num = eduTeacherMapper.deleteById(id);

        return num > 0;
    }
}
