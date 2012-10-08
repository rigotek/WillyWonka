package engine.interfaces;

import java.util.List;

import structures.AgentPart;
import structures.PartType;

public interface Nest {
	
	/**
	 * This message is sent by the lane when a part has reached the end of the lane and the nest is not full.
	 * @param p
	 */
	public abstract void msgNextPart(AgentPart p);
	
	/**
	 * This message is sent by the lane camera when it wants to know what parts are in the nest right now.
	 */
	public abstract void msgTakingPicture();
	
	
	/**
	 * This message is sent by the parts robot after it has decided what parts to take out of the nest so the nest agent
can remove them.
	 * @param pts
	 */
	public abstract  void msgINeedTheseParts(List<AgentPart> pts);
	
	/**
	 * This message is sent by the parts robot when it needs more of a different part type, so this
	 * message will trickle back to the lane, feeder, and gantry.
	 * @param type
	 */
	
	public abstract void msgChangeToThisPart(PartType type);	
	
	public abstract void msgThesePartsAreBad(List<AgentPart> pts);
	
	public abstract void msgPurgeTheNest();
}