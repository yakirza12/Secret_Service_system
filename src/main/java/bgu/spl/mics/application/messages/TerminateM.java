package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.Event;

public class TerminateM  implements Broadcast {

Boolean terminatedM ;

public TerminateM(Boolean terminatedM){
    this.terminatedM = true;

}

    public Boolean getTerminatedM() {
        return terminatedM;
    }


}
