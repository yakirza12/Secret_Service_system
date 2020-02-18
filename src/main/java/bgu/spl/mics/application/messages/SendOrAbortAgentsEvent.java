package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.util.List;

public class SendOrAbortAgentsEvent implements Event<Boolean>{
    // if send - true , but if abort - false
    private String senderName;
    private int senderId;
    private Boolean answer;
    private List<String> serialAgentsNumbers;
    private int time;

    public SendOrAbortAgentsEvent( String senderName, int senderId,Boolean answer , List<String> serialAgentsNumbers, int time){

        this.senderName = senderName;
        this.senderId=senderId;
        this.answer=answer;
        this.serialAgentsNumbers=serialAgentsNumbers;
        this.time=time;
    }

    public Boolean getAnswer() {
        return answer;
    }

    public List<String> getSerialAgentsNumbers() {
        return serialAgentsNumbers;
    }

    public int getTime() {
        return time;
    }

    public String getSenderName(){
        return senderName;
    }
}
