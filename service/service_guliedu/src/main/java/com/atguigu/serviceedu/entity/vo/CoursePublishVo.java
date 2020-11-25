package com.atguigu.serviceedu.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;       //课程名称
//    private String cover;       //课程封面
    private Integer lessonNum;     //总课时
    private String description;   //课程描述
    private String subjectLevelOne;   //所属一级分类
    private String subjectLevelTwo;   //所属二级分类
    private String teacherName;       //讲师姓名
    private String price;             //只用于课程价格
}
