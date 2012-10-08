//Alberto Marroquin

package engine.test.mock;
import structures.AgentKit;
import structures.Configuration;
import engine.interfaces.KitRobot;
import engine.test.EventLog;
import engine.test.LoggedEvent;
import engine.test.MockAgent;
import gui.holdable.GuiKit;

public class MockKitRobot extends MockAgent implements KitRobot{
	public EventLog log = new EventLog();
	
	public MockKitRobot(String name) {
		super(name);
	}

	public void msgHereIsKitConfiguration(Configuration c) {
		log.add(new LoggedEvent("KitRobot received message: msgHereIsKitConfiguration. "+
				"Configuration: " + c));	
	}

	public void msgKitIsDone(AgentKit kit) {
		log.add(new LoggedEvent("KitRobot received message: msgKitIsDone. "+
				"Kit: " + kit + " on position: " + kit.position));	
	}

	public void msgKitIsGood(AgentKit kit) {
		log.add(new LoggedEvent("KitRobot received message: msgKitIsGood. Kit is on slot"+
				kit.position));		
	}

	public void msgKitIsBad(AgentKit kit) {
		log.add(new LoggedEvent("KitRobot received message: msgKitIsBad. Kit is on slot"+
				kit.position));	
	}

	public void msgAnimationDone() {
		log.add(new LoggedEvent("KitRobot received message: msgAnimationDone."));
	}

	public void msgDoneCreatingKit(AgentKit kit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDonePlacingKit(GuiKit kit) {
		// TODO Auto-generated method stub
		
	}
}
