package com.atguigu.edumsm.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.edumsm.service.MsmService;
import com.atguigu.edumsm.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    //填写手机号  发送验证码  /edumsm/msm/send/{phone}
    @GetMapping(value = "/send/{phone}")
    public Result code(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        System.out.println("验证码：  " + code);
        if(!StringUtils.isEmpty(code)) return Result.ok();

        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        System.out.println("验证码：  " + code);
        boolean isSend = msmService.send(phone, "SMS_205441195", param);
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code,20, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("发送短信失败");
        }
    }

}
