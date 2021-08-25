package com.shop.order.service;

import com.shop.common.vo.ApiResponse;
import com.shop.order.service.impl.ProductFeignServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(value = "product-service", fallback = ProductFeignServiceFallback.class)
public interface ProductFeignService {

    @RequestMapping("/pro1")
    ApiResponse pro1();

    @RequestMapping("/subStock/{id}")
    ApiResponse subStock(@PathVariable(value = "id") int productId);
}
