package com.shop.order.controller;


import com.shop.common.basePDSC.BaseController;
import com.shop.common.utils.ResponseCode;
import com.shop.common.vo.ApiResponse;
import com.shop.order.dto.PageBiDdxxTJDTO;
import com.shop.order.service.BiDdxxTJService;
import com.shop.order.util.PageResult;
import com.shop.order.vo.PageBiDdxxTJVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zyx
 * @since 2021-08-12
 */
@RestController
@Api(value = "订单统计接口")
@RequestMapping("/bi-ddxx-tj")
public class BiDdxxTJController extends BaseController {

    @Autowired
    private BiDdxxTJService ddxxTJService;

    @PostMapping("/pageDdxxTj")
    @ApiOperation(value = "分页订单信息统计接口的名称", notes = "分页订单信息统计描述", response = PageBiDdxxTJVO.class)
    public ApiResponse pageDdxxTj(@RequestBody PageBiDdxxTJDTO dto) {

        PageResult result = ddxxTJService.pageBiDdxxTJ(dto);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ResponseCode.Base.SUCCESS.toString());
        apiResponse.setMsg("请求成功！");
        apiResponse.setData(result);
        return apiResponse;
    }
}
