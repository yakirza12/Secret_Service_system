package bgu.spl.mics;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
@SuppressWarnings("unchecked")

/**
 * The {@link MessageBrokerImpl class is the implementation of the MessageBroker interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBrokerImpl implements MessageBroker {
	private Map<Class<? extends Message>, BlockingQueue> messageMap; //register to type of message - round robin
	private Map<Subscriber,BlockingQueue> subscriberMap; //missions
	private Map<Message,Future> futureMap;
	private Semaphore sem;

	private static class SingletonHolder{
		private static MessageBrokerImpl instance = new MessageBrokerImpl();
	}

	//need to IMPL
	private MessageBrokerImpl(){
		messageMap = new ConcurrentHashMap<Class<? extends Message>, BlockingQueue>();
		subscriberMap = new ConcurrentHashMap<Subscriber,BlockingQueue>();
		futureMap = new ConcurrentHashMap<Message,Future>();
		sem= new Semaphore(1);
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static MessageBroker getInstance() {
		return SingletonHolder.instance;
	}



	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, Subscriber m) {
		if(!messageMap.containsKey(type)) { //just for not creating a queue for every event
			BlockingQueue<Subscriber> subscriberBlockingQueue = new LinkedBlockingQueue<Subscriber>();
			messageMap.putIfAbsent(type, subscriberBlockingQueue);
		}
		BlockingQueue<Subscriber> queueMessage= messageMap.get(type);
		try {
			queueMessage.put(m);
		} catch (InterruptedException ignored) {

		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, Subscriber m) {
		if(!messageMap.containsKey(type)) { //just for not creating a queue for every broadcast
			BlockingQueue<Subscriber> subscriberBlockingQueue = new LinkedBlockingQueue<Subscriber>();
			messageMap.putIfAbsent(type, subscriberBlockingQueue);
		}

		BlockingQueue<Subscriber> queueMessage= messageMap.get(type);
		try {
			queueMessage.put(m);
		} catch (InterruptedException ignored) {

		}

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future<T> newF=futureMap.get(e);
		newF.resolve(result);
	}


	/**
	 * Adds the {@link Broadcast} {@code b} to the message queues of all the
	 * Subscriber subscribed to {@code b.getClass()}.
	 * <p>
	 * @param b 	The message to added to the queues.
	 */
	@Override
	public void sendBroadcast(Broadcast b) {
		if(!messageMap.containsKey(b.getClass())) { //just for not creating a queue for every broadcast
			BlockingQueue<Subscriber> subscriberBlockingQueue = new LinkedBlockingQueue<Subscriber>();
			messageMap.putIfAbsent(b.getClass(), subscriberBlockingQueue);
		}

		BlockingQueue<Subscriber> queueMessage= messageMap.get(b.getClass()); //get queue

		for (Subscriber subscriberRegistered : queueMessage) {
			try {
				subscriberMap.get(subscriberRegistered).put(b); // add the broadcast to the subscribers
			} catch (InterruptedException ignored) {

			}
		}

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e)  {
		if(!messageMap.containsKey(e.getClass())) { //just for not creating a queue for every event
			BlockingQueue<Subscriber> subscriberBlockingQueue = new LinkedBlockingQueue<Subscriber>();
			messageMap.putIfAbsent(e.getClass(), subscriberBlockingQueue);
		}
		Future<T> newFuture = null;
		try {
			sem.acquire();
			BlockingQueue<Subscriber> queueMessage = messageMap.get(e.getClass()); //get queue of subscribers to event<T>
			if (!queueMessage.isEmpty()) {
				Subscriber tempGetSubscriber = queueMessage.take(); //remove first subscriber head of the queue
				newFuture = new Future<T>();
				futureMap.putIfAbsent(e, newFuture);
				subscriberMap.get(tempGetSubscriber).put(e);
				queueMessage.put(tempGetSubscriber); //add again the subscriber to end of the queue - round robin

			}
			sem.release();
		}
		catch(InterruptedException ignored){

		}
		return newFuture;
	}

	@Override
	public void register(Subscriber m) {
		BlockingQueue<Message> messageBlockingQueue= new LinkedBlockingQueue<Message>();
		subscriberMap.put(m,messageBlockingQueue);
	}

	@Override
	public void unregister(Subscriber m) {
		if(subscriberMap.containsKey(m)) {
			for (BlockingQueue messageQueue : messageMap.values()) {
				if (messageQueue.contains(m)) {
					messageQueue.remove(m);
				}
			}
			subscriberMap.remove(m); //delete the subscriber's queue
		}
	}

	@Override
	public Message awaitMessage(Subscriber m) throws InterruptedException {
		if(!subscriberMap.containsKey(m)){
			throw new IllegalStateException("The subscriber was never registered.");
		}


			BlockingQueue<Message> mQueue = subscriberMap.get(m);
			return mQueue.take();


	}

	

}
