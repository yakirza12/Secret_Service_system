package bgu.spl.mics.application.passiveObjects;

import java.util.List;

public class AgentMissionDetail { //Boolean answer, idMoneyPenny, listAgentsName
    private boolean answer;
    private int idMoneyPenny;
    private List<String> listAgentsNames;

    public AgentMissionDetail(boolean answer, int idMoneyPenny, List<String> listAgentsNames){
        this.answer=answer;
        this.idMoneyPenny=idMoneyPenny;
        this.listAgentsNames=listAgentsNames;
    }

    public boolean getAnswer(){
        return answer;
    }

    public int getIdMoneyPenny() {
        return idMoneyPenny;
    }

    public List<String> getListAgentsNames() {
        return listAgentsNames;
    }
}
