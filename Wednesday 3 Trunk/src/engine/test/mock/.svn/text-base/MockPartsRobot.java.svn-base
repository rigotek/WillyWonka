package engine.test.mock;

import java.util.List;

import structures.*;
import engine.interfaces.*;
import engine.test.*;

public class MockPartsRobot extends MockAgent implements PartsRobot {

	public MockPartsRobot(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public EventLog log = new EventLog();

	@Override
	public void msgINeedThese(AgentKit kit, KitStand kitstand) {
		log.add(new LoggedEvent(
				"Received new kit conifguration from KitStand that needs to be completed."
		));
		
	}

	@Override
	public void msgIHaveGoodParts(List<AgentPart> gp, Nest nt, LaneCamera camera) {
		log.add(new LoggedEvent(
				"More good parts are available from "+nt.toString()+" so says " + camera.toString()
		));
	}

	@Override
	public void msgPartsReceived() {
		log.add(new LoggedEvent(
				"Internal message from the gui that is has picked up the parts from the nest"
		));
	}

	@Override
	public void msgPartsSent() {
		log.add(new LoggedEvent(
				"Message from the gui that it has finished sending the parts to the appropriate kit"
		));		
	}

}
