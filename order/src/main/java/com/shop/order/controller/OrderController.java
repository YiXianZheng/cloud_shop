package com.shop.order.controller;

import com.shop.common.basePDSC.BaseController;
import com.shop.common.utils.DateUtil;
import com.shop.common.utils.ResponseCode;
import com.shop.common.vo.ApiResponse;
import com.shop.order.config.redis.RedisUtil;
import com.shop.order.config.redis.lockutil.DistributedLockHandler;
import com.shop.order.config.redis.lockutil.Lock;
import com.shop.order.dto.OrderFormDto;
import com.shop.order.entity.Orders;
import com.shop.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Api(value = "订单接口")
@Slf4j
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private DistributedLockHandler distributedLockHandler;
    @Autowired
    private Redisson redisson;

    @RequestMapping("/test")
    @ApiOperation(value = "测试接口",notes = "测试接口",httpMethod = "GET",response = String.class)
    public String test() {

        return "测试页面！";
    }

    @GetMapping("getList")
    @ApiOperation(value = "获取全部订单",notes = "获取全部订单", httpMethod = "GET", response = ApiResponse.class)
    public ApiResponse getAll() {

        log.error("库存：" + redisUtil.get("7"));
        List<Orders> list = orderService.list();
        return ApiResponse.creatSuccess(list);
    }

    @GetMapping("getById/{id}")
    @ApiOperation(value = "获取根据id获取订单",notes = "获取根据id获取订单", httpMethod = "GET", response = ApiResponse.class)
    public ApiResponse getById(@PathVariable(name = "id") int id) {

        return ApiResponse.creatSuccess(orderService.getById(id));
    }

    @PostMapping("/save")
    @ApiOperation(value = "创建订单",notes = "创建订单", httpMethod = "POST", response = ApiResponse.class)
    public ApiResponse save(@RequestBody OrderFormDto dto) {
        String lockName = "lock_create_order";
        Lock lock = new Lock(lockName, DateUtil.DATE_PATTERN_01);
        try {
            if (distributedLockHandler.tryLock(lock)) {
                return orderService.createOrder(dto.getProductId());
            }
            return ApiResponse.creatFail(ResponseCode.Base.GET_LOCK_ERR);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.creatFail(ResponseCode.Base.SYSTEM_ERR);
        } finally {
            distributedLockHandler.releaseLock(lock);
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新订单",notes = "更新订单", httpMethod = "POST", response = ApiResponse.class)
    public ApiResponse update(@RequestBody Orders entity) {

        try {
            orderService.updateById(entity);
            return ApiResponse.creatSuccess("修改成功！");
        } catch (Exception e) {
            log.error("修改订单失败：" + e.getMessage());
            return ApiResponse.creatFail(ResponseCode.Base.SYSTEM_ERR);
        }
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除订单",notes = "删除订单", httpMethod = "GET", response = ApiResponse.class)
    public ApiResponse delete(@ApiParam(name = "id", value = "订单id", required = true) int id) {

        try {
            orderService.removeById(id);
            return ApiResponse.creatSuccess("删除成功！");
        } catch (Exception e) {
            log.error("删除订单失败：" + e.getMessage());
            return ApiResponse.creatFail(ResponseCode.Base.SYSTEM_ERR);
        }
    }
}
