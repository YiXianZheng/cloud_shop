package com.shop.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shop.common.utils.ResponseCode;
import com.shop.common.vo.ReturnVo;
import com.shop.product.config.redis.RedisUtil;
import com.shop.product.entity.Product;
import com.shop.product.entity.Stock;
import com.shop.product.mapper.StockMapper;
import com.shop.product.service.ProductService;
import com.shop.product.service.StockService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zyx
 * @since 2021-07-28
 */
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

    @Autowired
    private ProductService productService;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional
    @GlobalTransactional
    public ReturnVo subStock(int productId) {

        log.error("库存seata全局xid= " + RootContext.getXID());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ReturnVo returnVo = new ReturnVo();
        Product product = productService.getById(productId);
        if (product == null) {
            log.error("商品不存在");
            returnVo.code = ReturnVo.ERROR;
            returnVo.responseCode = ResponseCode.Base.NET_ERR;
            return returnVo;
        }

        stockMapper.decrease(productId);

        // 获取新的库存更新到redis
        Stock stock = getOne(new QueryWrapper<Stock>().eq("product_id", productId));
        log.error("新库存：" + stock.getNum());
        redisUtil.set(String.valueOf(productId), stock.getNum());
        returnVo.code = ReturnVo.SUCCESS;
        returnVo.responseCode = ResponseCode.Base.SUCCESS;
        return returnVo;
    }


}
