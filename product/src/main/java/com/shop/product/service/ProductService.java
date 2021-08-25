package com.shop.product.service;

import com.shop.common.vo.ReturnVo;
import com.shop.product.dto.ProductFormDto;
import com.shop.product.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyx
 * @since 2021-07-28
 */
public interface ProductService extends IService<Product> {

    ReturnVo addNewProduct(ProductFormDto dto);
}
