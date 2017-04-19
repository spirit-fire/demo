package com.spirit.demo.akka;

import akka.actor.UntypedActor;

/**
 * Created by lgx on 2017/4/9.
 */
public class AkkaDermoWorker extends UntypedActor{
    @Override
    public void onReceive(Object o) throws Throwable {
        System.out.println("[x]: Worker receive msg...");
        if(o instanceof  String) {
            System.out.println(o);
            getSender().tell(this.doWork(), getSelf());
        }
    }

    private String doWork(){
        return "[x]: Worker finished dealing with msg...";
    }
}
