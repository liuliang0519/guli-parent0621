package com.atguigu.ucenterservice.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.eduoos.servicebase.config.exception.GuliException;
import com.atguigu.ucenterservice.entity.Member;
import com.atguigu.ucenterservice.entity.vo.LoginVo;
import com.atguigu.ucenterservice.entity.vo.RegisterVo;
import com.atguigu.ucenterservice.mapper.MemberMapper;
import com.atguigu.ucenterservice.service.MemberService;
import com.atguigu.ucenterservice.utils.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-11-07
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {


    @Autowired
    RedisTemplate<String,String> redisTemplate;

    //登录之后 返回token字符串
    @Override
    public String login(LoginVo loginVo) {
        String phone = loginVo.getPhone();
        String password = loginVo.getPassword();

        //1 判断手机号和密码是否为空
       if (StringUtils.isEmpty(password) || StringUtils.isEmpty(phone)){
           throw new GuliException(20001,"手机号或者密码为空");
       }

        //2 根据手机号 查询用户信息
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",phone);
        Member member = baseMapper.selectOne(wrapper);
        if (member == null){
            throw new GuliException(20001,"手机号为空");
        }

        //3 比较密码是否一样
        if (!member.getPassword().equals(MD5.encrypt(password))){//输入的密码加密  在和数据库比较
            throw new GuliException(20001,"密码错误");
        }

        //4 查看用户是否禁用
        if (member.getIsDisabled()){ //值是true表示禁用
            throw new GuliException(20001,"用户被封号！");
        }


        //5 根据用户信息  使用jwt生成 token字符串返回
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());

        return jwtToken;
    }


    //用户注册
    @Override
    public Integer register(RegisterVo registerVo) {

        Integer num = 0;
        String nikename =  registerVo.getNickname();
        String phone = registerVo.getPhone();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //判断注册信息是否为空
       if (
           StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)
           || StringUtils.isEmpty(nikename) || StringUtils.isEmpty(code)
       ){
           num = 1;
           throw new GuliException(20001,"注册信息为空");
       }

        //判断验证码是否相同
        String codeRedis = redisTemplate.opsForValue().get(phone);
       if (!code.equals(codeRedis)){
           num = 1;
           throw new GuliException(20001,"验证码错误");

       }

        //判断手机号是否已经注册过(在数据库中是否存在)
        QueryWrapper<Member> w = new QueryWrapper<>();
        w.eq("mobile",phone);
        Integer count = baseMapper.selectCount(w);
        if (count > 0){
            num = 1;
            throw new GuliException(20001,"手机号已被注册");

        }

        //吧注册用户的信息 添加到数据库
        Member member = new Member();
        member.setNickname(nikename);
        //密码加密
        member.setPassword(MD5.encrypt(password));
        member.setMobile(phone);
        member.setIsDisabled(false);
        //设置默认头像
        member.setAvatar("https://java0621-teacher.oss-cn-beijing.aliyuncs.com/2020/10/30/83ff28741f28482486da6b3fe7bd1645周慧敏.jpeg");
        int insert = baseMapper.insert(member);
        if (insert == 0){
            num = 1;
            throw new GuliException(20001,"注册失败");

        }

        System.out.println("===========  :"+num);
        System.out.println("===========  :"+num);
        System.out.println("===========  :"+num);
        return num;
    }


    //根据openId查询  用户信息
    @Override
    public Member selectWxUserInfo(String openid) {
        QueryWrapper<Member> w = new QueryWrapper<>();
        w.eq("openid",openid);
        Member member = baseMapper.selectOne(w);
        return member;
    }


}
