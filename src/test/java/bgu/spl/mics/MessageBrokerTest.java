package bgu.spl.mics;

import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import bgu.spl.mics.example.subscribers.ExampleBroadcastSubscriber;
import bgu.spl.mics.example.subscribers.ExampleEventHandlerSubscriber;
import bgu.spl.mics.MessageBrokerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MessageBrokerTest {
    private ExampleEvent testevent;
    private MessageBroker mBroker;
    Class eventClassTYPE;

    @BeforeEach
    public void setUp(){
        //message broker
        mBroker=MessageBrokerImpl.getInstance();
        //event
        testevent=new ExampleEvent("testSender");
        Class eventClassTYPE= ExampleEvent.class;
    }


    @Test
    public void testRegister(){
        ExampleEventHandlerSubscriber newSub= new ExampleEventHandlerSubscriber("test Subscriber", new String[]{"0", "1"});
        mBroker.register(newSub);

    }

    @Test
    public void testUnregister(){
        ExampleEventHandlerSubscriber newSub= new ExampleEventHandlerSubscriber("test Subscriber", new String[]{"0", "1"});
        mBroker.register(newSub);
        mBroker.unregister(newSub);

    }

    @Test
    public void testSubscribeEvent() throws InterruptedException {
        ExampleEventHandlerSubscriber newSub= new ExampleEventHandlerSubscriber("test Subscriber", new String[]{"0", "1"});
        mBroker.register(newSub);
        mBroker.subscribeEvent (eventClassTYPE, newSub);
    }

    @Test
    public void testSubscribeBroadcast() throws InterruptedException {
        ExampleEventHandlerSubscriber newSub= new ExampleEventHandlerSubscriber("test Subscriber", new String[]{"0", "1"});
        mBroker.register(newSub);
        Class broadcastClassTYPE= ExampleBroadcastSubscriber.class;
        mBroker.subscribeBroadcast (broadcastClassTYPE, newSub);

    }


    @Test
    public void testSendBroadcast() throws InterruptedException {
        //broadcast sub
        ExampleBroadcastSubscriber newSubBroadcat= new ExampleBroadcastSubscriber("test Subscriber", new String[]{"0", "1"});
        mBroker.register(newSubBroadcat);
        Class broadcastClassTYPE= ExampleBroadcastSubscriber.class;
        mBroker.subscribeBroadcast (broadcastClassTYPE, newSubBroadcat);
        //this one will not sub to the broadcast type
        ExampleEventHandlerSubscriber newSub= new ExampleEventHandlerSubscriber("test Subscriber", new String[]{"0", "1"});
        mBroker.register(newSub);
        mBroker.subscribeEvent (eventClassTYPE, newSub);

        Broadcast newTestBroadcast= new ExampleBroadcast("007");
        mBroker.sendBroadcast(newTestBroadcast);

    }


    @Test
    public void testSendEvent() throws InterruptedException {
        ExampleEventHandlerSubscriber newSub= new ExampleEventHandlerSubscriber("test Subscriber", new String[]{"0", "1"});
        mBroker.register(newSub);
        mBroker.subscribeEvent (eventClassTYPE, newSub);
    }

    @Test
    public void testComplete() throws InterruptedException {
    //(Event < T > e, T result);
        ExampleEventHandlerSubscriber newSub= new ExampleEventHandlerSubscriber("test Subscriber", new String[]{"0", "1"});
        mBroker.register(newSub);
        mBroker.subscribeEvent (eventClassTYPE, newSub);
        Future<String> mission= mBroker.sendEvent(testevent);
        String oldResult= "done";
        mBroker.complete(testevent,oldResult);
        assertEquals(mission.get(),oldResult);
    }

    @Test
    public void testAwaitMessage() throws InterruptedException {
        ExampleEventHandlerSubscriber newSub= new ExampleEventHandlerSubscriber("test Subscriber", new String[]{"0", "1"});
        mBroker.register(newSub);
        mBroker.subscribeEvent (eventClassTYPE, newSub);
        Future<String> mission= mBroker.sendEvent(testevent);
        Message eventMessageThatReturned;
        eventMessageThatReturned = mBroker.awaitMessage(newSub);
    }

}