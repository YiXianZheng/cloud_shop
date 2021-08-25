package com.shop.order.service.impl;

import com.shop.common.vo.ApiResponse;
import com.shop.order.service.ProductFeignService;

/**
 * @author zyx
 * @date 2021/8/25 9:11
 */
public class ProductFeignServiceFallback implements ProductFeignService {
    @Override
    public ApiResponse pro1() {
        ApiResponse response = new ApiResponse();
        response.setCode("101");
        response.setMsg("获取商品信息失败，请稍后重试！");
        return response;
    }

    @Override
    public ApiResponse subStock(int productId) {
        ApiResponse response = new ApiResponse();
        response.setCode("102");
        response.setMsg("扣除商品库存失败，请稍后重试！");
        return response;
    }
}
