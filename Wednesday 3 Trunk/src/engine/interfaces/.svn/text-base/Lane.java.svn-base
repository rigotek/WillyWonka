package engine.interfaces;

import structures.AgentPart;
import structures.PartType;

public interface Lane {
	
	/**
	 * This message is sent by the feeder when it wants to know how many spots are open on the lane.
	 */
	public abstract void msgRequestNumberOfOpenSpots();
	
	/**
	 * This message is sent by the feeder whenever it contains parts to give to the lane and it knows the lane is not
	yet full. 
	 * @param p
	 */
	public abstract void msgPartForLane(AgentPart p);
	
	/**
	 * This message is sent by the nest each time the nest's scheduler runs so the lane knows whether it can continue
to give parts to the nest or not.
	 * @param b
	 */
	public abstract void msgIAmNotFull(Boolean b);
	
	/**
	 * This message is sent by the nest when the part type needs to change, and will trickle back
	 * to the feeder and gantry.
	 * @param type
	 */
	public abstract void msgChangeToThisPart(PartType type);
	
}