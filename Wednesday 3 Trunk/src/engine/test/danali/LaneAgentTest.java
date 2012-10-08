package engine.test.danali;

import structures.AgentPart;
import structures.PartType;
import engine.agents.LaneAgent;
import engine.test.mock.MockFeeder;
import engine.test.mock.MockNest;
import junit.framework.TestCase;

/**
 * This class unit tests the Lane Agent normative scenarios with its messages and interaction with other
 * factory agents.
 * 
 * @author Dana Li
 *
 */

public class LaneAgentTest extends TestCase {
	
	// The lane agent to be tested
	
	public LaneAgent lane;
	
	/**
	 * This method tests what happens when the feeder requests to know the number of spots open on
	 * the lane. When the lane receives the message, it should change the boolean variable partwaiting
	 * to true, so when the scheduler runs, it will call an action that messages back the feeder with
	 * the number of spots available on the lane. Also, partwaiting should be switched back to false
	 * so that the lane will not constantly be messaging the feeder with msgLaneSpotsOpen.
	 */
	
	public void testMsgRequestNumberOfOpenSpots() {
		
		lane = new LaneAgent("lane", 20);
		MockFeeder feeder = new MockFeeder("feeder");
		lane.setFeeder(feeder);
		
		lane.msgRequestNumberOfOpenSpots();
		
		assertTrue("The boolean variable partwaiting is now true to indicate the feeder wants to send parts down lane ",
				lane.partwaiting);
		
		lane.pickAndExecuteAnAction();
		
		assertTrue("Feeder should have received message msgLaneSpotsOpen. Event log: " + feeder.log.toString(),
				feeder.log.containsString("Message Received: Feeder was told that there were"));
		
		assertFalse("The boolean variable partwaiting is now true to indicate the feeder wants to send parts down lane ",
				lane.partwaiting);
	}
	
	/**
	 * This method tests what happens when the feeder sends the lane a new part. I created an AgentPart
	 * that will be sent in the msgPartForLane parameter. After the message is called, a new part
	 * should be added to the parts list in the lane. After the scheduler is called, the size variable is
	 * also updated because the part received is moved onto the lane.
	 */
	
	public void testMsgPartForLane() {
		
		lane = new LaneAgent("lane", 20);
		AgentPart part = new AgentPart(PartType.TYPE1);
		
		lane.msgPartForLane(part);
		
		assertEquals("There should now be one part in the parts list ", 1, lane.parts.size());
		
		assertEquals("The number of parts on the lane should be zero ", 0, lane.size);
		
		lane.pickAndExecuteAnAction();
		
		assertEquals("The number of parts on the lane has gone up to one ", 1, lane.size);
		
		lane.msgPartForLane(part);
		
		assertEquals("There should now be two parts in the parts list ", 2, lane.parts.size());
		
		lane.pickAndExecuteAnAction();
		
		assertEquals("The number of parts on the lane has gone up to two ", 2, lane.size);
	}
	
	/**
	 * This method tests what happens when the nest sends msgIAmNotFull to the lane with a boolean
	 * parameter. Whichever boolean value is sent, the variable nestopen should be set to that. If the
	 * boolean is false, after the scheduler call, the action GiveToNest should not happen, and thus
	 * the msgNextPart to the nest won't happen either.
	 */
	
	public void testMsgIAmNotFull() {
		
		lane = new LaneAgent("lane", 20);
		MockNest nest = new MockNest("nest");
		
		lane.msgIAmNotFull(true);
		
		assertTrue("The boolean variable nestopen should be true ", lane.nestopen);
		
		lane.msgIAmNotFull(false);
		
		assertFalse("The boolean variable nestopen should be false ", lane.nestopen);
		
		lane.pickAndExecuteAnAction();
		
		assertFalse("Because the nest is full the lane should not give a part to the nest, so msgNextPart in nest shouldn't be called ", 
				nest.log.containsString("Received message msgNextPart from lane"));

	}
	
	/**
	 * This method tests what happens when the nest messages the lane to change the part type with
	 * msgChangeToThisPart. That should change the boolean variable changetype in the lane to true, and
	 * after the scheduler is called, an action should be taken that involves sending the feeder the
	 * message to change that part type also. Then the changetype boolean should be set back to false.
	 */
	
	public void msgChangeToThisPart() {
		
		lane = new LaneAgent("lane", 20);
		MockFeeder feeder = new MockFeeder("feeder");
		
		lane.msgChangeToThisPart(null);
		
		assertTrue("The boolean variable changetype should be true ", lane.changetype);
		
		lane.pickAndExecuteAnAction();
		
		assertTrue("Feeder should have received message msgChangeToThisPart. Event log: ",
				feeder.log.containsString("Message received: Feeder received the message to change it's part type to"));
		
		assertFalse("The boolean variable changetype should be false ", lane.changetype);
	}
	
}