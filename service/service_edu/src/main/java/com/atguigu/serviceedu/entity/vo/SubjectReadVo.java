package com.atguigu.serviceedu.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class SubjectReadVo {


    //第一列
    @ExcelProperty(index = 0)
    private String oneLevelSubject;

    //第二列
    @ExcelProperty(index = 1)
    private String twoLevelSubject;
}
