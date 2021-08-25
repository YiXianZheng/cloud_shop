package com.shop.gateway.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandlerController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String error() {
        return "出现异常";
    }
}
