package com.example.zhifu.web;

import com.alipay.api.AlipayApiException;
import com.example.zhifu.config.AlipayTemplate;
import com.example.zhifu.listener.CancelSender;
import com.example.zhifu.pay.service.CommodityService;
import com.example.zhifu.pay.service.PaymentInfoService;
import com.example.zhifu.vo.PayVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        paymentInfoService.oderPay(payVo);
        sendDelayMessageCancelOrder(payVo.getOut_trade_no());
        return pay;

    }
    private void sendDelayMessageCancelOrder(String out_trade_no ) {
        //获取订单超时时间，假设为60分钟(测试用的30秒)
        long delayTimes = 60 * 1000;
        //发送延迟消息
        cancelSender.sendMessage(out_trade_no, delayTimes);
    }


}
