package bgu.spl.mics.application.passiveObjects;

public class GadgetMissionDetail {
    private boolean answer;
    private int recievedInQtime;


    public GadgetMissionDetail(boolean answer, int recievedInQtime){
        this.answer=answer;
        this.recievedInQtime=recievedInQtime;
    }

    public boolean getAnswer() {
        return answer;
    }

    public int getRecievedInQtime() {
        return recievedInQtime;
    }
}
