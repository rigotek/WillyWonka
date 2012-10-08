package engine.interfaces;

import java.util.ArrayList;

import structures.AgentPart;

public interface LaneCamera {

	//Message sent from Nest agent after it receives another part
	public abstract void msgIHaveAnotherPart(int numParts, Nest nest);

	//Take picture after timer has fired
	public abstract void msgTimerFired();

	//Message sent from Nest agent after picture taken
	public abstract void msgHereAreParts(ArrayList<AgentPart> parts, Nest nest);

	//Gui callback
	public abstract void msgDoneTakingPicture();

}