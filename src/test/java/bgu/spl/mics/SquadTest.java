package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Squad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class SquadTest {
    private Squad mySquad;
    private Agent[] agents;
    @BeforeEach
    public void setUp(){
        mySquad=Squad.getInstance();
        Agent OO=new Agent();
        OO.setName("bond");
        OO.setSerialNumber("007");
        agents = new Agent[1];
        agents[0]=OO;
        mySquad.load(agents);
    }

        @Test
    public void testLoad(){


    }


    @Test
    public void testReleaseAgents(){
        List<String> serials = new ArrayList<>();
        serials.add("007");

    }

    @Test
    public void testSendAgents(){

        List<String> serials = new ArrayList<>();
        serials.add("007");
        int timetick = 5;
        long pretime=java.lang.System.currentTimeMillis();
        mySquad.sendAgents(serials,timetick);
        assertEquals(pretime+timetick*1000,java.lang.System.currentTimeMillis()); //maybe should be 100

    }

    @Test
    public void testGetAgents(){
        List<String> serials = new ArrayList<>();
        serials.add("007");
         }

    @Test
    public void testGetAgentsNames(){
        List<String> serials = new ArrayList<>();
        serials.add("007");
        List<String> names = new ArrayList<>();
        serials.add("bond");
        assertLinesMatch(mySquad.getAgentsNames(serials),names);
    }

}