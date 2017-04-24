package com.spirit.demo.serviceloader;

/**
 * Created by lgx on 2017/4/11.
 */
public class ServiceDemoTest2Impl implements ServiceDemoInterface {
    /** @see ServiceDemoInterface#getCurrentServiceName() */
    @Override
    public void getCurrentServiceName() {
        System.out.println("[y]: Current service name is test2...");
    }
}
