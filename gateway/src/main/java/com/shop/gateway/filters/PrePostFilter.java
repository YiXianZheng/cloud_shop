package com.shop.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class PrePostFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; // 可以在请求被路由之前调用  pre
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        // 获取request对象
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // 判断是否登录请求
        /*return !request.getRequestURI().contains("/login") && !request.getRequestURI().contains("/test")
                && !request.getRequestURI().contains("/updateAllToken");*/// 是否执行该过滤器，此处为true，说明需要过滤
        return true;
    }

    @Override
    public Object run() {
        long startTime = System.currentTimeMillis();

        // 获取request对象
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        ctx.getResponse().setCharacterEncoding("UTF-8");

        long endTime = System.currentTimeMillis();
        log.error("------------- 接口过滤 -------------" + (endTime - startTime));
        return null;
    }
}
