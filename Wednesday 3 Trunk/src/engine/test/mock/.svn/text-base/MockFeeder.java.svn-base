package engine.test.mock;

import structures.AgentBin;
import structures.PartType;
import engine.interfaces.Feeder;
import engine.interfaces.Lane;
import engine.test.EventLog;
import engine.test.MockAgent;
import engine.test.LoggedEvent;

public class MockFeeder extends MockAgent implements Feeder {

	public EventLog log = new EventLog();
	
	public MockFeeder(String name) {
		super(name);
	}

	@Override
	public void msgHereIsBin(AgentBin b) {
		log.add(new LoggedEvent(
				"Message Received: Feeder was sent and AgentBin from the Gantry."));
	}

	@Override
	public void msgLaneSpotsOpen(Lane lane, int spotsOpen) {
		log.add(new LoggedEvent(
				"Message Received: Feeder was told that there were " + spotsOpen + " in the lane."));
	}

	@Override
	public void msgChangeToThisPart(Lane lane, PartType partType) {
		log.add(new LoggedEvent(
				"Message received: Feeder received the message to change it's part type to "
				+ "a new type." ));
		
	}
	
	
	
	
}