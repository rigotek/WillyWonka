package structures;

import gui.holdable.GuiBin;
import gui.holdable.GuiCandy;
import gui.locationsimg.Locations;

import java.util.LinkedList;
import java.util.List;

/**
 * Agent interpretation of a bin.
 * 
 * @author Pierre Tasci
 *
 */
public class AgentBin {
	private List<AgentPart> parts;
	private PartType type;
	//number of parts left in the bin
	private int numparts;
	//tells how many parts this bin can hold in total
	final static int capacity = 12;
	//Reference to the linked gui bin
	private GuiBin guibin;
	
	public AgentBin() {
		parts = new LinkedList<AgentPart>();
		numparts = 0;
	}
	
	/**
	 * Constructor to create a new bin and initialize it with some parts that you pass in.
	 * 
	 * @param addparts	List of parts passed in to initialize the parts list to
	 * @param addtype	The type of part that is being passed in.
	 */
	public AgentBin(List<AgentPart> addparts, PartType addtype) {
		while(addparts.size() > capacity) 
			addparts.remove(0);
		parts = addparts;
		numparts = addparts.size();
		type = addtype;
	}
	
	public void setGuiBin(GuiBin gb) {
		guibin = gb;
	}
	/**
	 * Getter for the GuiBin
	 * @return
	 */
	public GuiBin guibin() {
		return guibin;
	}
	
	/**
	 * Allows any agent (should only be gantry) to refill a bin with new parts. 
	 * 
	 * @param newtype	Type of part to fill bin with
	 * @param quantity	Number of parts to put in the bin.
	 */
	public List<AgentPart> refillParts(PartType newtype, int quantity) {
		numparts = quantity;
		type = newtype;
		
		return parts;
	}
	
	public AgentPart getNextPart() {
		AgentPart part = new AgentPart(this.type);
		GuiCandy candy = new GuiCandy(guibin.getCurrentX(),guibin.getCurrentY()+ (guibin.getHeight() / 2), this.type);
		parts.add(part);
		part.setGuiCandy(candy);
		candy.setAgentPart(part);
		numparts--;
		return part;
	}
	
	/**
	 * Returns a reference to the first element in the parts list and removes it as well
	 * @return
	 */
	public AgentPart getFirstPart() {
		numparts--;
		return ((LinkedList<AgentPart>) parts).poll();
	}
	
	/**
	 * Peeks at the first element in the parts list without removing it
	 * @return
	 */
	public AgentPart readFirstPart() {
		return ((LinkedList<AgentPart>) parts).peek();
	}
	
	
	
	/**
	 * Getter for the number of parts currently in the bin
	 * @return
	 */
	public int num() {
		return numparts;
	}
	
	/**
	 * Getter for the type parts this bin holds
	 * @return
	 */
	public PartType type() {
		return type;
	}
	
	/**
	 * Getter for the entire list of parts
	 */
	public List<AgentPart> parts() {
		return parts;
	}
}
