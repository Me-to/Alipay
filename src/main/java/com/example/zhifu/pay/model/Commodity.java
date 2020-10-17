package com.example.zhifu.pay.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangqian
 * @since 2020-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("commodity")
@ApiModel(value="Commodity对象", description="")
public class Commodity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品id")
    private String commodityid;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "金额")
    private Float money;

    @ApiModelProperty(value = "库存")
    private Integer inventory;

    @ApiModelProperty(value = "创建日期")
    private Date createtime;

    @ApiModelProperty(value = "商品描述")
    private String description;


}
