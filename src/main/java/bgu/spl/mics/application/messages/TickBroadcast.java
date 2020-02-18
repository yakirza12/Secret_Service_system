package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast{

    private int currentTime;
    private String senderId;
    private int timeToTerminate;

    public TickBroadcast(String senderId, int currentTime,int timeToTerminate) {
        this.currentTime = currentTime;
        this.senderId=senderId;
        this.timeToTerminate =timeToTerminate;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public String getSenderId(){
        return senderId;
    }

    public int getTimeToTerminate() {
        return timeToTerminate;
    }
}
