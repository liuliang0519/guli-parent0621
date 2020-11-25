package com.atguigu.eduvod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVideoByVideoId(String id);
}
