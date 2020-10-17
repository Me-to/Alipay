package com.example.zhifu.pay.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.zhifu.pay.model.PaymentInfo;
import com.example.zhifu.pay.mapper.PaymentInfoMapper;
import com.example.zhifu.pay.service.PaymentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhifu.vo.PayAsyncVo;
import com.example.zhifu.vo.PayVo;
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
        QueryWrapper<PaymentInfo> queryWrapper = new QueryWrapper<PaymentInfo>()
                .eq("out_trade_no", vo.getOut_trade_no());
        PaymentInfo paymentInforesult = this.baseMapper.selectOne(queryWrapper);
        paymentInforesult.setPaymentType(1);
        paymentInforesult.setCallbackTime(vo.getNotify_time());
        paymentInforesult.setCallbackContent("");
        paymentInforesult.setEffective(1);

        if (vo.getTrade_status().equals("TRADE_SUCCESS") || vo.getTrade_status().equals("TRADE_FINISHED")) {
            status = 1;
            paymentInforesult.setPaymentStatus(status);
            this.updateById(paymentInforesult);
            return "success";
        } else {
            paymentInforesult.setPaymentStatus(status);
            this.updateById(paymentInforesult);
            return "fail";
        }

    }

    @Override
    public void oderPay(PayVo payVo) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOutTradeNo(payVo.getOut_trade_no());
        paymentInfo.setPaymentStatus(0);
        paymentInfo.setSubject(payVo.getSubject());
        paymentInfo.setTotalAmount(new BigDecimal(payVo.getTotal_amount()));
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setEffective(1);
        paymentInfo.setPaymentStatus(0);
        this.baseMapper.insert(paymentInfo);
    }
}
