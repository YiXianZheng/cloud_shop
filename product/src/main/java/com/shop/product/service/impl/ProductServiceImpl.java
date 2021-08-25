package com.shop.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.common.utils.ResponseCode;
import com.shop.common.vo.ReturnVo;
import com.shop.product.config.redis.RedisUtil;
import com.shop.product.dto.ProductFormDto;
import com.shop.product.entity.Product;
import com.shop.product.entity.Stock;
import com.shop.product.mapper.ProductMapper;
import com.shop.product.service.ProductService;
import com.shop.product.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zyx
 * @since 2021-07-28
 */
@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private StockService stockService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReturnVo addNewProduct(ProductFormDto dto) {

        ReturnVo returnVo = new ReturnVo();

        Product entity = new Product();
        BeanUtils.copyProperties(dto, entity);
        // 商品名称不能重复
        if (StringUtils.isBlank(dto.getName())) {
            returnVo.code = ReturnVo.FAIL;
            returnVo.responseCode = ResponseCode.LoginRegister.USER_EXIST;
            return returnVo;
        }
        Product product = getOne(new QueryWrapper<Product>().eq("name", dto.getName()));
        if (product != null) {
            returnVo.code = ReturnVo.FAIL;
            returnVo.responseCode = ResponseCode.Parameter.MISSINGUSERNAME;
            return returnVo;
        }
        boolean flag = save(entity);
        if (!flag) {
            returnVo.code = ReturnVo.FAIL;
            returnVo.responseCode = ResponseCode.Parameter.MISSINGPASSWORD;
            return returnVo;
        }
        // 生成库存表
        Product product1 = getOne(new QueryWrapper<Product>().eq("name", dto.getName()));
        Stock stock = new Stock();
        stock.setProductId(product1.getId());
        stock.setNum(dto.getNum());
        stockService.save(stock);

        // 将库存放到redis
        redisUtil.set(String.valueOf(product1.getId()), dto.getNum());

        return ReturnVo.returnSuccess(ReturnVo.SUCCESS);
    }
}
