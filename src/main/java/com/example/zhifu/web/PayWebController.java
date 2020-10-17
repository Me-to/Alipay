package com.example.zhifu.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.zhifu.config.AlipayTemplate;
import com.example.zhifu.listener.CancelSender;
import com.example.zhifu.pay.mapper.CommodityMapper;
import com.example.zhifu.pay.model.Order;
import com.example.zhifu.pay.model.PaymentInfo;
import com.example.zhifu.pay.service.CommodityService;
import com.example.zhifu.pay.service.OrderService;
import com.example.zhifu.pay.service.PaymentInfoService;
import com.example.zhifu.vo.PayAsyncVo;
import com.example.zhifu.vo.PayVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Controller
public class PayWebController {

    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    CommodityService commodityService;

    @Autowired
    PaymentInfoService paymentInfoService;

    @Autowired
    CancelSender cancelSender;

    @Autowired
    OrderService orderService;
    Order order;
    @ResponseBody
    @GetMapping(value = "/payOrder", produces = "text/html")
    public String payOrder(@RequestParam("commodityid") String commodityid) throws AlipayApiException {

//        // 商户订单号，商户网站订单系统中唯一订单号，必填
//        private String out_trade_no;
//        // 付款金额，必填
//        private String total_amount;
//        // 订单名称 必填
//        private String subject;
//        // 支付宝会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝。可选

        //填写订单信息和金额以及其他封装的必须内容
        PayVo payVo = commodityService.Configuration(commodityid);
        String pay = alipayTemplate.pay(payVo);
        String replace = UUID.randomUUID().toString().replace("", "");
        order.setPayType(1);
        order.setId(Integer.valueOf(replace));
        orderService.save(order);
        sendDelayMessageCancelOrder(replace,payVo);

//        sendDelayMessageCancelOrder();
        return pay;

    }
    private void sendDelayMessageCancelOrder(String id,PayVo payVo ) {
        //获取订单超时时间，假设为60分钟(测试用的30秒)
        long delayTimes = 30 * 1000;
        //发送延迟消息
        cancelSender.sendMessage(id,payVo, delayTimes);
    }


}
