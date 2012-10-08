package engine.agents;

import structures.AgentBin;
import structures.PartType;
import engine.interfaces.Feeder;
import engine.interfaces.Lane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

import structures.AgentPart;
import engine.Agent;
import engine.interfaces.Gantry;
import gui.GuiDiverter;
import gui.GuiFeeder;

/**
 * Description:
 * The Feeder Agent has two parts combined into it that work together: 
 * the feeder and the Diverter.  The feeder will receive a bin from the gantry 
 * and move it onto the feeder belt which will move the bin down towards
 * the diverter where it stops.  When it arrives, it checks to make sure that 
 * the lane the parts will enter into has spots and that the diverter is in 
 * the proper position.  At this point each part is sent to the lane until the 
 * lane gets full or the feeder runs out of parts to send.  At this point, the
 * bin is purged and a new bin (of a determined type) is requested.
 * 
 * The feeder will receive the following messages:
 * 		msgHereIsBin(Bin) from the GantryAgent
 * 		msgLaneSpotsOpen(int) from the Lane
 * The feeder will send the following messaged:
 * 		msgIInitiallyNeedThisPartType() to GantryAgent
 * 		msgIReturnAndRequestPartType(typePart) to the GantryAgent
 * 		msgRequestNumnerOfOpenSpots() to the LaneAgent
 * 		msgHereIsPart(Part) to the LaneAgent
 * 
 * @author Shannon McElligott
 */

public class FeederAgent extends Agent implements Feeder{

//**********************************DATA************************************//	
	
	
	/** Enum that will be set by a message from the top lane or constructor telling
	 * the feeder what type of part the top lane is producing/what it needs to order */
	private PartType topPartType = null;	
	/** Enum that will be set by a message from the top lane or constructor telling
	 * the feeder what type of part the top lane is producing/what it needs to order */
	private PartType bottomPartType = null;
	
	//LIST OF THE AGENTS THAT THE FEEDER PASSES MESSAGES TO
	/** This is the top lane agent that will be called when the lane has parts that
	 * are specific to that lane (topPartType) */
	private Lane topLaneAgent;
	/** This is the bottom lane agent that will be called when the lane has parts that
	 * are specific to that lane (bottomPartType) */
	private Lane bottomLaneAgent;
	/** Gantry agent that will send new bins and pick up old bins */
	private Gantry	gantryAgent;
	
	//LIST OF ALL THE GUI THAT THE FEEDER CALLS
	/** Gui feeder that is sent messages by the feeder */
	private GuiFeeder	guiFeeder;
	/** Gui diverter that will be messaged when a part needs to be passed */
	private GuiDiverter	guiDiverter;
	
	/** The current bin that is in the feeder */
	private AgentBin bin;
	
	/** number of spots open in the top lane */
	private int topLaneSpotsOpen = 0;
	/** number of spots open in the bottom lane */
	private int bottomLaneSpotsOpen = 0;
	/** counts the number of times the number of parts has been requested from the 
	 * current lane that is being sent parts*/
	private int counterOfLaneSpotRequests = 0;
	/**counts the number of parts that have been moved from the diverter/bin to the lane*/
	private int numberOfPartsMoved = 0;
	
	/** List of lanes whose part types need to be updated*/
	private List<UpdatedLane> updatedLanes = Collections.synchronizedList(new ArrayList<UpdatedLane>());

	/** Class that is used to store the data of lanes that need to be updated */
	public class UpdatedLane{
		/** Constructor that fills in data fields; @param pos; @param partT */
		public UpdatedLane(boolean pos, PartType partT) {
			position = pos;
			partType = partT;
		}				
		/** Basic constructor */
		public UpdatedLane() {
		}
		/** 0 (false) for top; 1 (true) for top */
		boolean position;
		/** PartType that is the new part type in the specified lane */
		PartType partType;
	}


	//Testing Variables:
	/** counts the number of times the lane can be requested */ 
	int counterLimitOfRequests = 1;
	/** only creates 10 parts */
	boolean testV0 = false;
	/** Used for testing to avoid calling the GUI actions */
	private boolean guiIsOn = true;
	
	/** direction that the diverter is moving */
	private boolean diverterDirectionIsUp = true; 
	
	/** part type that will be requested by the feeder once the bin has been purged */
	PartType iNeedThisPartType = null;

