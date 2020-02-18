package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.MissionInfo;

public class MissionReceivedEvent implements Event<Boolean>{

    private String senderName;
    private String senderId;
    private MissionInfo mission;


    public MissionReceivedEvent(String senderName, String senderId, MissionInfo mission) {
        this.senderName = senderName;
        this.senderId=senderId;
        this.mission=mission;

    }

    public String getSenderName() {
        return senderName;
    }

    public MissionInfo getMission(){
        return mission;
    }


}
