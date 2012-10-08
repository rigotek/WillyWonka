//Alberto Marroquin

package engine.test.mock;
import structures.AgentKit;
import engine.interfaces.KitCamera;
import engine.test.EventLog;
import engine.test.LoggedEvent;
import engine.test.MockAgent;
import java.util.LinkedList;

public class MockKitCamera extends MockAgent implements KitCamera {
	public EventLog log = new EventLog();
	public LinkedList<AgentKit> kits = new LinkedList<AgentKit>();
	
	public MockKitCamera(String name) {
		super(name);
	}
	
	public void msgInspectKit(AgentKit kit) {
		log.add(new LoggedEvent("KitCamera received message: msgInspectKit. Kit is on slot "+
				kit.position));
		kits.add(kit);
	}
	
	public void msgDoneTakingPicture() {
		log.add(new LoggedEvent("KitCamera received message: msgDoneTakingPicture. "));
		
	}
	
	public LinkedList<AgentKit> getKits(){
		return kits;
	}
}
