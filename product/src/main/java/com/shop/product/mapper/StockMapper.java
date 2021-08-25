package com.shop.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.product.entity.Stock;

public interface StockMapper extends BaseMapper<Stock> {

    void decrease(int productId);
}
