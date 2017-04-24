package com.spirit.demo.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Created by lgx on 2017/4/13.
 */
public class ThreadExecutorsPoolDemo {

    private static final Logger log = Logger.getLogger(ThreadExecutorsPoolDemo.class.getName());

    private ThreadPoolExecutor threadPool = null;

    /**
     * ThreadFactory
     */
    private class CustomThreadFactory implements ThreadFactory{

        AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadNmae = ThreadExecutorsPoolDemo.class.getName()+count.addAndGet(1);
            System.out.println("[c] create thread: "+threadNmae);
            t.setName(threadNmae);
            return t;
        }
    }

    /**
     * RejectedExecutionHandler
     */
    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * init
     */
    private void init(){
//        this.threadPool = new ThreadPoolExecutor(10,20,10, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(50));
        this.threadPool = new ThreadPoolExecutor(10,20,10, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(50),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());
    }

    /**
     * destroy
     */
    private void destroy(){
        if(null!=this.threadPool){
            this.threadPool.shutdown();
        }
        while(!this.threadPool.isShutdown()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * main task
     */
    private void deal(){
        this.init();
        for(int index=0; index<100; index++){
            System.out.println("[x] submit task number: "+index);
            this.threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("running...");
                }
            });
        }
        this.destroy();
    }

    public static void main(String[] args){
        ThreadExecutorsPoolDemo demo = new ThreadExecutorsPoolDemo();
//        demo.deal();
        log.info("just a test!");
    }
}
