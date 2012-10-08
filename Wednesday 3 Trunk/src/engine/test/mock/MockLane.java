package engine.test.mock;

import java.util.List;

import structures.*;
import engine.interfaces.*;
import engine.test.*;

public class MockLane extends MockAgent implements Lane {

	public MockLane(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public EventLog log = new EventLog();

	@Override
	public void msgRequestNumberOfOpenSpots() {
		log.add(new LoggedEvent (
				"Received message msgRequestNumberOfOpenSpots from feeder to see how many lane spots are available"
				));
		
	}

	@Override
	public void msgPartForLane(AgentPart p) {
		log.add(new LoggedEvent (
				"Received message msgPartForLane from feeder that gave the lane part "
				+ p.toString()));
		
	}

	@Override
	public void msgIAmNotFull(Boolean b) {
		log.add(new LoggedEvent (
				"Received message msgIAmNotFull from nest to tell the lane that the nest is current not full: " + b.toString()
				));
		
	}

	@Override
	public void msgChangeToThisPart(PartType type) {
		log.add((new LoggedEvent (
				"Received message msgINeedThisPart from the nest to tell the lane that it should change to part type " 
				+ type)));
		
	}
	
}