	/** timer that will be used to randomly send parts down the lane */
	Timer timer = new Timer();
	
	//BOOLEANS THAT CHANGE WHEN ACTION RETURN METHODS ARE CALLED
	/** set in the message that receives the bin from the gantry */
	private boolean binIsHere = false;
	/** booleans that are set during actions that allow the states to switch between states */
	private boolean movedBinDownFeederLane = false;
	private boolean diverterIsInProperPosition = false;
	private boolean laneHasOpenSpots = false;
	private boolean requestNumberOfSpotsInLane = false;
	private boolean needToPurgeBin = false;
	private boolean binHasBeenPurged = false;
	private boolean noNeedToChangePartTypes = false;
	private boolean doneMovingPart = false;
	private boolean receivedNumberOfSpotsInLane = false;
	private boolean requestedABinFromGantry = false;
	
	/** request a bin */
	private boolean needToRequestABin = false;
	
	/** agent part; this will be passed to the laneAgent */
	private AgentPart agentPart;
	
	private String name;
	
	public enum FeederState { 	
		/** large feeder states */
		loadBin, unloadPart, purgeBin, idle
	}
	private FeederState feederState;
	
	public enum InternalFeederState {
		/** idle states */
		initial, requestedBin,
		/** loadBin states */
		receivedBin, waitingToMoveBinDownLane, checkingDiverterPosition,
		/** unloadPart states */
		checkingNumberOfSpotsOpenInLane, checkIfNeedToChangePartType,
		messagingRequestForNumberOfSpotsInLane, waitingForPartToBeMovedIntoLane,
		/** purgeBin state */
		waitingForBinToBePurged, decideToMessageGantryForReturnBin, waitingForAReturnBin, notRequestedABin
	
	}
	private InternalFeederState internalFeederState;
	
	//CONSTRUCTOR
	public FeederAgent(String name) {
		bin = null;
		this.name = name;
		feederState = FeederState.idle;
		internalFeederState = InternalFeederState.initial;

	}

//**********************************MESSAGES************************************//

	/** message called by the lane that requests the feeder to switch a certain partType in its lane
	 * to a new part type; will add the new partType to array updatedLanes */
	public void msgChangeToThisPart(Lane lane, PartType partType) {
		print("Feeder has received request to change the part (" + partType.toString() +
				") in the lane [" + lane.toString() + "].");
		if (lane == topLaneAgent) {
			print("" + internalFeederState);
			UpdatedLane updatedLane = new UpdatedLane(false, partType);
			updatedLanes.add(updatedLane);
		}
		else {
			UpdatedLane updatedLane = new UpdatedLane(true, partType);
			updatedLanes.add(updatedLane);
		}
		stateChanged();
	}

	/** message called by the gantry, passing the feeder then new bin */
	public void msgHereIsBin(AgentBin newBin) {
		bin = newBin; //sets the Feeder bin to new bin that was delivered
		print("Received new bin of type (" + bin.type() + ") from Gantry.");
		binIsHere = true;
		stateChanged();
	}
	
	/** message called by the LaneAgent once the number of spots has been requested by the feeder; */
	public void msgLaneSpotsOpen(Lane lane, int spotsOpen) {
		if (lane == topLaneAgent) {
			topLaneSpotsOpen = spotsOpen;
			print("Received a message from the top lane stating it has " + spotsOpen +
					" spot open.");
			receivedNumberOfSpotsInLane = true;
		}		
		if (lane == bottomLaneAgent) {
			bottomLaneSpotsOpen = spotsOpen;
			print("Received a message from the bottom lane stating it has " + spotsOpen +
			" spot open.");
			receivedNumberOfSpotsInLane = true;
		}
		stateChanged();
	}
	
//**********************************SCHEDULER************************************//

