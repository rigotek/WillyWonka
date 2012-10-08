package engine.test.mock;
import java.util.LinkedList;
import java.util.ArrayList;
import engine.interfaces.KitStand;
import engine.test.EventLog;
import engine.test.LoggedEvent;
import engine.test.MockAgent;
import structures.AgentKit;
import structures.AgentPart;

public class MockKitStand extends MockAgent implements KitStand {
	public LinkedList<AgentKit> kits = new LinkedList<AgentKit>();
	
	public MockKitStand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public EventLog log = new EventLog();
	
	public void msgHereIsNextAgentKit(AgentKit ak) {
		log.add(new LoggedEvent("KitStand received message: msgHereIsNextAgentKit. Kit" +
				" slot is " + ak.position));	
		kits.add(ak);
	}
	
	public void msgGiveParts(ArrayList<AgentPart> p, AgentKit kit) {
		log.add(new LoggedEvent(
				"I have received parts from the parts robot for some kit."
		));		
	}

	public LinkedList<AgentKit> getKits(){
		return kits;
	}

}
