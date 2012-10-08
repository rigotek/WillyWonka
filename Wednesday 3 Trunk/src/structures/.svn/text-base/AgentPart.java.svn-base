package structures;

import gui.holdable.GuiCandy;

/**
 * This represents the Agent View of a part.
 * 
 * @author Pierre Tasci
 */
public class AgentPart {
	//Enum telling what type of part it is. CS 200 will give it more descriptive types
	public PartType type;
	//boolean to determine if the part is faulty or not
	public boolean goodpart;
	//Reference to the gui part linked to this agentpart
	public GuiCandy partgui;
	//enum of state
	public KitPlacement position;
	
	public AgentPart(PartType t) {
		type = t;
		int random = (int)(Math.random()*100);
		//goodpart = true;
		if(random > 20)
			goodpart = true;
		else 
			goodpart = false;
		position = position.NOPOS;
	}
	
	public boolean getFaulty(){
		return !goodpart;
	}
	
	public void setGuiCandy(GuiCandy candy) {
		this.partgui = candy;
	}
}