	/** Scheduler method; called when stateChanged() is called. */
	public boolean pickAndExecuteAnAction() {
		
//		print("In scheduler: " + feederState.toString() + "; " + internalFeederState.toString() +
//				"; need to purge bin: " + needToPurgeBin);
		
		/****IDLE STATE****/
		if (feederState == FeederState.idle) {
			if (internalFeederState == InternalFeederState.initial) {
				synchronized(updatedLanes) {
					if (updatedLanes.size() > 0) {
						internalFeederState = InternalFeederState.requestedBin;
						initialUpdateLane();
						return true;
					}
				}
			}
			if (internalFeederState == InternalFeederState.requestedBin) {
				if (binIsHere == true) {
					binIsHere = false;
					feederState = FeederState.loadBin;
					internalFeederState = InternalFeederState.receivedBin;
					return true;
				}
			}
			if (internalFeederState == InternalFeederState.notRequestedABin) {
				synchronized(updatedLanes) {
					if (updatedLanes.size() > 0) {
						internalFeederState = InternalFeederState.requestedBin;
						initialUpdateLane();
						return true;
					}
				}
			}
		}
		
		
		/****LOAD BIN STATE****/
		if (feederState == FeederState.loadBin) {
			if (internalFeederState == InternalFeederState.receivedBin) {
				internalFeederState = InternalFeederState.waitingToMoveBinDownLane;
				callToMoveBinDownFeederLane();
				return true;
			}
			if (internalFeederState == InternalFeederState.waitingToMoveBinDownLane) {
				if (movedBinDownFeederLane == true) {
					movedBinDownFeederLane = false;
					internalFeederState = InternalFeederState.checkingDiverterPosition;
					checkingDiverterPosition();
					return true;
				}
			}
			if (internalFeederState == InternalFeederState.checkingDiverterPosition) {
				if (diverterIsInProperPosition == true) {
					diverterIsInProperPosition = false;
					feederState = FeederState.unloadPart;
					internalFeederState = InternalFeederState.checkingNumberOfSpotsOpenInLane;
					checkNumberOfSpotsOpenInLane();
					return true;
				}
			}
		}
		
		
		/****UNLOAD PART****/
		if (feederState == FeederState.unloadPart) {
			if (internalFeederState == InternalFeederState.checkingNumberOfSpotsOpenInLane) {
				if (needToPurgeBin == true) {
					needToPurgeBin = false;
					feederState = FeederState.purgeBin;
					internalFeederState = InternalFeederState.waitingForBinToBePurged;
					callToPurgeBin();
					return true;
				}
				if (laneHasOpenSpots == true) {
					laneHasOpenSpots = false;
					internalFeederState = InternalFeederState.checkIfNeedToChangePartType;
					synchronized(updatedLanes) {
						updatePartTypes();
					}
					return true;
				}
				if (requestNumberOfSpotsInLane == true) {
					requestNumberOfSpotsInLane = false;
					internalFeederState = InternalFeederState.messagingRequestForNumberOfSpotsInLane;
					requestNumberOfSpotsInLane();
					return true;
				}
			}
			
			if (internalFeederState == InternalFeederState.checkIfNeedToChangePartType) {
				if (noNeedToChangePartTypes == true) {
					noNeedToChangePartTypes = false;
					internalFeederState = InternalFeederState.waitingForPartToBeMovedIntoLane;
					callToMovePart();
					return true;
				}
				if (needToPurgeBin == true) {
					needToPurgeBin = false;
					feederState = FeederState.purgeBin;
					internalFeederState = InternalFeederState.waitingForBinToBePurged;
					callToPurgeBin();
					return true;
				}		
			}
			
			if (internalFeederState == InternalFeederState.waitingForPartToBeMovedIntoLane) {
				if (doneMovingPart == true) {
					doneMovingPart = false;
					internalFeederState = InternalFeederState.checkingNumberOfSpotsOpenInLane;
					checkNumberOfSpotsOpenInLane();
					return true;
				}
			}
			
			if (internalFeederState == InternalFeederState.messagingRequestForNumberOfSpotsInLane) {
				if (needToPurgeBin == true) {
					needToPurgeBin = false;
					feederState = FeederState.purgeBin;
					internalFeederState = InternalFeederState.waitingForBinToBePurged;
					callToPurgeBin();
					return true;
				}
				
				if (receivedNumberOfSpotsInLane == true) {
					//need to update spots and then go back to checkingNumberOfSpotsOpeninLoa
					receivedNumberOfSpotsInLane = false;
					internalFeederState = InternalFeederState.checkingNumberOfSpotsOpenInLane;
					checkNumberOfSpotsOpenInLane();
					return true;
				}
			}
		}
		
		/****PURGE BIN****/
		if (feederState == FeederState.purgeBin) {
			if (internalFeederState == InternalFeederState.waitingForBinToBePurged) {
				if (binHasBeenPurged == true) {
					binHasBeenPurged = false;
					if (needToRequestABin == true) {
						print("line 347 in scheduler");
						needToRequestABin = false;
						internalFeederState = InternalFeederState.decideToMessageGantryForReturnBin;
						requestABinFromGantry();
						return true;
					}
					else {
						feederState = FeederState.idle;
						internalFeederState = InternalFeederState.notRequestedABin;
						tellGantryToPickUpBin();
						return true;
					}
				}
			}
			if (internalFeederState == InternalFeederState.decideToMessageGantryForReturnBin) {
				if (requestedABinFromGantry == true) {
					requestedABinFromGantry = false;
					feederState = FeederState.idle;
					internalFeederState = InternalFeederState.requestedBin;
					return true;
				}
			}
		}
		
		return false;
	}
	
	
		
	
	
//**********************************ACTIONS************************************//
	
