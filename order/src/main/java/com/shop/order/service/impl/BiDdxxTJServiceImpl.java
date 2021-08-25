package com.shop.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.order.dto.PageBiDdxxTJDTO;
import com.shop.order.entity.BiDdxxTJ;
import com.shop.order.mapper.BiDdxxTJMapper;
import com.shop.order.service.BiDdxxTJService;
import com.shop.order.util.PageResult;
import com.shop.order.util.PageUtil;
import com.shop.order.vo.PageBiDdxxTJVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyx
 * @since 2021-08-12
 */
@Service
public class BiDdxxTJServiceImpl extends ServiceImpl<BiDdxxTJMapper, BiDdxxTJ> implements BiDdxxTJService {

    @Autowired
    private BiDdxxTJMapper ddxxTJMapper;
    @Override
    public PageResult pageBiDdxxTJ(PageBiDdxxTJDTO dto) {

        Page<PageBiDdxxTJVO> page = new Page<>(dto.getPage(), dto.getRows());
        IPage<PageBiDdxxTJVO> pageTestVos = ddxxTJMapper.pageBiDdxxTJ(page, dto);

        //如果使用的是默认的单表Mapper的通用方法,查询出来的数据是IPage<Entity>类型的,还要转换为IPage<VO>类型再返回,如下
        /*IPage<BiDdxxTJ> aa10IPage = ddxxTJMapper.selectPage(
                new Page<>(dto.getPage(), dto.getRows())
                , new QueryWrapper<BiDdxxTJ>()
                        .eq("AAA100", "AAA100")
        );*/

        return PageUtil.PageResult(pageTestVos);
    }
}
