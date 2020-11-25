package com.atguigu.serviceedu.entity.chapterVo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//章节
@Data
public class ChapterVo {

    private String id;

    private String title;

    //一个章节有很多小节
    private List<VideoVo> children = new ArrayList<>();

}
