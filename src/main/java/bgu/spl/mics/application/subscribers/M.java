package bgu.spl.mics.application.subscribers;

import bgu.spl.mics.Future;
import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.passiveObjects.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * M handles ReadyEvent - fills a report and sends agents to mission.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class M extends Subscriber {
	private Diary diary;
	private int currentTimeTick;
	private int senderId;

	public M(String name, int senderId) {
		super(name);
		this.senderId=senderId;
		diary = Diary.getInstance();

	}

	@Override
	protected void initialize() {

		subscribeBroadcast(TickBroadcast.class, message -> {
			currentTimeTick = message.getCurrentTime();

		});

		subscribeBroadcast(TerminateM.class, message -> {
			terminate();
			getSimplePublisher().sendBroadcast(new TeminateBrodcast());
		});



		subscribeEvent(MissionReceivedEvent.class, message -> {
			Future<AgentMissionDetail> futureAgents= getSimplePublisher().sendEvent(new AgentsAvailableEvent(message.getMission().getSerialAgentsNumbers(),getName(),this.senderId));
			Future<GadgetMissionDetail> futureGadget;
			Future<Boolean> futureSendOrAbort;


				if (futureAgents != null && futureAgents.get() != null && futureAgents.get().getAnswer() == true) {
					futureGadget = getSimplePublisher().sendEvent(new GadgetAvailableEvent(getName(), this.senderId, message.getMission().getGadget()));
					if (futureGadget != null && futureGadget.get() != null && futureGadget.get().getAnswer() == true && currentTimeTick <= message.getMission().getTimeExpired()) {
						futureSendOrAbort = getSimplePublisher().sendEvent(new SendOrAbortAgentsEvent(getName(), this.senderId, true, message.getMission().getSerialAgentsNumbers(), message.getMission().getDuration()));
						Report newReport = new Report();
						writeReport(newReport, message.getMission(), futureAgents.get().getIdMoneyPenny(), futureAgents.get().getListAgentsNames(), futureGadget.get().getRecievedInQtime());
						diary.addReport(newReport);

					} else {
						futureSendOrAbort = getSimplePublisher().sendEvent(new SendOrAbortAgentsEvent(getName(), this.senderId, false, message.getMission().getSerialAgentsNumbers(), 0));
					}
				} else {
					futureSendOrAbort = getSimplePublisher().sendEvent(new SendOrAbortAgentsEvent(getName(), this.senderId, false, message.getMission().getSerialAgentsNumbers(), 0));
				}

			diary.incrementTotal();

			complete(message,true);
		});




		
	}

	private void writeReport(Report newReport, MissionInfo mission, int MPserialnumber , List<String> agentsNames, int Qtime){
		newReport.setAgentsNames(agentsNames);
		newReport.setAgentsSerialNumbersNumber(mission.getSerialAgentsNumbers());
		newReport.setGadgetName(mission.getGadget());
		newReport.setM(senderId);
		newReport.setMissionName(mission.getMissionName());
		newReport.setMoneypenny(MPserialnumber);
		newReport.setQTime(Qtime);
		newReport.setTimeCreated(currentTimeTick);
		newReport.setTimeIssued(mission.getTimeIssued());
	}

}
