package com.shop.order.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.order.dto.PageBiDdxxTJDTO;
import com.shop.order.entity.BiDdxxTJ;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.order.vo.PageBiDdxxTJVO;
import feign.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zyx
 * @since 2021-08-12
 */
@Repository
public interface BiDdxxTJMapper extends BaseMapper<BiDdxxTJ> {

    IPage<PageBiDdxxTJVO> pageBiDdxxTJ(Page<PageBiDdxxTJVO> page, PageBiDdxxTJDTO dto);
}
