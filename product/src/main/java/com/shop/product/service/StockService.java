package com.shop.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shop.common.vo.ReturnVo;
import com.shop.product.entity.Stock;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyx
 * @since 2021-07-28
 */
public interface StockService extends IService<Stock> {

    ReturnVo subStock(int productId);
}
