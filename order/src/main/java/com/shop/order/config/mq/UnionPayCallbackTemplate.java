package com.shop.order.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
//@Component
public class UnionPayCallbackTemplate extends AbstractPayCallbackTemplate{
    
    @Override
    public Map<String, String> verifySignature(HttpServletRequest req, HttpServletResponse resp) {

        log.error("BackRcvResponse接收后台通知开始");

        String encoding = "UTF-8";
        // 获取银联通知服务器发送的后台通知参数
        Map<String, String> reqParam = getAllRequestParam(req);

        log.error("BackRcvResponse接收后台通知结束");
        return reqParam;
    }

    // 异步回调中网络尝试延迟，导致异步回调重复执行 可能存在幂等性问题
    //
    @Override
    @Transactional
    public String asyncService(Map<String, String> verifySignature) {

        String orderId = verifySignature.get("orderId"); // 获取后台通知的数据，其他字段也可用类似方式获取
        String respCode = verifySignature.get("respCode");

        // 判断respCode=00、A6后，对涉及资金类的交易，请再发起查询接口查询，确定交易成功后更新数据库。
        System.out.println("orderId:" + orderId + ",respCode:" + respCode);
        // 1.判断respCode是否为已经支付成功断respCode=00、A6后，
        if (!(respCode.equals("00") || respCode.equals("A6"))) {
            return failResult();
        }

        int i = 1 / 0; // 支付状态还是为待支付状态但是 积分缺增加
        return successResult();
    }



    @Override
    public String failResult() {
        return "1";
    }

    @Override
    public String successResult() {
        return "0";
    }

    /**
     * 获取请求参数中所有的信息 当商户上送frontUrl或backUrl地址中带有参数信息的时候，
     * 这种方式会将url地址中的参数读到map中，会导多出来这些信息从而致验签失败，
     * 这个时候可以自行修改过滤掉url中的参数或者使用getAllRequestParamStream方法。
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    // System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }

    /**
     * 获取请求参数中所有的信息。
     * 非struts可以改用此方法获取，好处是可以过滤掉request.getParameter方法过滤不掉的url中的参数。
     * struts可能对某些content-type会提前读取参数导致从inputstream读不到信息，所以可能用不了这个方法。
     * 理论应该可以调整struts配置使不影响，但请自己去研究。
     * 调用本方法之前不能调用req.getParameter("key");这种方法，否则会导致request取不到输入流。
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParamStream(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        try {
            String notifyStr = "收到通知报文";
            log.error("收到通知报文：" + notifyStr);
            String[] kvs = notifyStr.split("&");
            for (String kv : kvs) {
                String[] tmp = kv.split("=");
                if (tmp.length >= 2) {
                    String key = tmp[0];
                    String value = "111";
                    res.put(key, value);
                }
            }
        } catch (Exception e) {
            log.error("getAllRequestParamStream.IOException error: " + e.getClass() + ":" + e.getMessage());
        }
        return res;
    }
}
