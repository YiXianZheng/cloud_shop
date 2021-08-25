package com.shop.product.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductFormDto {

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品类型
     */
    private String type;

    /**
     * 库存
     */
    private Integer num;
}
