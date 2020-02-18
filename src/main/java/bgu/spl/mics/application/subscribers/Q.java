package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.AgentsAvailableEvent;
import bgu.spl.mics.application.messages.GadgetAvailableEvent;
import bgu.spl.mics.application.messages.TeminateBrodcast;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.GadgetMissionDetail;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Squad;

import java.util.List;

/**
 * Q is the only Subscriber\Publisher that has access to the {@link bgu.spl.mics.application.passiveObjects.Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class Q extends Subscriber {

	private int serialID;
	private Inventory inventory;
	private int currentTimeTick;

	public Q(String name, int serialID) {
		super(name);
		this.serialID=serialID;
		this.inventory = Inventory.getInstance();
		this.currentTimeTick=0;
	}

	@Override
	protected void initialize() {

		subscribeBroadcast(TickBroadcast.class, message -> {
			currentTimeTick = message.getCurrentTime();
			if(currentTimeTick >= message.getTimeToTerminate()){
				terminate();
			}
		});



		subscribeEvent(GadgetAvailableEvent.class, message -> {
			Boolean gadgetAvailable = inventory.getItem(message.getGadget());
			GadgetMissionDetail newDetail = new GadgetMissionDetail(gadgetAvailable,currentTimeTick);
			complete(message,newDetail);
		});
		
	}

}
