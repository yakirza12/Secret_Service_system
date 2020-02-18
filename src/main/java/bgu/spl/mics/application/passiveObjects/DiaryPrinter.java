package bgu.spl.mics.application.passiveObjects;

import java.util.List;

public class DiaryPrinter {
    private List<Report> reports;
    private int total;

    DiaryPrinter(List<Report> reports,int total){
        this.reports = reports;
        this.total = total;
    }

}
