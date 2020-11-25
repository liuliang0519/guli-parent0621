package com.atguigu.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.commonutils.Result;
import com.atguigu.eduoos.servicebase.config.exception.GuliException;
import com.atguigu.orderservice.entity.Order;
import com.atguigu.orderservice.entity.PayLog;
import com.atguigu.orderservice.mapper.PayLogMapper;
import com.atguigu.orderservice.service.OrderService;
import com.atguigu.orderservice.service.PayLogService;
import com.atguigu.orderservice.utils.HttpClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author atguigu
 * @since 2020-11-10
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    OrderService orderService;





    //根据订单编号 生成微信支付二维码
    @Override
    public Map createNative(String orderId) {
        //1 根据订单号  查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);

        //2 调用微信支付接口 生成二维码
        //二维码接口  需要传递很多参数
        Map m = new HashMap();
        //1、设置支付参数
        m.put("appid", "wx74862e0dfcf69954"); //支付id
        m.put("mch_id", "1558950191");        //商户号
        m.put("nonce_str",  WXPayUtil.generateNonceStr()); //随机数
        m.put("body", order.getCourseTitle());   //课程名称
        m.put("out_trade_no", orderId);          //订单号
        //订单金额
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");

        m.put("spbill_create_ip", "127.0.0.1");          //客户端ip
        m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
        m.put("trade_type", "NATIVE");       //二维码类型：固定金额的二维码


        //调用微信生成二维码接口，设置xml类型参数
        HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        httpClient.setHttps(true);

        Map<String, String> resultMap = new HashMap<>();

        //用于封装返回的结果集
        Map map = new HashMap<>();
        try {
            //把上面参数map集合变成xml，根据商户key进行加密
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            httpClient.post(); //发送post请求

            //请求得到  返回的第三方的数据
            String xml = httpClient.getContent();
            //把返回的xml数据 转换成map数据
            resultMap = WXPayUtil.xmlToMap(xml);

//            System.out.println("xml= " + xml);
//            System.out.println("===============");
//            System.out.println("resultMap= " + resultMap);
            map.put("out_trade_no", orderId);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());

            map.put("result_code", resultMap.get("result_code"));  //二维码生成状态 成功SUCCESS
            map.put("code_url", resultMap.get("code_url"));        //二维码显示地址


        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"生成二维码失败！");
        }
        return map;
    }


    // 调用微信接口，根据订单号查询支付状态
    @Override
    public Map<String, String> queryStatusPay(String orderNo) {
        try {

            //1 设置查询状态接口需要参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 使用httpclient调用微信接口，传递参数，需要转换xml，进行加密
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            httpClient.post();

            //3 得到微信接口返回数据
            String xml = httpClient.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(xml);


            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"查询状态失败");
        }
    }


    //支付成功后 更新订单状态，添加支付记录
    @Override
    public void updateOrderStates(Map<String, String> map) {
        //订单号
        String orderNo = map.get("out_trade_no");

        //1 根据订单表订单状态status=1
        //根据订单号查询订单对象
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        //判断如果订单状态已经是1，直接返回
        if(order.getStatus().intValue()==1) {
            return;
        }
        order.setStatus(1); //讲订单表的支付状态 设置为（1）表示已支付
        //进行更新
        orderService.updateById(order);

        //2 向订单记录表pay_log添加记录
        PayLog payLog = new PayLog();

        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);



    }
}
