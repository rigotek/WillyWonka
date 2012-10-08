package engine.test.danali;

import java.util.ArrayList;

import structures.AgentPart;
import structures.PartType;
import engine.agents.NestAgent;
import engine.agents.NestAgent.MyPart;
import engine.agents.NestAgent.PartState;
import engine.test.mock.MockLane;
import engine.test.mock.MockLaneCamera;
import gui.GuiNest;
import junit.framework.TestCase;
import gui.holdable.*;

/**
 * This class unit tests the nest agent's normative scenarios with its messages and interaction with
 * other factory agents.
 * 
 * @author Dana Li
 *
 */

public class NestAgentTest extends TestCase {
	
	// The nest agent to be tested
	
	public NestAgent nest;
	
	/**
	 * This method tests what happens when the lane sends the nest msgNextPart(AgentPart). After it receives the message, the nest should have
	 * one part in its parts list, though the number of parts in the nest should still be zero. After the scheduler call, the number of parts
	 * in the nest will now be one due to the state change, and the nest should have also messaged the lane camera in that action msgIHaveAnotherPart
	 * so the camera knows the nest received an additional part.
	 */
	
	public void testMsgNextPart() {
		
		nest = new NestAgent("nest", 10);
		MockLaneCamera camera = new MockLaneCamera("camera");
		MockLane lane = new MockLane("lane");
		AgentPart part = new AgentPart(null);
		GuiNest guinest = new GuiNest(0, 0);
		nest.capacity = 20;
		
		nest.setLane(lane);
		nest.setLaneCamera(camera);
		nest.setGuiNest(guinest);
		
		nest.msgNextPart(part);
	
		assertEquals("There should now be one part in the parts list ", 1, nest.parts.size());
		
		assertEquals("The number of parts in the nest should be zero ", 0, nest.numParts);
		
		nest.pickAndExecuteAnAction();
		
		assertEquals("The number of parts in the nest should now be one ", 1, nest.numParts);
		
		assertTrue("The lane camera should have received message msgIHaveAnotherPart. Event log: ",
				camera.log.containsString("Received message from Nest"));
		
	}
	
	/**
	 * This method tests what happens when the lane camera sends the nest message msgTakingPicture. The nest should change the boolean variable
	 * picture to true when it receives the message, and after the scheduler call, the action taken should include sending the lane camera
	 * msgHereAreParts with a list of the parts currently in the nest. Then the nest should reset the boolean variable picture back to false
	 * so the scheduler won't keep coming to the action of sending the camera a list of nest parts when it doesn't need one.
	 */
	
	public void testMsgTakingPicture() {
		
		nest = new NestAgent("nest", 10);
		MockLaneCamera camera = new MockLaneCamera("camera");
		MockLane lane = new MockLane("lane");
		
		nest.capacity = 20;
		nest.setLane(lane);
		nest.setLaneCamera(camera);
		
		
		nest.msgTakingPicture();
		
		assertTrue("The boolean variable picture should now be true ", nest.picture);
		
		nest.pickAndExecuteAnAction();
		
		assertTrue("The lane camera should have received message msgHereAreParts. Event log: ",
				camera.log.containsString("Received parts list from Nest"));
		
		assertFalse("The boolean variable picture should now be false ", nest.picture);
	}
	
	/**
	 * This method tests what happens when the parts robot sends nest msgINeedTheseParts. Since this message is sent after the robot has already
	 * taken the parts, the nest needs to identify which parts they were and remove them. In this test, two parts are given to the nest
	 * through msgNextPart and after a scheduler call, makes sure that these parts are in the nest. Then, msgINeedTheseParts is sent, and
	 * after another scheduler call, both those parts should be removed and the nest should be empty again.
	 */
	
	public void testMsgINeedTheseParts() {
		
		nest = new NestAgent("nest", 10);
		MockLaneCamera camera = new MockLaneCamera("camera");
		MockLane lane = new MockLane("lane");
		
		nest.capacity = 20;
		nest.setLane(lane);
		nest.setLaneCamera(camera);
		
		ArrayList<AgentPart> removeparts = new ArrayList<AgentPart>();
		
		AgentPart part1 = new AgentPart(PartType.TYPE1);
		AgentPart part2 = new AgentPart(PartType.TYPE2);
		
		removeparts.add(part1);
		removeparts.add(part2);
		
		nest.msgNextPart(part1);
		nest.msgNextPart(part2);
		
		assertEquals("The parts list in the nest should have two parts ", 2, nest.parts.size());
		
		nest.pickAndExecuteAnAction();
		
		assertEquals("The number of parts in the nest should now be two ", 2, nest.numParts);
		
		nest.msgINeedTheseParts(removeparts);
		
		nest.pickAndExecuteAnAction();
		
		assertTrue("There should be no parts in the parts list now ", nest.parts.isEmpty());
		
		assertEquals("numParts should be zero now ", 0, nest.numParts);
		
	}
	
	/**
	 * This method tests what happens when the parts robot sends the nest msgChangeToThisPart(AgentPart). The message should cause the boolean
	 * variable changetype to become true, and after the scheduler call, the action taken should include messaging the lane with msgChangeToThisPart
	 * so the type change trickles through the agents. Also, the boolean variable changetype should be reset to false again.
	 */
	
	public void testMsgChangeToThisPart() {
		
		nest = new NestAgent("nest", 10);
		MockLaneCamera camera = new MockLaneCamera("camera");
		MockLane lane = new MockLane("lane");
		
		nest.capacity = 20;
		nest.setLane(lane);
		nest.setLaneCamera(camera);
		
		nest.msgChangeToThisPart(null);
		
		assertTrue("The boolean variable changetype should be true ", nest.changetype);
		
		nest.pickAndExecuteAnAction();
		
		assertTrue("The lane should have received message msgChangeToThisPart. Event log: ",
				lane.log.containsString("Received message msgINeedThisPart from the nest"));
		
		assertFalse("The boolean variable changetype should be false ", nest.changetype);
		
		
	}
}