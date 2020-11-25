package com.atguigu.ucenterservice.service;

import com.atguigu.ucenterservice.entity.Member;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-11-07
 */
public interface MemberService extends IService<Member> {

    String login(LoginVo loginVo);

    Integer register(RegisterVo registerVo);


    Member selectWxUserInfo(String openid);
}
