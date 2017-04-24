package com.spirit.demo.serviceloader;

import java.util.ServiceLoader;

/**
 * Created by lgx on 2017/4/11.
 */
public class ServiceDemoController {

    public static void main(String[] args){
        ServiceLoader<ServiceDemoInterface> serviceLoader =
                ServiceLoader.load(ServiceDemoInterface.class);
        for(ServiceDemoInterface service:serviceLoader){
            service.getCurrentServiceName();
        }
    }
}
