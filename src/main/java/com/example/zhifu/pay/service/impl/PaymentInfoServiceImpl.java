package com.example.zhifu.pay.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.zhifu.pay.model.PaymentInfo;
import com.example.zhifu.pay.mapper.PaymentInfoMapper;
import com.example.zhifu.pay.service.PaymentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhifu.vo.PayAsyncVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付对账表 服务实现类
 * </p>
 *
 * @author zhangqian
 * @since 2020-10-12
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {

    @Override
    public String handlePayResult(PayAsyncVo vo) {
        int status = 0;

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOutTradeNo(vo.getOut_trade_no());
        paymentInfo.setPaymentType(1);
        paymentInfo.setTradeNo(vo.getTrade_no());
        paymentInfo.setTotalAmount(new BigDecimal(vo.getBuyer_pay_amount()));
        paymentInfo.setSubject(vo.getSubject());
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setCallbackTime(vo.getNotify_time());
        paymentInfo.setCallbackContent("");
        paymentInfo.setEffective(1);

        if (vo.getTrade_status().equals("TRADE_SUCCESS") || vo.getTrade_status().equals("TRADE_FINISHED")) {
            status = 1;
            paymentInfo.setPaymentStatus(status);
            this.save(paymentInfo);
            return "success";
        }else {
            paymentInfo.setPaymentStatus(status);
            this.save(paymentInfo);
            return "fail";
        }

    }
}
