package engine.test.mock;

import structures.AgentBin;
import structures.PartType;
import engine.interfaces.Feeder;
import engine.interfaces.Gantry;
import engine.test.EventLog;
import engine.test.LoggedEvent;
import engine.test.MockAgent;


public class MockGantry extends MockAgent implements Gantry {
	
	public MockGantry(String name) {
		super(name);
	}

	public EventLog log = new EventLog();

	@Override
	public void msgReturnAndRequestBin(Feeder f, AgentBin b, PartType pt) {
		log.add(new LoggedEvent("Message Received: Gantry has received a request from the" +
				" Feeder Agent requesting the part type " + pt.toString() + " and returning " +
				"the current bin that needs to be refilled."));		
	}

	@Override
	public void msgINeedThisPartType(Feeder f, PartType parttype) {
		log.add(new LoggedEvent("Message Received: Gantry has received a request from the " +
				"bin during the start of the factory, requesting the first bin of type: " +
				parttype.toString()));		
	}

	@Override
	public void msgReturnBin(Feeder f, AgentBin b) {
		log.add(new LoggedEvent("Message Received: Gantry has received a bin back from the " +
				"feeder."));			
	}
	
	

}