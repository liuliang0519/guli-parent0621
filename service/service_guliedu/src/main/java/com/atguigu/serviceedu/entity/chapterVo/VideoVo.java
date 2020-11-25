package com.atguigu.serviceedu.entity.chapterVo;

import lombok.Data;

//小节
@Data
public class VideoVo {

    private String id;
    private String title;
    private Boolean isFree; //是否免费
    private String videoSourceId;//视频id

}
