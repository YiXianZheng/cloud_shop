package com.shop.order.service;

import com.shop.order.util.PageResult;
import com.shop.order.dto.PageBiDdxxTJDTO;
import com.shop.order.entity.BiDdxxTJ;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zyx
 * @since 2021-08-12
 */
public interface BiDdxxTJService extends IService<BiDdxxTJ> {

    PageResult pageBiDdxxTJ(PageBiDdxxTJDTO dto);

    List<Map<String, Object>> selectAll();
}
