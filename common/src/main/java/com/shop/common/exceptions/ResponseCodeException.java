package com.shop.common.exceptions;

/**
 * [ 全局异常拦截后保留ResponseCode码的异常]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/8/7 0007 下午 16:11
 * @since JDK1.8
 */
public class ResponseCodeException extends RuntimeException{
    private Integer code;

    public ResponseCodeException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
