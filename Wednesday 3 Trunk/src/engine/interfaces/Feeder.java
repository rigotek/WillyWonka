package engine.interfaces;

import structures.AgentBin;
import structures.PartType;

public interface Feeder {

	//Constructor
	
	//public Feeder(Lane topLane, Lane bottomLane)
	
	//Messages
	/**
	 * This message will be called by the Gantry; will pass an Agent Bin to the Feeder
	 */
	public abstract void msgHereIsBin(AgentBin agentBin);

	/**
	 * This message will be called by the Lane, informing the Feeder how many spots are still available in the lane
	 * @param lane
	 * @param spotsOpen
	 */
	public abstract void msgLaneSpotsOpen(Lane lane, int spotsOpen);

	/**
	 * This message will be called by the LaneAgent, telling the feeder that it needs to
	 * switch the part that the feeder is sending to the lane to the specified part that
	 * was sent in as a parameter.
	 * @param partType
	 */
	public abstract void msgChangeToThisPart(Lane lane, PartType partType);
}
