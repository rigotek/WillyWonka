package engine.test.tasci;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.*;
import engine.Agent;
import engine.agents.*;
import engine.agents.PartsRobotAgent.NestPart;
import engine.agents.PartsRobotAgent.PartsRobotState;
import engine.interfaces.*;
import engine.test.mock.*;
import gui.GuiPartsRobot;
import gui.holdable.GuiCandy;
import gui.holdable.GuiKit;
import gui.interfaces.Gui;
import gui.interfaces.GuiPartsRobotInterface;
import structures.*;

/**
 * This test class tests all of the normative scenarios of the parts robot and its
 * interaction with various parts of the factory.
 * 
 * @author Pierre Tasci
 *
 */
public class PartsRobotTest extends TestCase {
	PartsRobotAgent partsrobot;
	MockNest n1,n2;
	MockLaneCamera camera;
	MockKitStand kitstand;
	MockGuiPartsRobot mockrobot = new MockGuiPartsRobot();
	
	/**
	 * Part 1: Creates the parts robot, sets all of its dependencies and tests those
	 * Part 2: Create a configuration and give it to the Parts Robot
	 * Part 3: Call the scheduler and nothing should happen since there have been no parts made ready yet
	 * Part 4: Give the Parts Robot only 1 type of part that it needs and not enough of it, call scheduler and ensure that it chooses to 
	 * fire off the state machine to go pick up those parts
	 */
	public void testNormativeScenario() {
		//----------------------PART1----------------------------
		n1 = new MockNest("Mock Nest 1");
		n2 = new MockNest("Mock Nest 2");
		ArrayList<Nest> nestlist = new ArrayList<Nest>();
		nestlist.add(n1);
		nestlist.add(n2);
		kitstand = new MockKitStand("Kit Stand");
		camera = new MockLaneCamera("Camera");
		
		partsrobot = new PartsRobotAgent("Parts Robot",nestlist);
		
		assertFalse("The partsrobot should now be initialized to something.", partsrobot == null);
		
		partsrobot.setKitStand(kitstand);
		
		assertTrue("The kitstand associated with the parts robot should now be initialized.", partsrobot.kitstand == this.kitstand);
		
		partsrobot.setGuiPartsRobot(mockrobot);
		
		//----------------------PART2----------------------------
		//create a configuration using two part types since we are testing with just two nests for now.
		HashMap<PartType, Integer> cf = new HashMap<PartType,Integer>();
		//this is an easy configuration to process since the parts robot can handle 4 parts at a time
		cf.put(PartType.TYPE1, 4);
		cf.put(PartType.TYPE2, 4);
		Configuration config = new Configuration(cf);
		AgentKit kit1 = new AgentKit(config);
		kit1.guikit = new GuiKit(0, 0);
		
		//now that the kit has been created, send it to the parts robot
		partsrobot.msgINeedThese(kit1, kitstand);
		
		//check that it was successfully added to mykit
		//assertEquals("Mykit should now contain the new agent kit as the leading entry", kit1, partsrobot.mykits.get(0).kit);
		
		//-------------------PART3--------------------------------
		//Call the scheduler of the parts robot. It should return false
		assertFalse("No action was scheduled since we have a kit but no parts.",partsrobot.pickAndExecuteAnAction());
		
		//------------------------PART4----------------------------
		//create some parts to give to the parts robot
		ArrayList<AgentPart> newparts = new ArrayList<AgentPart>();
		//add 3 new part1 which is one less than what the part robot needs
		newparts.add(new AgentPart(PartType.TYPE1));
		newparts.add(new AgentPart(PartType.TYPE1));
		newparts.add(new AgentPart(PartType.TYPE1));
		
		//send these new parts to the parts robot
		partsrobot.msgIHaveGoodParts(newparts, n1, camera);
		
		//ensure that these new parts are in the correct nestpart inner class
		for(NestPart np : partsrobot.nestparts) {
			if(np.nest.equals(n1)) {
				//assure that the nest is the correct type
				assertTrue("Type of this nest should be part1",np.type == PartType.TYPE1);
				//assure that the nest has the three new parts we added
				assertTrue("There are only 3 parts in this nest", np.parts.size() == 3);
				assertTrue("These exact 3 parts are in the nest.", np.parts.containsAll(newparts));
			}
		}
		
		//call scheduler
		assertTrue("Scheduler rule was fired.", partsrobot.pickAndExecuteAnAction());
		
		//assert that we called the switch lane method
		//assertTrue("There should be nothing in missing after now", partsrobot.mykits.get(0).missing.size() == 0);
		//since we added part type 1s after it thought there were none, this switch should do nothing
		assertTrue(n1.log.size() == 0);
		assertTrue(n2.log.size() == 0);
		
		//now call the scheduler again so it will pick up on the parts in nest 1 it wants to deliver
		assertTrue("Scheduler was fired.", partsrobot.pickAndExecuteAnAction());
		
		//check that i am now holding these parts
		assertTrue("I am holding the parts now.", partsrobot.myparts.parts.size() == 3);
		
		//---------------PART5---------------------------
		//Now that the gui has been called, we need it to call back
		partsrobot.msgPartsReceived();
		
		assertTrue("I am now in the gui holding parts state", partsrobot.state == PartsRobotState.GUIHOLDINGPARTS);
		
		//call the scheduler and make sure that now we are sending message to the nest
		assertTrue("Scheduler fired", partsrobot.pickAndExecuteAnAction());
		
		assertTrue("State is now holding parts", partsrobot.state == PartsRobotState.HOLDINGPARTS);
		assertTrue(n1.log.size() > 0);
		
		//since we are an FSM we should now go directly back to the FSM and deliver those parts
		assertTrue("Scheduler Fired", partsrobot.pickAndExecuteAnAction());
		assertFalse(partsrobot.myparts == null);
		assertTrue(partsrobot.myparts.parts.size() > 0);
		assertTrue("State is now gui delivering parts", partsrobot.state == PartsRobotState.GUIDELIVERINGPARTS);
		
		//Now we get a callback from the gui
		partsrobot.msgPartsSent();
		assertTrue("State should be delivering parts",partsrobot.state == PartsRobotState.DELIVERINGPARTS);
		
		//schedule the action to send the message to the kitstand
		assertTrue("Scheduler fired", partsrobot.pickAndExecuteAnAction());
		assertTrue(partsrobot.state == PartsRobotState.IDLE);
		//assertTrue(partsrobot.mykits.size() > 0);
		
		assertTrue(kitstand.log.size() > 0);
		
		//now lets make it decide between two nests to go pick up parts
		ArrayList<AgentPart> morepart1 = new ArrayList<AgentPart>();
		ArrayList<AgentPart> morepart2 = new ArrayList<AgentPart>();
		//add 3 new part1 which is one less than what the part robot needs
		morepart1.add(new AgentPart(PartType.TYPE1));
		morepart1.add(new AgentPart(PartType.TYPE1));
		morepart1.add(new AgentPart(PartType.TYPE1));
		morepart2.add(new AgentPart(PartType.TYPE2));
		morepart2.add(new AgentPart(PartType.TYPE2));
		morepart2.add(new AgentPart(PartType.TYPE2));
		morepart2.add(new AgentPart(PartType.TYPE2));
		//there are now enough parts of both types to complete the kit
		partsrobot.msgIHaveGoodParts(morepart1, n1, camera);
		partsrobot.msgIHaveGoodParts(morepart2, n2, camera);
		
		//now let it go pick which nest to go get parts from, it should take the remaining parts for part type 1
		assertTrue(partsrobot.pickAndExecuteAnAction());
		assertTrue(partsrobot.myparts.parts.size() == 1);
	}
	
	
	
	private class MockGuiPartsRobot extends GuiPartsRobot implements GuiPartsRobotInterface {

		@Override
		public void draw(Graphics g) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updateLocation() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setAgent(Agent agent) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void DoGoToIdle() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void DoRetrieveParts(Gui nest, ArrayList<GuiCandy> parts) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void DoSendParts(Gui kitStand, Gui kit, ArrayList<GuiCandy> parts) {
			// TODO Auto-generated method stub
			
		}
		
	}
}