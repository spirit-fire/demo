package com.spirit.demo.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created by lgx on 2017/4/9.
 */
public class AkkaDemoClient extends UntypedActor {

    @Override
    public void preStart(){
        final ActorRef client = this.getContext().actorOf(Props.create(AkkaDermoWorker.class), "clinet");
        client.tell("Hello, world!", this.getSelf());
    }

    @Override
    public void onReceive(Object o) throws Throwable {
        ActorRef actor = null;
        if(o instanceof String){
            System.out.println("[xx]: Client reveive msg... "+o);
            this.getContext().stop(this.getSelf());

        }else{
            this.unhandled(o);
        }
    }

    public static void main(String[] args){
        akka.Main.main(new String[]{AkkaDemoClient.class.getName()});
    }
}
