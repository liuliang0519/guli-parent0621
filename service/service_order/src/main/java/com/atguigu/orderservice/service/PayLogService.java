package com.atguigu.orderservice.service;

import com.atguigu.orderservice.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author atguigu
 * @since 2020-11-10
 */
public interface PayLogService extends IService<PayLog> {

    //根据订单编号  生成微信支付二维码
    Map createNative(String orderId);


    //1 调用微信接口，根据订单号查询支付状态
    Map<String, String> queryStatusPay(String orderNo);

    //支付成功后 更新订单状态，添加支付记录
    void updateOrderStates(Map<String, String> map);
}