	/**updates lane types if in initial state */
	private void initialUpdateLane() {
		
		UpdatedLane topLane = new UpdatedLane();
		UpdatedLane bottomLane = new UpdatedLane();
		boolean updateBottom = false;
		boolean updateTop = false;

		UpdatedLane updatedLane;
		int count = updatedLanes.size();
		
		for (int i=0; i < count; i++) {
			updatedLane = new UpdatedLane();
			updatedLane = updatedLanes.get(0);
			updatedLanes.remove(updatedLane);
			if (updatedLane.position == false) { //top lane agent
				topLane = updatedLane;
				updateTop = true;
			}
			else {
				bottomLane = updatedLane;
				updateBottom = true;
			}
		}
		//at this point the newest lanes are in the spots bottomLane and updatedLane
		if (updateTop == true) { //top has been updated
			//if you need to update the top lane, then set the topPartType and then reuqest that type
			topPartType = topLane.partType;
			gantryAgent.msgINeedThisPartType(this, topPartType);	
			//if updateBottom is also true, then add it back into the thing.
			if (updateBottom == true) {
				updatedLanes.add(bottomLane);
				//should return and be done
				return;
			}
		}
		if (updateBottom == true) { //top has been updated
			//if (updateTop == true) {
				//should never actually get here
				//updatedLanes.add(topLane);
			//}
			//else {//if you need to update the top lane, then set the topPartType and then reuqest that type
				bottomPartType = bottomLane.partType;
				gantryAgent.msgINeedThisPartType(this, bottomPartType);	
				//if updateBottom is also true, then add it back into the thing.
			//}
		}
	}
		
	/**updates lane types if the array of requests to change part types (updatedLanes) is greater than 0;
	 * will then see if bin needs to be purged as a result of updating the part type */
	private void updatePartTypes() {

	//	print("from line 390...checking updatedParts. purgeBin = " + needToPurgeBin );
		UpdatedLane topLane = new UpdatedLane();
		UpdatedLane bottomLane = new UpdatedLane();
		boolean updateBottom = false;
		boolean updateTop = false;
		UpdatedLane updatedLane;
		int count = updatedLanes.size();
		
		for (int i=0; i < count; i++) {
			updatedLane = new UpdatedLane();
			updatedLane = updatedLanes.get(0);
			updatedLanes.remove(updatedLane);
			if (updatedLane.position == false) { //top lane agent
				topLane = updatedLane;
				updateTop = true;
			//	print("will need to update the top lane to " + topLane.partType);
			}
			else {
				bottomLane = updatedLane;
				updateBottom = true;
			//	print("will need to update the top lane to " + bottomLane.partType);
			}
		}
		if ((updateTop == true) || (updateBottom == true)) {
			if (updateTop == true) { //top has been updated
				if (topPartType == null) { //bin does not have a topPartType
					topPartType = topLane.partType;
					noNeedToChangePartTypes = true;
				}
				else if (topPartType == bin.type()) {
					//commented out in order for the purge stuff to work
					//if (topPartType != topLane.partType) {
						print("The requested part needs to be in the top lane, so the top lane " +
						"needs to be purged.");
						needToPurgeBin = true;
						//means that we need to request an actual bin from the gantry
						needToRequestABin = true;
						iNeedThisPartType = topLane.partType;
					//}
				}
				else { //bin is the opposite type
					topPartType = topLane.partType;
					noNeedToChangePartTypes = true;
				}
			}
			if (updateBottom == true) { //top has been updated
				if (bottomPartType == null) { //bin does not have a topPartType
					bottomPartType = bottomLane.partType;
					noNeedToChangePartTypes = true;
				}
				else if (bottomPartType == bin.type()) {
					if (needToPurgeBin == false) { //don't want to purge bin twice
						//if (bottomPartType != bottomLane.partType) {
							print("The requested part needs to be in the top lane, so the top lane " +
							"needs to be purged.");
							needToPurgeBin = true;
							//means that we need to request an actual bin from the gantry
							iNeedThisPartType = bottomLane.partType;
							needToRequestABin = true;
						//}
					}
					else {
						//add request back into the array
						updatedLanes.add(bottomLane);
						noNeedToChangePartTypes = true;
					}
				}
				else { //bin is the opposite type
					bottomPartType = bottomLane.partType;
					noNeedToChangePartTypes = true;
				}
			}
		}
		else {
			noNeedToChangePartTypes = true;
		}		
	}
	
