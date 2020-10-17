package com.example.zhifu.pay.service;

import com.example.zhifu.pay.model.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhifu.vo.PayAsyncVo;
import com.example.zhifu.vo.PayVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 支付对账表 服务类
 * </p>
 *
 * @author zhangqian
 * @since 2020-10-12
 */
public interface PaymentInfoService extends IService<PaymentInfo> {

    String handlePayResult(@Param("vo") PayAsyncVo vo);

    void oderPay(PayVo payVo);
}
