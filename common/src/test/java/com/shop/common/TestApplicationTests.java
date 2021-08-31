package com.shop.common;

import com.alibaba.fastjson.JSONObject;
import com.shop.common.utils.PostUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class TestApplicationTests {

    @Test
    void contextLoads() {

        long begaintime = System.currentTimeMillis();//开始系统时间

        //线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        //设置集合点为93
        final int count = 3000;
        CountDownLatch countDownLatch = new CountDownLatch(count);//与countDownLatch.await();实现运行完所有线程之后才执行后面的操作
        //final CyclicBarrier barrier = new CyclicBarrier(count);  //与barrier.await() 实现并发;
        //创建100个线程
        for(int i = 0; i < count; i++){

            Task target = new Task(countDownLatch);
            //barrier.await();
            pool.execute(target);
        }

        pool.shutdown();
        try {
            countDownLatch.await();  //这一步是为了将全部线程任务执行完以后，开始执行后面的任务（计算时间，数量）
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis(); //结束时间
        System.out.println(count + " 个  接口请求总耗时 ： "+(endTime-begaintime)+"-----平均耗时为"+ ((endTime-begaintime)/count));

        /*for (int i = 0; i < 100; i++) {
            postTest();
        }*/
    }

    private static void postTest() throws IOException {

        Map<String, String> params = new HashMap<>();
        params.put("productId", "7");
        String url = "http://localhost:8888/order/save";
        String json = "{\"productId\" : 7}";

        System.out.println(PostUtils.send(url, JSONObject.parseObject(json)));
    }


    static class Task implements Runnable {

        private CountDownLatch countDownLatch;  //多线程结束后，执行后面的代码（计算时间、数量）

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try{
                String url = "http://localhost:8888/order/save";
                String json = "{\"productId\" : 7}";
                System.out.println(PostUtils.send(url, JSONObject.parseObject(json)));
            }catch(Exception e){
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        }
    }
}
