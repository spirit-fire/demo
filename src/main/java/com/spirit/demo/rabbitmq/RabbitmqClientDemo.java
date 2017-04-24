package com.spirit.demo.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;

/**
 * Created by lgx on 2017/3/25.
 */
public class RabbitmqClientDemo extends RabbitmqInit implements Consumer{

    /**
     * recv
     */
    private void recv(){
        try {
//            this.serviceChannel.basicConsume(RABBITMQ_DEMO_QUEUE_NAME, true, this);
            this.serviceChannel.basicConsume(RABBITMQ_DEMO_QUEUE_NAME, false, this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 主逻辑: recv
     */
    public void deal(){
        this.init();
        this.recv();
    }

    public static void main(String[] args){
        RabbitmqClientDemo rabbitmqClientDemo = new RabbitmqClientDemo();
        rabbitmqClientDemo.deal();
        rabbitmqClientDemo.close();
    }

    public void handleConsumeOk(String s) {
        try {
            this.serviceChannel.basicAck(0L, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("[c] consumer: '%s' ", s));
    }

    public void handleCancelOk(String s) {

    }

    public void handleCancel(String s) throws IOException {

    }

    public void handleShutdownSignal(String s, ShutdownSignalException e) {

    }

    public void handleRecoverOk(String s) {

    }

    public void handleDelivery(String s, Envelope envelope, AMQP.BasicProperties basicProperties, byte[] bytes) throws IOException {
        String ss = (String) SerializationUtils.deserialize(bytes);
        System.out.println(String.format("[c] consumer received: '%s' ", ss));
//        System.out.println(String.format("[c] consumer received: '%s' ", bytes.toString()));
    }
}
