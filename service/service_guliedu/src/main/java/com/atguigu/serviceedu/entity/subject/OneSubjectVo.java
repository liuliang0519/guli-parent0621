package com.atguigu.serviceedu.entity.subject;


import lombok.Data;

import java.util.List;

@Data
public class OneSubjectVo {

    private String id;
    private String title;
    private List<TwoSubjectVo> children;
}
