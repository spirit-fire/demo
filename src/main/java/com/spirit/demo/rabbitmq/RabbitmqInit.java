package com.spirit.demo.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by lgx on 2017/3/25.
 */
public class RabbitmqInit {
    public static final String RABBITMQ_DEMO_QUEUE_NAME = "RABBITMQ_DEMO";

    public Connection connection = null;

    public Channel serviceChannel = null;

    /**
     * 初始化呀
     */
    public void init(){
        if(null==this.serviceChannel){
            try {
                int prefetchCount = 1;
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");
                this.connection = factory.newConnection();
                this.serviceChannel = this.connection.createChannel();
                this.serviceChannel.queueDeclare(RABBITMQ_DEMO_QUEUE_NAME, false, false, false, null);
                this.serviceChannel.basicQos(prefetchCount);// RabbitMQ不要同时给一个工作者超过一个任务

            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * close
     */
    public void close(){
        if(null==this.serviceChannel){
            try {
                this.serviceChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }

        if(null==this.connection){
            try {
                this.connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
