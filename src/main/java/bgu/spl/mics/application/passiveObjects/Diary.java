package bgu.spl.mics.application.passiveObjects;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Passive object representing the diary where all reports are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Diary {
	private List<Report> reports;
	private int total;
	private AtomicInteger incrementTotal;

	private static class SingletonHolder {
		private static Diary instance = new Diary();
	}


	private Diary(){
		reports = new LinkedList<Report>();
		total=0;
		incrementTotal= new AtomicInteger(0);
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Diary getInstance() {
		return Diary.SingletonHolder.instance;
	}

	public List<Report> getReports() {
		return reports;
	}

	/**
	 * adds a report to the diary
	 * @param reportToAdd - the report to add
	 */
	public synchronized void addReport(Report reportToAdd){
		reports.add(reportToAdd);
	}

	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<Report> which is a
	 * List of all the reports in the diary.
	 * This method is called by the main method in order to generate the output.
	 */
	public void printToFile(String filename){

		try(FileWriter file = new FileWriter(filename)) {
            DiaryPrinter diaryPrinter = new DiaryPrinter(this.getReports(),this.getTotal());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(diaryPrinter);
            file.write(json);
		} catch (IOException ex) {//error
			ex.printStackTrace();
		}
	}

	/**
	 * Gets the total number of received missions (executed / aborted) be all the M-instances.
	 * @return the total number of received missions (executed / aborted) be all the M-instances.
	 */
	public int getTotal(){

		return incrementTotal.get();
	}

	/**
	 * Increments the total number of received missions by 1
	 */
	public void incrementTotal(){
		do{
			total= incrementTotal.get();
		}
		while(!incrementTotal.compareAndSet(total,total+1));
	}
}
