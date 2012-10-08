package structures;

import gui.holdable.GuiCandy;
import gui.holdable.GuiKit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Class to represent the configuration and background information of what represents a kit.
 * 
 * @author Pierre Tasci
 *
 */
public class AgentKit {
	//The configuration used by this kit
	public final Configuration config;
	//Position on the kitstand
	public KitPosition position;
	public List<AgentPart> parts;
	public Map<PartType, Integer> quantities;
	public Map<PartType, Integer> kitParts;//KitCameraAgent will used this. This will keep track of parts being added.
	public boolean complete = false;
	public GuiKit guikit;
	public String name;
	public int id;
	
	public AgentKit(Configuration cf, KitPosition pos, String name) {
		config = cf;
		position = pos;
		parts = Collections.synchronizedList(new LinkedList<AgentPart>());
		quantities = new HashMap<PartType,Integer>();
		kitParts = new HashMap<PartType,Integer>();
		for(PartType p : config.configuration().keySet()) {
			quantities.put(p, config.configuration().get(p));
			kitParts.put(p, 0);//Kit will have zero parts until added by the parts robot.
		}
		complete = false;
		this.name = name;
	}
	
	public void setGuiKit(GuiKit kit) {
		guikit = kit;
	}
	
	public AgentKit(Configuration cf, int id) {
		config = cf;
		position = null;
		parts = new LinkedList<AgentPart>();
		quantities = new HashMap<PartType,Integer>();
		kitParts = new HashMap<PartType,Integer>();
		for(PartType p : config.types) {
			quantities.put(p, config.getType(p));
			kitParts.put(p, 0);//Kit will have zero parts until added by the parts robot.
		}
		complete = false;
		this.id = id;
	}
	
	public AgentKit(Configuration cf) {
		config = cf;
		position = null;
		parts = new LinkedList<AgentPart>();
		quantities = new HashMap<PartType,Integer>();
		kitParts = new HashMap<PartType,Integer>();
		for(PartType p : config.types) {
			quantities.put(p, config.getType(p));
			kitParts.put(p, 0);//Kit will have zero parts until added by the parts robot.
		}
		complete = false;
	}
	
	/**
	 * This is a very important function. Not only does this add new parts into the kit but it also checks to see 
	 * if the kit is now completed.
	 * 
	 * @param newparts
	 * @return
	 */
	public void dumpParts(List<AgentPart> newparts) {
		System.out.println("Dumping: I need " + quantities.get(PartType.TYPE1) + " of type 1");
		
		for( AgentPart p : newparts) {
			parts.add(p);
			if(quantities.containsKey(p.type)) {
				quantities.put(p.type, quantities.get(p.type) - 1);
				kitParts.put(p.type, kitParts.get(p.type) + 1);//Updating the amount for a specific type 
			}
			else {
				kitParts.put(p.type, 1);//If no type is found, add one.
				quantities.put(p.type, 1);
			}
		}
		
		System.out.println("Done Dumping: I need " + quantities.get(PartType.TYPE1) + " of type 1");
		
		complete = true;
		/*if(config.remaining(PartType.TYPE1, quantities.get(PartType.TYPE1)) != 0) {
			return false;
		}
		else if(config.remaining(PartType.TYPE2, quantities.get(PartType.TYPE2)) != 0) {
			return false;
		}
		else if(config.remaining(PartType.TYPE3, quantities.get(PartType.TYPE3)) != 0) {
			return false;
		}
		else if(config.remaining(PartType.TYPE4, quantities.get(PartType.TYPE4)) != 0) {
			return false;
		}
		else if(config.remaining(PartType.TYPE5, quantities.get(PartType.TYPE5)) != 0) {
			return false;
		}
		else if(config.remaining(PartType.TYPE6, quantities.get(PartType.TYPE6)) != 0) {
			return false;
		}
		else if(config.remaining(PartType.TYPE7, quantities.get(PartType.TYPE7)) != 0) {
			return false;
		}
		else if(config.remaining(PartType.TYPE8, quantities.get(PartType.TYPE8)) != 0) {
			return false;
		}
		else {
			complete = true;
			return true;
		}*/
		for(PartType p:quantities.keySet()) {
			if(quantities.get(p)>0)
				complete = false;
		}
		/*if(complete)
			return true;
		else
			return false;*/	
	}
	
	public ArrayList<AgentPart> fillParts() {
		ArrayList<AgentPart> newparts = new ArrayList<AgentPart>();
		ArrayList<GuiCandy> candylist = new ArrayList<GuiCandy>();
		for(PartType p : quantities.keySet()) {
			for(int i = 0; i < quantities.get(p); i++) {
				AgentPart part = new AgentPart(p);
				GuiCandy candy = new GuiCandy(guikit.getCurrentX(), guikit.getCurrentY(), p);
				part.setGuiCandy(candy);
				candylist.add(candy);
				candy.setAgentPart(part);
				newparts.add(part);
			}
		}
		parts = newparts;
		guikit.fillKit(candylist);
		System.out.println("I am filling with "+candylist.size() + " candies.");
		return newparts;
	}
	
	public Boolean complete(List<AgentPart> part) {
		HashMap<PartType,Integer> test = new HashMap<PartType, Integer>();
		for(AgentPart p : part) {
			if(test.containsKey(p.type)) {
				test.put(p.type, test.get(p.type) + 1);
			}
			else {
				test.put(p.type, 1);
			}
		}
		
		if(test.equals(config)) {
			return true;
		}
		else {
			return false;
		}
		
		
		/*for(AgentPart p : part) {
			if(quantities.containsKey(p.type) && quantities.get(p.type) != 0) {
				quantities.put(p.type, quantities.get(p.type) - 1);
			}
		}
		
		for(PartType p:quantities.keySet()) {
			if(quantities.get(p)>0)
				complete = false;
		}
		if(complete)
			return true;
		else
			return false;*/
	}
	
	/**
	 * Tells how many of that type of part the kit still needs
	 * @param type
	 * @return
	 */
	public int needed(PartType type) {
		return config.remaining(type, quantities.get(type));
	}
	
	public void setPosition(KitPosition pos){
		position = pos;
	}
}
