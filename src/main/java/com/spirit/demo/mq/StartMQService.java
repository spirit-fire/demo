package com.spirit.demo.mq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lgx on 2017/5/14.
 */
public class StartMQService {

    public static void main(String[] args){
        if(args.length < 1){
            System.out.println("参数错误, 请输入项目名 [project name]....");
            System.exit(-1);
        }
        String projectName = args[0];
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContextProcAll.xml");
        Bootstrap controllerInterface = (Bootstrap) ctx.getBean(projectName);
        controllerInterface.deal();
    }
}
