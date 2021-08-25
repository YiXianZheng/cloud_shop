package com.shop.order.config;

import com.rabbitmq.client.*;
import com.shop.order.config.mq.ConnectionUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeclareConsumer {
    public static String EXCHANGE_NAME = "notifyExchange";
    public static String QU_declare_15S = "Qu_declare_15s";
    public static String EX_declare_15S = "EX_declare_15s";
    public static String ROUTINGKEY = "AliPaynotify";
    public static Connection connection = null;
    public static Channel channel = null;
    public static String originalExpiration = "0";

    public static void consume() {
        try {
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            final String queueName = channel.queueDeclare().getQueue();

            channel.queueBind(queueName, EXCHANGE_NAME, ROUTINGKEY);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            final Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    Map<String, Object> headers = properties.getHeaders();
                    if (headers != null) {
                        List<Map<String, Object>> xDeath = (List<Map<String, Object>>) headers.get("x-death");
                        System.out.println("xDeath--- > " + xDeath);
                        if (xDeath != null && !xDeath.isEmpty()) {
                            Map<String, Object> entrys = xDeath.get(0);
                            // for(Entry<String, Object>
                            // entry:entrys.entrySet()){
                            // System.out.println(entry.getKey()+":"+entry.getValue());
                            // }
                            originalExpiration = entrys.get("original-expiration").toString();
                        }
                    }
                    System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'" + "time" + System.currentTimeMillis());
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost post = new HttpPost(message);
                    HttpResponse response = httpClient.execute(post);
                    BufferedReader inreader = null;
                    if (response.getStatusLine().getStatusCode() == 200) {
                        inreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                        StringBuffer responseBody = new StringBuffer();
                        String line = null;
                        while ((line = inreader.readLine()) != null) {
                            responseBody.append(line);
                        }
                        if (!responseBody.equals("success")) {
                            // putDeclre15s(message);
                            if (originalExpiration.equals("0")) {
                                putDeclreQueue(message, 3000, QU_declare_15S);
                            }
                            if (originalExpiration.equals("3000")) {
                                putDeclreQueue(message, 30000, QU_declare_15S);
                            }
                            if (originalExpiration.equals("30000")) {
                                putDeclreQueue(message, 60000, QU_declare_15S);
                            }
                            if (originalExpiration.equals("60000")) {
                                putDeclreQueue(message, 120000, QU_declare_15S);
                            }
                            if (originalExpiration.equals("120000")) {
                                putDeclreQueue(message, 180000, QU_declare_15S);
                            }
                            if (originalExpiration.equals("180000")) {
                                putDeclreQueue(message, 300000, QU_declare_15S);
                            }
                            if (originalExpiration.equals("300000")) {
//								channel.basicConsume(QU_declare_300S,true, this);
                                System.out.println("finish notify");
                            }
                        }
                    } else {
                        System.out.println(response.getStatusLine().getStatusCode());
                    }
                }
            };

            channel.basicConsume(queueName, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }



    static Map<String, Object> xdeathMap = new HashMap<String, Object>();
    static List<Map<String, Object>> xDeath = new ArrayList<Map<String, Object>>();
    static Map<String, Object> xdeathParam = new HashMap<String, Object>();

    public static void putDeclre15s(String message) throws IOException {
        channel.exchangeDeclare(EX_declare_15S, "topic");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", EXCHANGE_NAME);// 死信exchange
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.expiration("3000").deliveryMode(2);// 设置消息TTL
        AMQP.BasicProperties properties = builder.build();
        channel.queueDeclare(QU_declare_15S, false, false, false, args);
        channel.queueBind(QU_declare_15S, EX_declare_15S, ROUTINGKEY);
        channel.basicPublish(EX_declare_15S, ROUTINGKEY, properties, message.getBytes());
        System.out.println("send message in QA_DEFERRED_15S" + message + "time" + System.currentTimeMillis());
    }

    public static void putDeclreQueue(String message, int mis, String queue) throws IOException {
        channel.exchangeDeclare(EX_declare_15S, "topic");
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", EXCHANGE_NAME);// 死信exchange
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.expiration(String.valueOf(mis)).deliveryMode(2);// 设置消息TTL
        AMQP.BasicProperties properties = builder.build();
        channel.queueDeclare(queue, false, false, false, args);
        channel.queueBind(queue, EX_declare_15S, ROUTINGKEY);
        channel.basicPublish(EX_declare_15S, ROUTINGKEY, properties, message.getBytes());
        System.out.println("send message in " + queue + message + "time============" + System.currentTimeMillis());
    }

    public static void main(String args[]) {
        connection = ConnectionUtil.getConnection();
        consume();
    }
}