	/** action that will call the gui and tell the guiFeeder to move the new bin down the lane */
	private void callToMoveBinDownFeederLane() {
		numberOfPartsMoved = 0;
		if(guiIsOn == true) {
			guiFeeder.doMoveBinDownFeederLane(bin.guibin()); //will send the gui of the bin
		}
		//print("Bin is being sent down the feeder lane.");	
	}
	
	/** called by the FeederGui when the bin has successfully been moved to the end of the lane */
	public void doneMoveBinDownFeederLane() {
		movedBinDownFeederLane = true;
		//print("Finished moving bin down the Feeder lane.");
		stateChanged();
	}
	
	/** checks to see if the diverter needs to change the direction; if so, calls animation */
	private void checkingDiverterPosition() {
		//print("Check Diverter Position, which is initially up  <-- : " + diverterDirectionIsUp);
		if ((bin.type() == topPartType) && (diverterDirectionIsUp == false)) {
			if (guiIsOn == true) {
				guiDiverter.doMoveDiverterToPosition('u');
			}
			print("Requested diverter to move up.");
		}
		else if ((bin.type() == bottomPartType) && (diverterDirectionIsUp == true)) {
			if (guiIsOn == true) {
				guiDiverter.doMoveDiverterToPosition('d');
			}
			print("Requested diverter to move down.");
		}
		else {
			print("Does not need to move diverter.  Now need to check the number of spots " +
					"open in the particular lane.");
			diverterIsInProperPosition = true;
			stateChanged();
		}
	}
	
	/** called by the diverter when it has changed positions based on the part type
	 * that is coming into the diverter/lane */
	public void doneMoveDiverterToPosition() { 
		if (diverterDirectionIsUp == false ) {
			print("The diverter has been changed; it now moves down.");
			diverterDirectionIsUp = true;
			diverterIsInProperPosition = true;
		}
		else if ( diverterDirectionIsUp == true ) {
			print("The diverter has been changed; it now moves down.");
			diverterDirectionIsUp = false;
			diverterIsInProperPosition = true;
		}
		stateChanged();
	}
	
	/** checks to make sure there are enough open spots in the lane to pass a part; also makes
	 * sure there are enough spots in the bin that the feeder is unloading, to be able to pass
	 * another part to the diverter */
	private void checkNumberOfSpotsOpenInLane() {
		//need to check to make sure that there are enough spots left in bin
		if (bin.type() == topPartType) {
			if (bin.num() == 0) {
				print("There are no more parts in the bin; the bin needs to be purged.");
				iNeedThisPartType = bottomPartType;
				needToPurgeBin = true;
				if (bottomPartType != null) {
					needToRequestABin = true;
				}
			}
			else if (topLaneSpotsOpen > 0) {
				//don't need to request More Parts
				laneHasOpenSpots = true;
			}
			else {
				requestNumberOfSpotsInLane = true;
			}
		}
		else { // bottom part type is present 
			if (bin.num() == 0) {
				print("There are no more parts in the bin; the bin needs to be purged.");
				iNeedThisPartType = topPartType;
				needToPurgeBin = true;
				if (topPartType != null) {
					needToRequestABin = true;
				}
				return;
			}
			else if (bottomLaneSpotsOpen > 0) {
				//don't need to requestMoreParts
				laneHasOpenSpots = true;
			}
			else {
				requestNumberOfSpotsInLane = true;
			}
		}
	}
	
