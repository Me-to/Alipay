package com.example.zhifu.pay.service.impl;

import com.example.zhifu.pay.model.Order;
import com.example.zhifu.pay.mapper.OrderMapper;
import com.example.zhifu.pay.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangqian
 * @since 2020-10-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
