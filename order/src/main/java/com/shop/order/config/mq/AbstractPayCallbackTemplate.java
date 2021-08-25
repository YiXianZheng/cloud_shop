package com.shop.order.config.mq;

import com.alibaba.druid.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//@Component
@Slf4j
public abstract class AbstractPayCallbackTemplate {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 获取所有请求的参数，封装成Map集合 并且验证是否被篡改
     */
    public abstract Map<String, String> verifySignature(HttpServletRequest req, HttpServletResponse resp);

    /**
     * 异步回调执行业务逻辑
     */
    @Transactional
    public abstract String asyncService(Map<String, String> verifySignature);
    public abstract String failResult();
    public abstract String successResult();

    /**
     * *1. 将报文数据存放到es <br>
     * 1. 验证报文参数<br>
     * 2. 将日志根据支付id存放到数据库中<br>
     * 3. 执行的异步回调业务逻辑<br>
     */
    @Transactional
    public String asyncCallBack(HttpServletRequest req, HttpServletResponse resp) {
        // 1. 验证报文参数 相同点 获取所有的请求参数封装成为map集合 并且进行参数验证
        Map<String, String> verifySignature = verifySignature(req, resp);
        // 2.将日志根据支付id存放到数据库中
        String paymentId = "paymentId";
        if (StringUtils.isEmpty(paymentId)) {
            return failResult();
        }
//		log.info(">>>>>asyncCallBack service 01");
        // 3.采用异步形式写入日志到数据库中
        threadPoolTaskExecutor.execute(new PayLogThread(paymentId, verifySignature));
//		log.info(">>>>>asyncCallBack service 04");

        // 4.201报文验证签名失败

        // 5.执行的异步回调业务逻辑
        return asyncService(verifySignature);
    }

    /**
     * 采用多线程技术或者MQ形式进行存放到数据库中
     */
    private void payLog(String paymentId, Map<String, String> verifySignature) {
		log.info(">>paymentId:{paymentId},verifySignature:{}", verifySignature);
    }

    // A 1423 B 1234
    /**
     * 使用多线程写入日志目的：加快响应 提高程序效率 使用线程池维护线程
     */
    class PayLogThread implements Runnable {
        private String paymentId;
        private Map<String, String> verifySignature;

        public PayLogThread(String paymentId, Map<String, String> verifySignature) {
            this.paymentId = paymentId;
            this.verifySignature = verifySignature;
        }

        @Override
        public void run() {
//			log.info(">>>>>asyncCallBack service 02");
            payLog(paymentId, verifySignature);
//			log.info(">>>>>asyncCallBack service 03");
        }

    }
}
