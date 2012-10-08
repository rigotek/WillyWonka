package engine.test.rmowrey;

import java.util.ArrayList;
import java.util.List;

import structures.AgentKit;
import structures.AgentPart;
import structures.Configuration;

import engine.agents.KitStandAgent;
import engine.test.mock.MockKitRobot;
import engine.test.mock.MockPartsRobot;
import junit.framework.TestCase;

public class KitStandAgentTest extends TestCase {

	KitStandAgent kitstand;
	MockKitRobot kitrobot;
	MockPartsRobot partsrobot;
	AgentKit kit;
	ArrayList<AgentPart> p;
	
	public void testMsgGiveParts(){
		kitstand = new KitStandAgent("kitstandagent");
		kitrobot = new MockKitRobot("kitrobot");
		partsrobot = new MockPartsRobot("partsrobot");
		kit = new AgentKit(null);
		p = new ArrayList<AgentPart>();
		
		kitstand.msgGiveParts(p, kit);
		
		assertTrue("kitstand parts should not be null", p != null);
		
	}
}
