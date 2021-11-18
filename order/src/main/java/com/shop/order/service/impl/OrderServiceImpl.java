package com.shop.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.common.utils.DateUtil;
import com.shop.common.utils.ResponseCode;
import com.shop.common.vo.ApiResponse;
import com.shop.order.config.redis.RedisUtil;
import com.shop.order.entity.Orders;
import com.shop.order.mapper.OrderMapper;
import com.shop.order.service.OrderService;
import com.shop.order.service.ProductFeignService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zyx
 * @since 2021-07-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    @GlobalTransactional
    public ApiResponse createOrder(int productId) {

        // 获取redis的库存
        int num = (int) redisUtil.get(String.valueOf(productId));
        log.error("商品库存 ----- " + num);
        if (num < 1) {

            return ApiResponse.creatFail(ResponseCode.Base.OUT_OF_STOCK);
        }

        // 生成订单
        Orders order = new Orders();
        order.setProductId(productId);
        order.setCreateor("郑益贤" + UUID.randomUUID().toString().substring(0, 4));
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_PATTERN_01);
        String format = sdf.format(new Date());
        order.setCreatetime(format);
        order.setStatus("0");
        save(order);
        // 扣库存
        ApiResponse response = productFeignService.subStock(productId);
        if (!String.valueOf(ResponseCode.Base.SUCCESS.getCode()).equals(response.getCode())) {

            return response;
        }

        return ApiResponse.creatSuccess();
    }

    @Transactional(rollbackFor = Exception.class)
    public int batchInsertOrders(List<Integer> ids, String schoolNo) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }

        List<Orders> orders = new LinkedList<>();
        ids.forEach(id -> {
            // 生成订单
            Orders order = new Orders();
            order.setProductId(id);
            order.setCreateor("郑益贤" + UUID.randomUUID().toString().substring(0, 4));
            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_PATTERN_01);
            String format = sdf.format(new Date());
            order.setCreatetime(format);
            order.setStatus("0");
            orders.add(order);
        });

        int nThreads = 50;

        int size = orders.size();
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Future<Integer>> futures = new ArrayList<>(nThreads);

        for (int i = 0; i < nThreads; i++) {
            List<Orders> list = orders.subList(size / nThreads * i, size / nThreads * (i + 1));
            Callable<Integer> task1 = () -> {
                orderMapper.insertAll(list);
                return 1;
            };
            futures.add(executorService.submit(task1));
        }
        executorService.shutdown();
        if (!futures.isEmpty()) {
            return 10;
        }
        return -10;
    }


}
