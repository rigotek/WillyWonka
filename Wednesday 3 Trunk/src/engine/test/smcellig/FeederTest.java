package engine.test.smcellig;

import java.util.LinkedList;
import java.util.List;
import structures.AgentBin;
import structures.AgentPart;
import structures.PartType;
import engine.agents.FeederAgent;
import engine.test.mock.MockGantry;
import engine.test.mock.MockLane;
import gui.GuiDiverter;
import gui.GuiFeeder;
import gui.holdable.GuiBin;
import junit.framework.TestCase;

/** Test switches bin type during the running of it. */
public class FeederTest extends TestCase{
	
	FeederAgent feeder;
	MockGantry gantry;
	MockLane bottomLane, topLane;
	
	AgentBin bin1, bin2;
	List<AgentPart> listOfAgentParts1, listOfAgentParts2;
	int bin1Size = 0, bin2Size = 0;
	
	GuiDiverter guiDiverter; 
	GuiFeeder guiFeeder;
	GuiBin guiBin;
	
	/** Starts by having one partType in the top bin, unloading it, changing the
	 * bottom bin type, then changing the top bin type, causing the bin to be purged;
	 * ordering a different type of part.  May need to change this part in the code
	 * to see which bin is less full, order that part Type... i think this is what
	 * the other methods were for.
	 */
public void testMultipleBinsEntering() {
		
		//creating agents and their mock's
		feeder = new FeederAgent("Feeder");
		gantry = new MockGantry("Gantry");		
		bottomLane = new MockLane("BottomLane");
		topLane = new MockLane("TopLane");
		
		//creating GUI & setting it
		guiDiverter = new GuiDiverter(23, 23);
		guiFeeder = new GuiFeeder(22, 22);
		feeder.setGuiFeeder(guiFeeder);
		feeder.setGuiDiverter(guiDiverter);
		guiBin = new GuiBin(0, 0);
		
		//setting the mock agents in the agent code
		feeder.setGantryAgent(gantry);
		feeder.setBottomLaneAgent(bottomLane);
		feeder.setTopLaneAgent(topLane);
	
		//create new variables that will be used as parameters in the messages:
		PartType partType1 = PartType.TYPE1;
		PartType partType2 = PartType.TYPE2;
		PartType partType3 = PartType.TYPE3;
	//	PartType partType4 = PartType.TYPE4;
		
			//creating a bin of type partType1:
			listOfAgentParts1 = new LinkedList<AgentPart>();
			for	(int i = 0; i<13; i++) {
				AgentPart agentPart = new AgentPart(partType1);
				listOfAgentParts1.add(agentPart);
				bin1Size++;
			}
			bin1 = new AgentBin(listOfAgentParts1, partType1);
			bin1.setGuiBin(new GuiBin(0,0));
			
			//creating a bin of type partType1:
			listOfAgentParts2 = new LinkedList<AgentPart>();
			for	(int i = 0; i<13; i++) {
				AgentPart agentPart2 = new AgentPart(partType2);
				listOfAgentParts2.add(agentPart2);
				bin2Size++;
			}
			bin2 = new AgentBin(listOfAgentParts2, partType2);
			bin2.setGuiBin(new GuiBin(0,0));

		
			// set testing to true 
			feeder.setToTest(true);
			
		//check initial conditions	
		assertFalse("Scheduler shouldn't enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'initial'", "initial", feeder.ifs());
		assertEquals("Checking to see if the number of messages that have been sent to the Bottom Lane" +
				" Agent is equal to 0.", 0, bottomLane.log.size());
		assertEquals("Checking to see if the number of messages that have been sent to the Bottom Lane" +
				" Agent is equal to 0.", 0, bottomLane.log.size());
		assertEquals("Checking to see if the number of messages that have been sent to the Gantry" +
				"is equal to 0.", 0, gantry.log.size());
		
		//request partType 1 in topLane & partType2 in bottomLane
		feeder.msgChangeToThisPart(topLane, partType1);
		feeder.msgChangeToThisPart(bottomLane, partType2);	
		//size of the updatedlanes array should be 2
		assertEquals("The list of lanes that need to be updated should now be equal to 2", 2, 
				feeder.updatedLanes().size());
		assertEquals("The ifs should be 'initial'", "initial", feeder.ifs());

		//first real run of scheduler; next state should be requestedBin 
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'requestedBin'", "requestedBin", feeder.ifs());
		//check to see that the topPartType was set and requested; the bottom was set, but
		//not requested
		assertEquals("The topPartTypePart should be changed to the new part type.", partType1,
				feeder.topPartType());
		//it has not changed yet, so this should be null and the size of updatedLanes should
		//still equal 1.
		assertEquals("The bottomPartTypePart should be changed to the part type 'null'.", null,
				feeder.bottomPartType());
		assertEquals("Checking to see if the number of messages that have been sent to the Gantry" +
				"is equal to 1.", 1, gantry.log.size());
		//all the lane requests have been processed, so it should be equal to 0
		assertEquals("The list of lanes that need to be updated should now be equal to 1", 1, 
				feeder.updatedLanes().size());

		//now running the scheduler should not get you anywhere.
		assertFalse("Scheduler shouldn't enter into an action", feeder.pickAndExecuteAnAction());
		
		//will now be requesting a type3 part in the bottomlane so that when the scheduler is called
		// * and it the updateParts is eventually called, type3 will prevail. 
		feeder.msgChangeToThisPart(bottomLane, partType3);
		assertEquals("The list of lanes that need to be updated should now be equal to 2", 2, 
				feeder.updatedLanes().size());
		//final check
		assertFalse("Scheduler shouldn't enter into an action", feeder.pickAndExecuteAnAction());
		
		//gantry sending in the message with a new bin
		feeder.msgHereIsBin(bin1);
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'receivedBin'", "receivedBin", feeder.ifs());
		//nothing else should actually be printed at this time, after the comment about the
		// * new bin type being received
		
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'waitingToMoveBinDownLane'", "waitingToMoveBinDownLane", feeder.ifs());
		assertFalse("Scheduler shouldn't enter into an action", feeder.pickAndExecuteAnAction());

		//this call will set a variable to true which will allow the pickAndExecuteAnAction() to run through
		feeder.doneMoveBinDownFeederLane();
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'checkingDiverterPosition'", "checkingDiverterPosition", feeder.ifs());
		//if the next statement is true, it means that the diverter is in the proper position and should not
		//* be changed, which is true 
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'checkingNumberOfSpotsOpenInLane'", "checkingNumberOfSpotsOpenInLane", feeder.ifs());
		//should not be purging bin
		assertFalse("The needToPurgeBin variable should equal 'false'", feeder.needToPurgeBin());
		//the following should go into requestNumberOfSpotsInLane
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		//now will come back after calling to check spots or something... this time will go into:
		//* request number of spots in lane; adding a message to lane log list 
		assertFalse("Scheduler shouldn't enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("Checking to see if the number of messages that have been sent to the Top Lane" +
				" Agent is equal to 1.", 1, topLane.log.size());
		assertEquals("The ifs should be 'messagingRequestForNumberOfSpotsInLane'", "messagingRequestForNumberOfSpotsInLane", feeder.ifs());

		//will receive information from the lane
		feeder.msgLaneSpotsOpen(topLane, 20);
		//current state
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'checkingNumberOfSpotsOpenInLane'", "checkingNumberOfSpotsOpenInLane", feeder.ifs());
		assertEquals("The bottomPartTypePart should be changed to the new part type.", null,
				feeder.bottomPartType());
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		//new state
		assertEquals("The ifs should be 'checkIfNeedToChangePartType'", "checkIfNeedToChangePartType", feeder.ifs());
		assertEquals("The bottomPartTypePart should be changed to the new part type.", partType3,
				feeder.bottomPartType());
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'waitingForPartToBeMovedIntoLane'", "waitingForPartToBeMovedIntoLane", feeder.ifs());
		assertFalse("Scheduler shouldn't enter into an action", feeder.pickAndExecuteAnAction());
		
		for (int y = 0; y < 11; y++) {
			feeder.doneUnloadPartIntoLane();					
			assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
			assertEquals("The ifs should be 'checkingNumberOfSpotsOpenInLane'", "checkingNumberOfSpotsOpenInLane", feeder.ifs());
			//should have good amount of spots
			assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
			assertEquals("The ifs should be 'checkIfNeedToChangePartType'", "checkIfNeedToChangePartType", feeder.ifs());
			assertFalse("The needToPurgeBin variable should equal 'equal'", feeder.needToPurgeBin());
			assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
			assertEquals("The ifs should be 'waitingForPartToBeMovedIntoLane'", "waitingForPartToBeMovedIntoLane", feeder.ifs());
		}
		
		feeder.doneUnloadPartIntoLane();					
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'checkingNumberOfSpotsOpenInLane'", "checkingNumberOfSpotsOpenInLane", feeder.ifs());
		//should have 0 amount of parts in the bin

		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'waitingForBinToBePurged'", "waitingForBinToBePurged", feeder.ifs());
		assertFalse("No scheduler action should be called", feeder.pickAndExecuteAnAction());
		
		//animation return call
		feeder.donePurgeBin();
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'decideToMessageGantryForReturnBin'", "decideToMessageGantryForReturnBin", feeder.ifs());

		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		assertEquals("The ifs should be 'requestedBin'", "requestedBin", feeder.ifs());
		
		assertFalse("No scheduler action should be called", feeder.pickAndExecuteAnAction());

		feeder.msgHereIsBin(bin2);
		assertTrue("Scheduler should enter into an action", feeder.pickAndExecuteAnAction());
		
		//comparing message sizes
		assertEquals("Checking to see if the number of messages that have been sent to the Gantry" +
				" is equal to 13.", 13, topLane.log.size());
		assertEquals("Checking to see if the number of messages that have been sent to the Gantry" +
				" is equal to 0.", 0, bottomLane.log.size());
		assertEquals("Checking to see if the number of messages that have been sent to the Gantry" +
				" is equal to 2.", 2, gantry.log.size());
		
		

	
		
}

}