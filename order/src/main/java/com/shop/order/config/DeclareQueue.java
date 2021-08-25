package com.shop.order.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.shop.order.config.mq.ConnectionUtil;

public class DeclareQueue {

    public static String EXCHANGE_NAME = "notifyExchange";

    public static void init() {

        Connection connection = ConnectionUtil.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            String routingKey = "AliPaynotify";
            String message = "消息111";
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println(" [x] Sent :" + message);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    public static void main(String args[]) {
        init();
    }
}
