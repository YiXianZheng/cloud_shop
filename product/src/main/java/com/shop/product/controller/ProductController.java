package com.shop.product.controller;

import com.shop.common.basePDSC.BaseController;
import com.shop.common.utils.LanguageEnum;
import com.shop.common.utils.ResponseCode;
import com.shop.common.vo.ApiResponse;
import com.shop.common.vo.ReturnVo;
import com.shop.product.dto.ProductFormDto;
import com.shop.product.entity.Product;
import com.shop.product.service.ProductService;
import com.shop.product.service.StockService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(value = "订单接口")
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;
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

        List<Product> list = productService.list();
        return ApiResponse.creatSuccess(list);
    }

    @GetMapping("getById/{id}")
    @ApiOperation(value = "通过id获取商品",notes = "通过id获取商品", httpMethod = "GET", response = ApiResponse.class)
    public ApiResponse getById(@PathVariable(name = "id") int id) {

        return ApiResponse.creatSuccess(productService.getById(id));
    }

    @PostMapping("/save")
    @ApiOperation(value = "新增商品",notes = "新增商品", httpMethod = "POST", response = ApiResponse.class)
    public ApiResponse save(@RequestBody ProductFormDto dto) {

        try {
            ReturnVo returnVo = productService.addNewProduct(dto);
            return toApiResponse(returnVo);
        } catch (Exception e) {
            log.error("添加商品失败：" + e.getMessage());
            return ApiResponse.creatFail(ResponseCode.Base.SYSTEM_ERR);
        }
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新商品",notes = "更新商品", httpMethod = "POST", response = ApiResponse.class)
    public ApiResponse update(@RequestBody Product entity) {

        try {
            productService.updateById(entity);
            return ApiResponse.creatSuccess("修改成功！");
        } catch (Exception e) {
            log.error("修改商品失败：" + e.getMessage());
            return ApiResponse.creatFail(ResponseCode.Base.SYSTEM_ERR);
        }
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除商品",notes = "删除商品", httpMethod = "GET", response = ApiResponse.class)
    public ApiResponse delete(@PathVariable(name = "id") int id) {

        try {
            productService.removeById(id);
            return ApiResponse.creatSuccess("删除成功！");
        } catch (Exception e) {
            log.error("删除商品失败：" + e.getMessage());
            return ApiResponse.creatFail(ResponseCode.Base.SYSTEM_ERR);
        }
    }

    @GetMapping("/subStock/{id}")
    public ApiResponse subStock(@PathVariable(name = "id") int id) {

        try {
            ReturnVo returnVo = stockService.subStock(id);
            log.error("返回状态码：" + returnVo.code);
            if (ReturnVo.SUCCESS == returnVo.code) {
                return toApiResponse(returnVo);
            } else {
                throw new Exception(returnVo.responseCode.getExplain(LanguageEnum.zh_CN));
            }

        } catch (Exception e) {
            log.error("扣除库存失败：" + e.getMessage());
            return ApiResponse.creatFail(ResponseCode.Base.API_NO_EXISTS);
        }
    }
}
