package com.atguigu.serviceedu.controller;

import com.atguigu.commonutils.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/serviceedu/user")
@CrossOrigin //允许跨域访问
public class EduLoginController {

    @GetMapping("/info")
    public Result info(){
        return Result.ok().data("roles","[admin]")
                .data("name","admin")
                .data("avatar","");


    }


    @PostMapping("/login")
    public Result login(){
        return Result.ok().data("token","admin");
    }


}
