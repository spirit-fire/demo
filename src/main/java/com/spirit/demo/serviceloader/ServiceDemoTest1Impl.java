package com.spirit.demo.serviceloader;

/**
 * Created by lgx on 2017/4/10.
 */
public class ServiceDemoTest1Impl implements ServiceDemoInterface {

    /** @see ServiceDemoInterface#getCurrentServiceName() */
    @Override
    public void getCurrentServiceName() {
        System.out.println("[x]: Current service name is test1...");
    }
}
