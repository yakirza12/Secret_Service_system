package bgu.spl.mics.application;

import bgu.spl.mics.Subscriber;
import bgu.spl.mics.application.passiveObjects.*;
import com.google.gson.*;
import bgu.spl.mics.application.subscribers.*;
import bgu.spl.mics.application.publishers.*;


import java.io.FileReader;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args) {
        try {
            //Json
            FileReader reader = new FileReader(args[0]);
            JsonElement jsonElement = new JsonParser().parse(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            //inventory
            JsonArray gadgets = jsonObject.get("inventory").getAsJsonArray();
            String[] gadgetsArray = new String[gadgets.size()];
            int i = 0;
            for (JsonElement element : gadgets) {
                gadgetsArray[i] = element.toString().substring(1, element.toString().length() - 1);
                i++;
            }
            Inventory.getInstance().load(gadgetsArray);

            //squad
            String name;
            String serialNumber;
            JsonArray squad = jsonObject.get("squad").getAsJsonArray();
            Agent[] agents = new Agent[squad.size()];
            int u = 0;
            for (JsonElement element : squad) {
                name = element.getAsJsonObject().get("name").toString();
                name = name.substring(1, name.length() - 1);
                serialNumber = element.getAsJsonObject().get("serialNumber").toString();
                serialNumber = serialNumber.substring(1, serialNumber.length() - 1);
                Agent addAgent = new Agent();
                addAgent.setName(name);
                addAgent.setSerialNumber(serialNumber);
                addAgent.release();//all agents are available when add them to the list.
                agents[u] = addAgent;
                u++;
            }
            Squad.getInstance().load(agents);


            //services
            int M =  jsonObject.get("services").getAsJsonObject().get("M").getAsInt();
            int time =  jsonObject.get("services").getAsJsonObject().get("time").getAsInt();
            int Moneypenny  = jsonObject.get("services").getAsJsonObject().get("Moneypenny").getAsInt();
            JsonArray intelligenceMissions =  jsonObject.get("services").getAsJsonObject().get("intelligence").getAsJsonArray();
            List<Intelligence> intelligencesList = new LinkedList<>();
            int SerialId = 1;
            String nameIntelligence = "Intelligence";
            for (JsonElement element1 : intelligenceMissions ) {
                JsonArray mission = element1.getAsJsonObject().get("missions").getAsJsonArray();
                List<MissionInfo> intelligenceMissionsList = new LinkedList<>();
                for (JsonElement element2 : mission) {
                    JsonArray serialAgentsNumbers = element2.getAsJsonObject().get("serialAgentsNumbers").getAsJsonArray();
                    List<String> serials = new LinkedList<>();
                    for (JsonElement element3 : serialAgentsNumbers) {
                        serials.add(element3.toString().substring(1, element3.toString().length() - 1));
                    }
                    int duration = element2.getAsJsonObject().get("duration").getAsInt();
                    int timeExpired = element2.getAsJsonObject().get("timeExpired").getAsInt();
                    int timeIssued = element2.getAsJsonObject().get("timeIssued").getAsInt();
                    String missionName = element2.getAsJsonObject().get("name").getAsString();
                    String gadget = element2.getAsJsonObject().get("gadget").getAsString();
                    MissionInfo missionCreated = new MissionInfo();
                    missionCreated.setGadget(gadget);
                    missionCreated.setDuration(duration);
                    missionCreated.setMissionName(missionName);
                    missionCreated.setSerialAgentsNumbers(serials);
                    missionCreated.setTimeExpired(timeExpired);
                    missionCreated.setTimeIssued(timeIssued);
                    intelligenceMissionsList.add(missionCreated);

                }
                String SerialIdString = Integer.toString(SerialId);
                String nameIntelligenceWithSerial = nameIntelligence + SerialIdString;
                Intelligence intelligence = new Intelligence(nameIntelligenceWithSerial, intelligenceMissionsList, SerialIdString);
                intelligencesList.add(intelligence);
                SerialId++;
            }


            List<Thread> threadsList = new LinkedList<>();


            for (int k = 1 ; k<= M; k++) {
                M newM = new M("M"+k, k);
                Thread newThread= new Thread(newM);
                threadsList.add(newThread);

            }

            for (int j = 1 ; j<= Moneypenny; j++) {
                Moneypenny newMoneyPenny= new Moneypenny("Mp"+j,j);
                Thread newThread= new Thread(newMoneyPenny);
                threadsList.add(newThread);

            }
            Q newQ =new Q("Q", 1);
            Thread newThreadQ= new Thread(newQ);
            threadsList.add(newThreadQ);



           for(Intelligence intelligence: intelligencesList){
                Thread newThread= new Thread(intelligence);
                threadsList.add(newThread);


            }



            TimeService newTimeService= new TimeService("TimeService", time);
            Thread newThreadTime= new Thread(newTimeService);
            threadsList.add(newThreadTime);



            for(Thread thread: threadsList) { //run all threads - note that timeService probably be the last one
                thread.start();
            }

            for(Thread thread: threadsList) { //wait for all threads to finish
                thread.join();
            }


            Inventory.getInstance().printToFile(args[1]);
            Diary.getInstance().printToFile(args[2]);



        } catch (Exception ignored) {

        }

    }
}
