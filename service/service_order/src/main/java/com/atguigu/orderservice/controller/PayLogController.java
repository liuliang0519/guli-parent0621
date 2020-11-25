package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.Result;
import com.atguigu.eduoos.servicebase.config.exception.GuliException;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.service.OrderService;
import com.atguigu.orderservice.service.PayLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-11-10
 */
@RestController
@RequestMapping("/orderservice/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @Autowired
    private OrderService orderService;

    //根据订单编号  查询订单支付状态  /orderservice/paylog/queryPayStatus/{orderNo}
    @GetMapping("queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo) {
        System.out.println("========检查支付状态");
        //1 调用微信接口，根据订单号查询支付状态
        Map<String, String> map = payLogService.queryStatusPay(orderNo);

        //2 如果查询微信接口返回map集合为空，表示失败
        if(map == null) {
            System.out.println("========支付出错");
            return Result.error().message("支付出错");
        }
        //3 支付成功，更新订单状态，添加支付记录
        if(map.get("trade_state").equals("SUCCESS")) {
            System.out.println("========支付成功");
            //支付成功后 更新订单状态，添加支付记录
            payLogService.updateOrderStates(map);
            return Result.ok().message("支付成功");
        }

        //支付中
        System.out.println("========正在支付中");
        return Result.ok().code(25000).message("支付中");
    }

    //生成微信支付二维码 根据订单编号  /orderservice/paylog/createNative/{orderId}
    @GetMapping("createNative/{orderId}")
    public Result createNative(@PathVariable String orderId){
        System.out.println("========生成了二维码");
        Map map = payLogService.createNative(orderId);
        return Result.ok().data(map);
    }


    //根据用户id和课程id 查询用户是否购买该课程 查询订单表支付状态是否 等于1
    //  /orderservice/paylog/getStatus/{courseId}
    @GetMapping("getStatus/{courseId}")
    public Result getStatus(@PathVariable String courseId, HttpServletRequest request){

            String memberId = JwtUtils.getMemberIdByJwtToken(request);
//          String memberId = "1325990761207988225";
            //已登录 通过head中的token获取 用户id
            //根据用户id 和 课程id 查询订单表中的status的字段
            QueryWrapper<Order> w = new QueryWrapper<>();
            w.eq("member_id",memberId);
            w.eq("course_id",courseId);

            Order order = orderService.getOne(w);
            if (order ==null){
                //未支付
                System.out.println("未支付1");
                return Result.ok().data("flag",false);
            }else {
                Integer status = order.getStatus();
                if (status.intValue() == 1){
                    //已支付
                    System.out.println("已支付");
                    return Result.ok().data("flag",true);
                }else {
                    //未支付
                    System.out.println("未支付2");
                    return Result.ok().data("flag",false);

                }

            }



    }
}

