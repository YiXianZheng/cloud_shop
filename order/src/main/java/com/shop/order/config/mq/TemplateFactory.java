package com.shop.order.config.mq;

public class TemplateFactory {

    public static AbstractPayCallbackTemplate getPayCallbackTemplate(String beanId) {
        //SpringContextUtil是对Spring容器进行各种上下文操作的工具类，该工具类必须声明为Spring 容器里的一个Bean对象，
        //否则无法自动注入ApplicationContext对象，可使用@Component注解实例化
        return (AbstractPayCallbackTemplate) SpringContextUtil.getBean(beanId);//通过name获取 Bean.
    }
}
