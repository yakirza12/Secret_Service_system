package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.MissionReceivedEvent;
import bgu.spl.mics.application.messages.TeminateBrodcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A Publisher\Subscriber.
 * Holds a list of Info objects and sends them
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Intelligence extends Subscriber {

	private List<MissionInfo> myMissions;
	private int currentTimeTick;
	private String senderId;


	public Intelligence(String name, List<MissionInfo> myMissions, String senderId) {
		super(name); //constructor of subscriber registers on the messagebroker
		this.myMissions=myMissions;
		this.currentTimeTick=0;
		this.senderId=senderId;

	}



	@Override
	protected void initialize() {

		subscribeBroadcast(TickBroadcast.class, message -> {
			this.currentTimeTick = message.getCurrentTime();
			for (MissionInfo mission: this.myMissions){
				if(mission.getTimeIssued() == this.currentTimeTick) {
					Future<Boolean> future = this.getSimplePublisher().sendEvent(new MissionReceivedEvent(getName(), senderId, mission));
				}
			}
		});

		subscribeBroadcast(TeminateBrodcast.class, message -> {
			terminate();
		});

	}

}
