package com.example.zhifu.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.example.zhifu.config.AlipayTemplate;
import com.example.zhifu.listener.CancelSender;
import com.example.zhifu.pay.service.CommodityService;
import com.example.zhifu.pay.service.PaymentInfoService;
import com.example.zhifu.vo.PayAsyncVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@Slf4j
public class PayNotifyController {
    @Autowired
    CommodityService commodityService;

    @Autowired
    AlipayTemplate alipayTemplate;

    @Autowired
    PaymentInfoService paymentInfoService;

    private static Logger LOGGER = LoggerFactory.getLogger(PayNotifyController.class);
    /**
     * 支付宝的异步回调
     *
     * @return
     */
    @PostMapping("/notify")
    public String pay_notify(PayAsyncVo vo, HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        log.info("收到支付宝最后的通知数据：" + vo);
        System.out.println("支付宝的响应：异步通知");
        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        if(AlipaySignature.rsaCheckV1(params, alipayTemplate.getAlipay_public_key(), alipayTemplate.getCharset(), alipayTemplate.getSign_type())){
            LOGGER.info("pay_notify--验签成功");
            String result = paymentInfoService.handlePayResult(vo);
            return result;
        }else {
            LOGGER.info("pay_notify--验签失败");
            return "fail";
        }

    }

}
