package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.GadgetMissionDetail;

public class GadgetAvailableEvent implements Event<GadgetMissionDetail>{
        private String gadget;
        private String senderName;
        private int senderId;


        public  GadgetAvailableEvent(String senderName, int senderId, String gadget) {
            this.senderName = senderName;
            this.senderId = senderId;
            this.gadget = gadget;

        }

        public String getGadget() {
            return gadget;
        }

        public String getSenderName(){
            return senderName;
        }
}
