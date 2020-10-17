package com.example.zhifu.pay.model;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 支付对账表
 * </p>
 *
 * @author zhangqian
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("payment_info")
@ApiModel(value="PaymentInfo对象", description="支付对账表")
public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "商户订单号")
    private Long id;

    private Long commodity_id;
    @ApiModelProperty(value = "交易流水号")
    private String outTradeNo;

    @ApiModelProperty(value = "支付类型（微信与支付宝）")
    private Integer paymentType;

    @ApiModelProperty(value = "支付宝交易凭证号")
    private String tradeNo;

    @ApiModelProperty(value = "订单金额。订单中获取")
    private BigDecimal totalAmount;

    @ApiModelProperty(value = "交易内容。利用商品名称拼接。")
    private String subject;

    @ApiModelProperty(value = "支付状态，默认值0-未支付，1-已支付。")
    private Integer paymentStatus;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "回调时间，初始为空，支付宝异步回调时记录")
    private String callbackTime;

    @ApiModelProperty(value = "回调信息，初始为空，支付宝异步回调时记录")
    private String callbackContent;

    @ApiModelProperty(value = "订单状态，0-无效，1-有效。")
    private Integer effective;

}
