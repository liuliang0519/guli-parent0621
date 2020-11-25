package com.atguigu.edumsm.service;

import java.util.Map;

public interface MsmService {
    /**
     * 发送短信
     */
    public boolean send(String PhoneNumbers, String templateCode, Map<String,Object> param);
}
