package com.shop.order.config.redis;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zyx
 * @date 2021/8/23 10:17
 */
@Configuration
public class RedisLockConfig {

    @Bean
    public Redisson redisson(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379").setPassword("123456")
                .setDatabase(0);
        return (Redisson) Redisson.create(config);
    }
}
