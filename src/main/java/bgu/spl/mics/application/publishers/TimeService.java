package bgu.spl.mics.application.publishers;

import bgu.spl.mics.Publisher;
import bgu.spl.mics.application.messages.TerminateM;
import bgu.spl.mics.application.messages.TickBroadcast;

import java.util.concurrent.TimeUnit;

/**
 * TimeService is the global system timer There is only one instance of this Publisher.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other subscribers about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends Publisher {
	private int duration; //lasttimetick
	private int currentTime = 0 ;
	String senderId;
	private int timeToTerminate;




	public TimeService(String senderId, int duration) {
		super("Change_This_Name");
		this.senderId=senderId;
		this.duration=duration;
		this.timeToTerminate=duration;
	}

	@Override
	protected void initialize() {
		getSimplePublisher().sendBroadcast(new TickBroadcast(senderId,currentTime,timeToTerminate));
		
	}

	@Override
	public void run() {
		try {
			while (duration != 0) {
				++currentTime;
				initialize();
				--duration;
				Thread.sleep(100);//ToDO add to yuval
			}

			getSimplePublisher().sendBroadcast(new TerminateM(true)); // ent evet toM to terminated.
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}


}