	/** based on the number of spots in the lanes and how many times the number of spots in
	 * the lane have been requested, a message will be sent to a lane if need to know the number
	 * of spots in the lane that are open or you need to purge the bin, etc. */
	private void requestNumberOfSpotsInLane() {
	//	print("purgeBin = " + needToPurgeBin +"; at beginning of line 534");
		if (bin.type() == topPartType ) {
			if (counterOfLaneSpotRequests > counterLimitOfRequests) {
				//print("came here, line 536");
				//need to purge bin and get different type
				counterOfLaneSpotRequests = 0;
				if (bottomPartType == null) {
					iNeedThisPartType = topPartType;
				}
				else {
					iNeedThisPartType = bottomPartType;
				}
				//print("setting purge bin in line 545");
				needToPurgeBin = true;
			}
			else { 
				topLaneAgent.msgRequestNumberOfOpenSpots();
				print("Feeder has requested the number of spots open in the Top Lane.");
				counterOfLaneSpotRequests++;
			}
		}
		else { //binType = bottom
			if (counterOfLaneSpotRequests > counterLimitOfRequests) {
				//need to purge bin and get different type
				counterOfLaneSpotRequests = 0;
				if (bottomPartType == null) {
					iNeedThisPartType = bottomPartType;
				}
				else {
					iNeedThisPartType = topPartType;
				}
				//print("setting purge bin in line 545");
				needToPurgeBin = true;
			}
			else { 
				bottomLaneAgent.msgRequestNumberOfOpenSpots();
				print("Feeder has requested the number of spots open in the Bottom Lane.");
				//print("purgeBin = " + needToPurgeBin);
				counterOfLaneSpotRequests++;
				//print("came here, line 569");
			}
		}
		
	}

	/** will get the next part in the bin and will send it to the diverter to send it down the lane */
	private void callToMovePart() {
		//at this point we know there are spots left in the lane to unload parts so we may unload 1 part
		//print("size of bin: " + bin.num());
		agentPart = bin.getNextPart();
		randomWait();
		if (guiIsOn == true) {
			guiDiverter.doUnloadPartIntoLane(agentPart.partgui); //sends a GuiPart
		}
		//print("Feeder has sent part for the diverter to move.");
		if (bin.type() == topPartType) {
			topLaneSpotsOpen--;
		}
		else {
			bottomLaneSpotsOpen--;
		}
	}

	/** called by the gui when the part is done being moved into the new lane; also sends a message to
	 * the lane agent with the part type that was moved into the lane */
	public void doneUnloadPartIntoLane() { 
		numberOfPartsMoved++;
		print("Feeder has moved a total of " + numberOfPartsMoved + " into the lane.");
		if (diverterDirectionIsUp == true) { //top lane
			topLaneAgent.msgPartForLane(agentPart);
		//	print("Moved a part into the top lane; sending part.");
		}
		else { // diverterDirectionIsUp == false; //bottom lane
			bottomLaneAgent.msgPartForLane(agentPart);
		//	print("Moved a part into the bottom lane; sending part.");
		}
		doneMovingPart = true;
		//print("done... purgeBin: " + needToPurgeBin);
		stateChanged();
	}//method
	
	/** will call the gui animation to return the bin to the front of the feeder */
	private void callToPurgeBin () {
		//print("The feeder is being purged.");
		if (guiIsOn == true) {
			guiFeeder.doPurgeBin();
		}
	}
	
