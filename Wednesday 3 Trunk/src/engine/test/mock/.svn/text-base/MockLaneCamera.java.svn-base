package engine.test.mock;

import java.util.ArrayList;

import structures.AgentPart;
import engine.interfaces.LaneCamera;
import engine.interfaces.Nest;
import engine.test.EventLog;
import engine.test.LoggedEvent;
import engine.test.MockAgent;

public class MockLaneCamera extends MockAgent implements LaneCamera{

	public MockLaneCamera(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	public EventLog log = new EventLog();
	@Override
	public void msgIHaveAnotherPart(int numParts, Nest nest) {
		log.add(new LoggedEvent("Received message from Nest " + nest.toString() + " that it has a new part.  Resetting timer..."));		
	}
	@Override
	public void msgTimerFired() {
		log.add(new LoggedEvent("Timer has fired, taking picture of nests..."));
		
	}
	@Override
	public void msgHereAreParts(ArrayList<AgentPart> parts, Nest nest) {
		log.add(new LoggedEvent("Received parts list from Nest " + nest.toString() + " containing parts: " + parts.toString()));
		
	}
	@Override
	public void msgDoneTakingPicture() {
		log.add(new LoggedEvent("Received message from Gui...animation complete."));
		
	}

}
