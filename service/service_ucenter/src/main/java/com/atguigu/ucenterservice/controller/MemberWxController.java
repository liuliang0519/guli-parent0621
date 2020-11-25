package com.atguigu.ucenterservice.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.eduoos.servicebase.config.exception.GuliException;
import com.atguigu.ucenterservice.entity.Member;
import com.atguigu.ucenterservice.service.MemberService;
import com.atguigu.ucenterservice.utils.ConstantPropertiesUtil;
import com.atguigu.ucenterservice.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class MemberWxController {


    @Autowired
    private MemberService memberService;

    //生成微信二维码
    //请求微信接口得到二维码
    //  /api/ucenter/wx/login
    @GetMapping("login")
    public String login() {
        //第一种写法：拼接字符串
//        String url = " https://open.weixin.qq.com/connect/qrconnect" +
//                "?appid="+ ConstantPropertiesUtil.WX_OPEN_APP_ID+
//                "&redirect_uri="+ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL+
//                "&response_type=code&scope=SCOPE&state=STATE";
//        return "redirect:"+url;
        //第二种写法：
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //把返回地址进行url编码
        String wxOpenRedirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            wxOpenRedirectUrl = URLEncoder.encode(wxOpenRedirectUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                wxOpenRedirectUrl,
                "atguigu"
        );
        return "redirect:"+url;   //重定向到二维码地址
    }


    //获取扫描人的信息
    @GetMapping("callback")
    public String callback(String code, String state, HttpSession session) {
        //临时票据，微信返回的，用于后面获取扫描人信息使用
        System.out.println("code:"+code);
        System.out.println("state:"+state);
        //1 获取扫描人信息（昵称或者头像等）
        //2 把信息添加到数据库
        try {
            // 拿着临时票据code请求微信接口，得到access_token
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            baseAccessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantPropertiesUtil.WX_OPEN_APP_ID,
                    ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                    code
            );
            //使用httpClient请求这个地址，得到access_token
            String result = HttpClientUtils.get(baseAccessTokenUrl);

            //把返回result字符串转换map集合，从map集合获取值
            Gson gson = new Gson();
            HashMap resultMap = gson.fromJson(result, HashMap.class);
            //从map集合获取值
            String access_token = (String)resultMap.get("access_token");
            String openid = (String)resultMap.get("openid");

            //判断微信信息在数据库是否重复，根据openid判断（查询微信的opendi在数据库中是否存在  ）
            //存在就 获取数据库中的member 返回（不注册）
            //不存在  就自动注册（存在就不注册）
            Member member = memberService.selectWxUserInfo(openid);
            if(member == null) { //没有重复的
                //拿着access_token和openid再去请求微信的另外接口，得到扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                baseUserInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //httpclient请求这个地址
                String userInfoResult = HttpClientUtils.get(baseUserInfoUrl);

                //从userInfoResult字符串获取微信扫描人信息
                HashMap userInfoMap = gson.fromJson(userInfoResult, HashMap.class);

                String nickname = (String)userInfoMap.get("nickname");  //微信昵称

                //过滤昵称包含特殊字符和表情
                Pattern patter = Pattern.compile("[a-zA-Z0-9\u4e00-\u9fa5]");
                Matcher match = patter.matcher(nickname);
                StringBuffer buffer = new StringBuffer();
                while (match.find()) {
                    buffer.append(match.group());
                }
                //得到去掉特殊表情的 昵称nickname
                nickname = buffer.toString();

                String headimgurl = (String)userInfoMap.get("headimgurl"); //微信头像
                //把获取微信扫描人信息添加数据库
                member = new Member();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);

                //存到数据库
                memberService.save(member);

            }

            //实现前端页面显示（登录），返回token
            String token = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            //跳转到首页面
            return "redirect:http://localhost:3000?token="+token;

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"扫描失败");
        }
    }
}

