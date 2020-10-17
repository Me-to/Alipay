package com.example.zhifu.listener;


import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.zhifu.config.AlipayTemplate;
import com.example.zhifu.pay.model.PaymentInfo;
import com.example.zhifu.pay.service.PaymentInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的处理者
 * Created by macro on 2018/9/14.
 */
@Component

public class CancelReceiver {
    @Autowired
    PaymentInfoService paymentInfoService;
    @Autowired
    AlipayTemplate alipayTemplate;
    private static Logger LOGGER = LoggerFactory.getLogger(CancelReceiver.class);

    @RabbitListener(queues = "qian.cancel.plugin")
    public void handle(String out_trade_no) {

        LOGGER.info("handle--这是回调信息");
        QueryWrapper<PaymentInfo> queryWrapper=new QueryWrapper<PaymentInfo>().eq("out_trade_no",out_trade_no);
        PaymentInfo pay = paymentInfoService.getOne(queryWrapper);

        if (pay.getPaymentStatus()==0){
            try {
                alipayTemplate.closePay(out_trade_no);
                pay.setEffective(0);
                paymentInfoService.updateById(pay);
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }


    }
}
