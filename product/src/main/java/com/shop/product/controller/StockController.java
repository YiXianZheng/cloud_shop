package com.shop.product.controller;

import com.shop.common.utils.ResponseCode;
import com.shop.common.vo.ApiResponse;
import com.shop.product.entity.Stock;
import com.shop.product.service.StockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(value = "订单接口")
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/pro1")
    @ApiOperation(value = "测试提供feign接口",notes = "测试提供feign接口",httpMethod = "GET",response = ApiResponse.class)
    public ApiResponse pro1() {

        log.error("当前调用端口：" );
        return ApiResponse.creatSuccess("这是商品接口！");
    }

    @GetMapping("getList")
    @ApiOperation(value = "获取全部商品",notes = "获取全部商品", httpMethod = "GET", response = ApiResponse.class)
    public ApiResponse getAll() {

        List<Stock> list = stockService.list();
        return ApiResponse.creatSuccess(list);
    }

    @GetMapping("getById")
    @ApiOperation(value = "获取全部商品",notes = "获取全部商品", httpMethod = "GET", response = ApiResponse.class)
    public ApiResponse getById(@ApiParam(name = "id", value = "商品id", required = true) int id) {

        return ApiResponse.creatSuccess(stockService.getById(id));
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存商品",notes = "保存商品", httpMethod = "POST", response = ApiResponse.class)
    public ApiResponse save(@RequestBody Stock entity) {

        try {
            stockService.save(entity);
            return ApiResponse.creatSuccess("添加成功！");
        } catch (Exception e) {
            log.error("添加商品失败：" + e.getMessage());
            return ApiResponse.creatFail(ResponseCode.Base.SYSTEM_ERR);
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新商品",notes = "更新商品", httpMethod = "POST", response = ApiResponse.class)
    public ApiResponse update(@RequestBody Stock entity) {

        try {
            stockService.updateById(entity);
            return ApiResponse.creatSuccess("修改成功！");
        } catch (Exception e) {
            log.error("修改商品失败：" + e.getMessage());
            return ApiResponse.creatFail(ResponseCode.Base.SYSTEM_ERR);
        }
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除商品",notes = "删除商品", httpMethod = "GET", response = ApiResponse.class)
    public ApiResponse delete(@ApiParam(name = "id", value = "商品id", required = true) int id) {

        try {
            stockService.removeById(id);
            return ApiResponse.creatSuccess("删除成功！");
        } catch (Exception e) {
            log.error("删除商品失败：" + e.getMessage());
            return ApiResponse.creatFail(ResponseCode.Base.SYSTEM_ERR);
        }
    }
}
