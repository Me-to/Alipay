package com.example.zhifu.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.zhifu.pay.model.Commodity;
import com.example.zhifu.pay.mapper.CommodityMapper;
import com.example.zhifu.pay.service.CommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhifu.vo.PayVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhangqian
 * @since 2020-10-14
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Override
    public PayVo Configuration(String commodityid) {
        PayVo payVo = new PayVo();
        //查询要购买的商品
        QueryWrapper<Commodity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commodityid", commodityid);
        Commodity commodity = this.baseMapper.selectOne(queryWrapper);
        //生成流水号
        String trade = UUID.randomUUID().toString().replace("-","");
        payVo.setOut_trade_no(trade);
        payVo.setTotal_amount(String.valueOf(commodity.getMoney()));
        payVo.setSubject(commodity.getName());
        payVo.setBody(commodity.getDescription());


        return payVo;
    }
}
