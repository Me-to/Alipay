package com.example.zhifu.pay.service;

import com.example.zhifu.pay.model.Commodity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhifu.vo.PayVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangqian
 * @since 2020-10-14
 */
public interface CommodityService extends IService<Commodity> {

    PayVo Configuration(@Param("commodityid") String commodityid);

}
