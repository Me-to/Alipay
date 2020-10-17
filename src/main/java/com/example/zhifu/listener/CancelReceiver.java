package com.example.zhifu.listener;


import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.zhifu.config.AlipayTemplate;
import com.example.zhifu.pay.model.Order;
import com.example.zhifu.pay.model.PaymentInfo;
import com.example.zhifu.pay.service.OrderService;
import com.example.zhifu.pay.service.PaymentInfoService;
import com.example.zhifu.vo.PayVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Queue;

/**
 * 取消订单消息的处理者
 * Created by macro on 2018/9/14.
 */
@Component

public class CancelReceiver {
    @Autowired
    OrderService orderService;
    @Autowired
    PaymentInfoService paymentInfoService;
    @Autowired
    AlipayTemplate alipayTemplate;
    private static Logger LOGGER = LoggerFactory.getLogger(CancelReceiver.class);

    @RabbitListener(queues = "qian.cancel.plugin")
    public void handle(String id, PayVo out_trade_no) {

        LOGGER.error("------这是回调信息");
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);

//        Order order = new Order();
//        order.setId(Integer.parseInt(id));
//        order.setPayType(0);
//        orderService.updateById(order);
        try {
            alipayTemplate.closePay(out_trade_no);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }
}
