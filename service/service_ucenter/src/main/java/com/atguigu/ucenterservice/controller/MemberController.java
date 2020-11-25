package com.atguigu.ucenterservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.vo.MemberOrderInfo;
import com.atguigu.ucenterservice.entity.Member;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-11-07
 */
@RestController
@RequestMapping("/ucenterservice/member")
@CrossOrigin
public class MemberController {


    @Autowired
    MemberService memberService;

    //根据用户id获取用户信息  /ucenterservice/member
    @GetMapping("getMemberOrder/{id}")
    public MemberOrderInfo getMemberOrder(@PathVariable String id){
        Member member = memberService.getById(id);
        MemberOrderInfo memberOrderInfo = new MemberOrderInfo();
        BeanUtils.copyProperties(member,memberOrderInfo);
        return memberOrderInfo;
    }


    //登录
    @PostMapping("login")
    public Result loginUser(@RequestBody LoginVo loginVo){
        //登录成功之后返回token字符串
        String token = memberService.login(loginVo);
        return Result.ok().data("token",token);
    };


    //注册 /ucenterservice/member/register
    @PostMapping("register")
    public Result register(@RequestBody RegisterVo registerVo){
        Integer num = memberService.register(registerVo);

        //注册用户
        if (num == 1){
            return Result.error();
        }else {
            return Result.ok();
        }
    }

    //根据token 获取用户信息
    //token放到请求头中 header
    @GetMapping("getLoginInfo")
    public Result getLoginInfo(HttpServletRequest request){
        //调用jwt方法 根据token获取id值
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //调用用户id 查询客户信息
        Member member = memberService.getById(memberId);

        return Result.ok().data("member",member);

    }
}

