package com.atguigu.ucenterservice;

import com.atguigu.ucenterservice.utils.MD5;
import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.atguigu"})
@SpringBootApplication
@MapperScan("com.atguigu.ucenterservice.mapper")
@EnableDiscoveryClient
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }

    @Test
    public void test(){
        String encrypt = MD5.encrypt("liuliang");
        System.out.println(encrypt);
    }
}
