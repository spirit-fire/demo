package com.spirit.demo.rabbitmq;

import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang.SerializationUtils;

import java.io.IOException;

/**
 * Created by lgx on 2017/3/25.
 */
public class RabbitmqServiceDemo extends  RabbitmqInit{

    /**
     * send
     * @param message
     */
    private void send(String message){
        this.init();
        try {
//            this.serviceChannel.basicPublish("", RABBITMQ_DEMO_QUEUE_NAME, null, SerializationUtils.serialize(message));
            this.serviceChannel.basicPublish("", RABBITMQ_DEMO_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
                    SerializationUtils.serialize(message)); // 持久化
            System.out.println(String.format("[x] sent: '%s'", message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 主逻辑: send
     */
    public void deal(String message){
        this.init();
        this.send(message);
    }

    public static void main(String[] args){
        RabbitmqServiceDemo rabbitmqServiceDemo = new RabbitmqServiceDemo();
        rabbitmqServiceDemo.deal("Hello world!");
        rabbitmqServiceDemo.deal("Just for fun!");
        rabbitmqServiceDemo.deal("Bye bye!");
        rabbitmqServiceDemo.deal("Bye bye!");
        rabbitmqServiceDemo.close();
    }
}
