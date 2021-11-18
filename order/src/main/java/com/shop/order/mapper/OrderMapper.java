package com.shop.order.mapper;

import com.shop.order.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zyx
 * @since 2021-07-29
 */
@Repository
public interface OrderMapper extends BaseMapper<Orders> {

    int insertAll(List<Orders> list);
}
