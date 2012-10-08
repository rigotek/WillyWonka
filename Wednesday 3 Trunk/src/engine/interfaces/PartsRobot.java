package engine.interfaces;

import java.util.List;

import structures.AgentKit;
import structures.AgentPart;

public interface PartsRobot {

	//---------------MESSAGES---------------------------
	/**
	 * Message from the kitting stand to the parts robot telling him
	 * which kits he needs to make so that the parts robot can 
	 * optimize his time usage and which kits he picks up
	 * 
	 * @param kit The Kit that the stand needs to fulfill
	 */
	public abstract void msgINeedThese(AgentKit kit, KitStand kitstand);

	/**
	 * Message from the lane camera to the robot that he has usable parts
	 * that the robot might want to know about.
	 * 
	 * @param gp A list of parts that the nest has available that I don't know about already
	 * @param nt The nest to which these parts belong
	 */
	public abstract void msgIHaveGoodParts(List<AgentPart> gp, Nest nt, LaneCamera camera);

	/**
	 * Message from the gui that the animation to go retrieve the parts from the
	 * nests has completed.
	 */
	public abstract void msgPartsReceived();

	/**
	 * Message from the gui that the animation to send the parts and dump it in the 
	 * kit on the kit stand has completed.
	 */
	public abstract void msgPartsSent();

}