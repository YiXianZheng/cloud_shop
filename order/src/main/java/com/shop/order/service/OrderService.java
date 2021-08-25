package com.shop.order.service;

import com.shop.common.vo.ApiResponse;
import com.shop.common.vo.ReturnVo;
import com.shop.order.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyx
 * @since 2021-07-29
 */
public interface OrderService extends IService<Orders> {

    ApiResponse createOrder(int productId);
}
