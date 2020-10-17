package com.example.zhifu.config;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.example.zhifu.vo.PayVo;
import lombok.Data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {
    private static Logger LOGGER = LoggerFactory.getLogger(AlipayTemplate.class);
        // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private String gatewayUrl;
    //在支付宝创建的应用的id
    private String app_id;
    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchant_private_key;
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String alipay_public_key;
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private String notify_url;
    // 同步通知，支付成功，一般跳转到成功页
    private String return_url;

        // 签名方式
    public  String sign_type;

    // 字符编码格式
    public  String charset;

    public String pay(PayVo vo) throws AlipayApiException {

        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", "utf-8", alipay_public_key, "RSA2");
        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        // 同步回调路径
        request.setReturnUrl(return_url);
        // 异步回调路径
        request.setNotifyUrl(notify_url);
        // 业务参数
        request.setBizContent(JSON.toJSONString(vo));
        String result = alipayClient.pageExecute(request).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        LOGGER.info("AlipayTemplate--支付宝收到消息");
        return result;
    }

    public String closePay(String out_trade_no) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, app_id, merchant_private_key, "json", "utf-8", alipay_public_key, "RSA2");
        AlipayTradeCloseRequest request=new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model=new AlipayTradeCloseModel();
        model.setOutTradeNo(out_trade_no);
//        model.setTradeNo(payVo.get);
        request.setBizModel(model);
        AlipayTradeCloseResponse response=alipayClient.execute(request);
        if (response.isSuccess()){
            LOGGER.info("closePay--关闭订单成功");
            return "success";
        }else {
            LOGGER.info("closePay--关闭订单失败");
            return "fail";
        }

    }
}