	/** will be called by the guiFeeder, telling the feeder that it done purging bin and moving bin
	 * back into starting position to be picked up; will request a new bin */
	public void donePurgeBin () {
		if (this == null){
			print("agent is null");
		}
		if (bin == null){
			print("bin is null");
		}
		/*
		if (iNeedThisPartType == null){
			if (bin.type() == topPartType) {
				if (bottomPartType != null) {
					print("iNeedThisPartType is null, changed to bottomPartType");
					iNeedThisPartType = bottomPartType;
				}
				else {
					print("iNeedThisPartType is null, changed to topPartType");
					iNeedThisPartType = topPartType;
				}
			}
			else {
				if (topPartType != null) {
					print("iNeedThisPartType is null, changed to topPartType");
					iNeedThisPartType = topPartType;
				}
				else {					
					print("iNeedThisPartType is null, changed to topPartType");
					iNeedThisPartType = bottomPartType;
				}
			}
		}
		if (iNeedThisPartType == null){
			print("ineedpart is still null");
		}
		*/
		//AgentBin gantryBin = new AgentBin(bin);
		//gantryBin = bin;
		//bin = null;
		//iNeedThisPartType = null;
		//temporarily:
		//needToRequestABin = true;
		if (diverterDirectionIsUp == true) {
			topPartType = null;
		}
		else {
			bottomPartType = null;
		}
		print("Bin has been purged, is now ready to be picked up by the gantry robot.");
		topLaneSpotsOpen = 0;
		bottomLaneSpotsOpen = 0;
		counterOfLaneSpotRequests = 0;
		binHasBeenPurged = true;
		stateChanged();
	}
	
	private void requestABinFromGantry() {
		gantryAgent.msgReturnAndRequestBin(this, bin, iNeedThisPartType);
		print("The old bin has been moved to the front of the feeder and is ready to be " +
				"picked up by the Gantry.  A request for " + iNeedThisPartType.toString() +
				" has be sent to the gantry.");
		requestedABinFromGantry = true;
	}
		
	private void tellGantryToPickUpBin() {
		gantryAgent.msgReturnBin(this, bin);
		print("Sent message to gantry telling it to pick up current bin.");
	}

	
	public void turnOffFeeder() {
		//turning off feeder - pauses the feeder gui
		guiFeeder.doFlipOnOffState(false);
	}
	
	public void wave() {
		/*will call the animation and set the bin to null;
		will set the feeder to its initial state */
		guiFeeder.doChocolateWave();
	}
	
	public void drain(){
		guiFeeder.doFlipOnOffState(false);
		//agent should also be paused
	}
	
	public void randomWait() {
		long random = (long) (1000*Math.random());
		/*try {
			java.lang.Thread.currentThread().sleep(random);
		} catch (Exception e){
			
		}*/
	}
	
//**********************************GETTERS/SETTERS************************************//
	
	/** Getters and Setters; getters mainly used for testing purposes; setters used for agents, GUI, etc. */
	
	public String iNeedThisPartType() {
		return iNeedThisPartType.toString();
	}
	
	public void setToTest(boolean on) {
		if (on == true){
			guiIsOn = false;
		}
		else {
			guiIsOn = true;
		}
	}
	
	public void setTestV0(boolean on) {
		testV0 = on;
	}

	/** Sets the gantry agent in the feeder; @param gantry */
	public void setGantryAgent(Gantry gantry) {
		gantryAgent = gantry;
	}
	
	/** Sets the TOP lane agent in the feeder; @param lane */
	public void setTopLaneAgent(Lane lane) {
		topLaneAgent = lane;
	}
	/** Sets the BOTTOM lane agent in the feeder; @param lane */
	public void setBottomLaneAgent(Lane lane) {
		bottomLaneAgent = lane;
	}
	
	public PartType topPartType() {
		return topPartType;
	}
	
	public PartType bottomPartType() {
		return bottomPartType;
	}
	
	public List<UpdatedLane> updatedLanes() {
		return updatedLanes;
	}
	
	public int bottomLaneSpotsOpen() {
		return bottomLaneSpotsOpen;
	}
	
	public int topLaneSpotsOpen() {
		return topLaneSpotsOpen;
	}
	
	public void setGuiFeeder(GuiFeeder gf) {
		guiFeeder = gf;
	}
	
	public void setGuiDiverter(GuiDiverter gd) {
		guiDiverter = gd;
	}
	
	public GuiFeeder getFeederGui() {
		return guiFeeder;
	}
	
	public String fs() {
		return feederState.toString();
	}
	
	public String ifs() {
		return internalFeederState.toString();
	}

	public boolean needToPurgeBin() {
		return needToPurgeBin;
	}
	
	public String toString() {
		return name;
	}
	
	
}//class
