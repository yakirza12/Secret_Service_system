package bgu.spl.mics.application.passiveObjects;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


/**
 *  That's where Q holds his gadget (e.g. an explosive pen was used in GoldenEye, a geiger counter in Dr. No, etc).
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Inventory {
	private List<String> gadgets;
	/**
     * Retrieves the single instance of this class.
     */

	private static class SingeltonHolder2{
		private static Inventory instance = new Inventory();
	}

	private Inventory(){
		gadgets = new LinkedList<>();
	}

	public static Inventory getInstance() {
		return SingeltonHolder2.instance;

	}

	/**
     * Initializes the inventory. This method adds all the items given to the gadget
     * inventory.
     * <p>
     * @param inventory 	Data structure containing all data necessary for initialization
     * 						of the inventory.
     */
	public void load(String[] inventory) {
		for(int j = 0;j<inventory.length;j++){
			gadgets.add(inventory[j]);
		}
	}
	
	/**
     * acquires a gadget and returns 'true' if it exists.
     * <p>
     * @param gadget 		Name of the gadget to check if available
     * @return 	‘false’ if the gadget is missing, and ‘true’ otherwise
     */
	public boolean getItem(String gadget){
		for(String item : gadgets){
			if(item.compareTo(gadget)==0) {
				gadgets.remove(item);
				return true;
			}
		}
		return false;
	}


	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<String> which is a
	 * list of all the of the gadgeds.
	 * This method is called by the main method in order to generate the output.
	 */
	public List<String> getGadgets(){
		return gadgets;
	}





	public void printToFile(String filename) {

		JsonArray obj = new JsonArray();
		for(String gadget : gadgets) {
			obj.add(gadget);
		}

		try(FileWriter file = new FileWriter(filename)) {
			file.write(obj.toString());

		} catch (IOException ex) {//error
			ex.printStackTrace();
		}
	}
}
