package com.atguigu.orderservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.vo.CourseOrderInfo;
import com.atguigu.commonutils.vo.MemberOrderInfo;
import com.atguigu.orderservice.client.CourseClient;
import com.atguigu.orderservice.client.MemberClient;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.mapper.OrderMapper;
import com.atguigu.orderservice.service.OrderService;
import com.atguigu.orderservice.utils.OrderNoUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;


/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2020-11-10
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CourseClient courseClient;

    @Autowired
    private MemberClient memberClient;

    //根据订单号  获取订单详情  /orderservice/order/getOrderByOrderId/{orderId}
    @GetMapping("getOrderByOrderId/{orderId}")
    public Result getOrderByOrderId(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return Result.ok().data("orderInfo", order);
    }


    //根据课程id  生成订单详情  返回订单号  /orderservice/order/getOrderInfo/{courseId}
    @PostMapping("getOrderInfo/{courseId}")
    public Result getOrderInfo(@PathVariable String courseId, HttpServletRequest request){


        //远程调用课程接口  根据课程id查询订单信息中所需要的课程信息
        CourseOrderInfo courseOrderInfo = courseClient.getCourseInfoOrder(courseId);
        //远程调用用户接口  根据登录者的id 查询订单详情中所需要的用户信息
        String id = JwtUtils.getMemberIdByJwtToken(request);
        MemberOrderInfo memberOrderInfo = memberClient.getMemberOrder(id);

        //把订单数据添加数据库
        Order order = new Order();
        //order对象设置需要值
        //生成订单号
        String orderNo = OrderNoUtil.getOrderNo();
        order.setOrderNo(orderNo);
        //设置课程相关数据
        order.setCourseId(courseId);
        order.setCourseTitle(courseOrderInfo.getTitle());
        order.setCourseCover(courseOrderInfo.getCover());
        order.setTeacherName(courseOrderInfo.getTeacherName());
        order.setTotalFee(courseOrderInfo.getPrice());
        order.setMemberId(id);
        order.setMobile(memberOrderInfo.getMobile());
        order.setNickname(memberOrderInfo.getNickname());
        order.setStatus(0);//状态：0代表未支付
        order.setPayType(1); //1 微信支付

        orderService.save(order);
        System.out.println(order);
        //返回订单号
        return Result.ok().data("orderId",orderNo);
    }
}

