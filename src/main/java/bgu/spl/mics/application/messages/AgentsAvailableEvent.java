package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.AgentMissionDetail;

import java.util.List;

public class AgentsAvailableEvent implements Event<AgentMissionDetail>{
    private List<String> serialAgentsNumbers;
    private String senderName;
    private int senderId;

    public AgentsAvailableEvent(List<String> serialAgentsNumbers, String senderName, int senderId){
        this.serialAgentsNumbers=serialAgentsNumbers;
        this.senderName = senderName;
        this.senderId=senderId;
    }

    public List<String> getSerialAgentsNumbers() {
        return serialAgentsNumbers;
    }

    public String getSenderName(){
        return senderName;
    }
}
