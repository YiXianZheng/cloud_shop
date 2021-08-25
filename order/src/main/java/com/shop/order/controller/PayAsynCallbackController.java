package com.shop.order.controller;

import com.shop.order.config.mq.AbstractPayCallbackTemplate;
import com.shop.order.config.mq.TemplateFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PayAsynCallbackController {

    private static final String UNIONPAYCALLBACK_TEMPLATE = "unionPayCallbackTemplate";
    //支付宝的回调——略

    /**
     * 银联异步回调接口执行代码
     *
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/unionPayAsynCallback")
    public String unionPayAsynCallback(HttpServletRequest req, HttpServletResponse resp) {
        AbstractPayCallbackTemplate abstractPayCallbackTemplate = TemplateFactory
                .getPayCallbackTemplate(UNIONPAYCALLBACK_TEMPLATE);//获取具体实现的模版工厂方案
        return abstractPayCallbackTemplate.asyncCallBack(req, resp);//调用异步回调方法
    }
}
