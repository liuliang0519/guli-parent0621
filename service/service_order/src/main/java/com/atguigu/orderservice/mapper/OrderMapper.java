package com.atguigu.orderservice.mapper;

import com.atguigu.orderservice.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 订单 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2020-11-10
 */
@Repository
public interface OrderMapper extends BaseMapper<Order> {

}
