package engine.agents;

import java.util.*;

import structures.*;
import engine.*;
import engine.agents.LaneAgent.MyPart;
import engine.interfaces.*;
import gui.GuiNest;
import gui.holdable.GuiCandy;

/** Agent representation of the Nest in the Kitting Cell Factory. The nest agent accepts parts from 
 * the lane after parts reach the end of the lane and the nest is not full. The nest agent also 
 * interacts with the lane camera by sending the camera all the current parts in the nest when the 
 * camera takes a picture. Also, the agent removes parts from the nest when the parts robots picks 
 * up parts that a kit needs. For the animation API, the nest agent will tell the animation when a 
 * part should appear and disappear from the nest.
 * 
 * @author Dana Li
 *
 */

public class NestAgent extends Agent implements Nest {
	
	// ___________ DATA _________________
	
	public String name;
	public boolean picture;
	public boolean changetype;
	public boolean purgenest;
	public int numParts;
	public int capacity;
	
	public GuiNest guinest;
	
	PartType typechange;
	PartType currenttype;

	Lane lane;
	LaneCamera camera;
	PartsRobot partsrobot;

	public enum PartState {
		NEW,
		INNEST,
		TOREMOVE,
		BAD
	}

	public class MyPart {
		AgentPart p;
		PartState state;
		
		public MyPart (AgentPart p, PartState ps) {
			this.p = p;
			state = ps;
		}
	}

	public List<MyPart> parts = Collections.synchronizedList(new ArrayList<MyPart>());
	
	// _______________ CONSTRUCTOR ____________________
	
	public NestAgent(String name, int capacity)
	{
		this.name = name;
		this.capacity = capacity;
		numParts = 0;
		picture = false;
		changetype = false;
		purgenest = false;
	}
	

	// ___________ MESSAGES ____________________
	
	/**
	 * This message is sent by the lane when a part has reached the end of 
	 * the lane and the nest is not full.
	 */
	public void msgNextPart(AgentPart p) {
		//print("The nest is receiving a new part from the lane");
		MyPart mp = new MyPart(p, PartState.NEW);
		parts.add(mp);
		currenttype = p.type;
		stateChanged();
	}

	/**
	 * This message is sent by the lane camera when it wants to know what parts are in the nest right now.
	 */
	public void msgTakingPicture() {
		print("The lane camera is taking a picture and wants to know what is in the nest");
		picture = true;
		stateChanged();
	}

	/**
	 * This message is sent by the parts robot after it has decided what parts to take out of the 
	 * nest so the nest agent can remove them.
	 */
	public void msgINeedTheseParts(List<AgentPart> pts) {
	//	print("The parts robot has removed a list of parts from the nest of size " + pts.size());
	//	print("Size of the parts list in nest is " + parts.size());
		for(MyPart mp:parts) {
			for(AgentPart p:pts) {
				if(mp.p == p) {
					mp.state = PartState.TOREMOVE;
					//print("CHANGING PART TO STATE TOREMOVE");
				}
			}
		}
		stateChanged();
		
	}
	
	/**
	 * This message is sent by the parts robot when it needs more of a different part type, so this
	 * message will trickle back to the lane, feeder, and gantry.
	 */
	
	public void msgChangeToThisPart(PartType type) {
		print("The parts robot needs the nest to change to this part type " + type);
		changetype = true;
		typechange = type;
		purgenest = true;
		stateChanged();
	}
	
	/**
	 * This message is sent by the lane camera after it has received a list of parts in the nest. It returns
	 * with this message indicate the parts that are bad in the nest and should be purged.
	 */
	
	public void msgThesePartsAreBad(List<AgentPart> pts) {
		for(MyPart mp:parts) {
			for(AgentPart p:pts) {
				if(mp.p == p) {
					mp.state = PartState.BAD;
				}
			}
		}
		stateChanged();
		
	}
	
	public void msgPurgeTheNest() {
		purgenest = true;
		stateChanged();
	}

	// ______________ SCHEDULER _________________
	
	public boolean pickAndExecuteAnAction() {
		
		if(changetype) {
			ChangePartType();
			return true;
		}
		
		if(purgenest) {
			PurgeNest();
			CheckForFull();
			return true;
		}
		
		if(picture) {
			SendCameraPicture();
			CheckForFull();
			return true;
		}
		
		synchronized(parts) {
			for(MyPart p:parts) {
				
				if(p.state == PartState.NEW) {
					CheckForFull();
					PutInNest(p);
					return true;
				}
				
				if(p.state == PartState.TOREMOVE) {
					//print("IN SCHEDULER OF PART TOREMOVE");
					RemoveFromNest(p);
					CheckForFull();
					return true;
				}
				
				if(p.state == PartState.BAD) {
					PurgeNestPart(p);
					CheckForFull();
					return true;
				}
			}
		}
		
		if(currenttype != typechange && numParts >= capacity-1) {
			PurgeNest();
			CheckForFull();
			return true;
		}
			
		return false;
	}
	
	// ____________ ACTIONS _____________________
	
	private void CheckForFull() {
		//print("The nest is checking to see if it is full");
		if(numParts >= capacity-1)
			lane.msgIAmNotFull(false);
		else
			lane.msgIAmNotFull(true);
		stateChanged();
	}
	
	private void ChangePartType() {
		print("The nest is telling the lane what part type it needs to change to");
		lane.msgChangeToThisPart(typechange);
		changetype = false;
		stateChanged();
	}
	
	private void PurgeNest() {
		print("The nest is purging all the parts");
		guinest.purgeNest();
		parts.removeAll(parts);
		numParts = 0;
		purgenest = false;
		stateChanged();
	}

	private void SendCameraPicture() {
		print("The nest is sending the camera a list of parts in the nest");
		ArrayList<AgentPart> partsinnest = new ArrayList<AgentPart>();
		synchronized(parts) {
			for(MyPart p:parts) {
				if(p.state == PartState.INNEST) {
					partsinnest.add(p.p);
				}
			}
		}
		camera.msgHereAreParts(partsinnest, this);
		picture = false;
		stateChanged();
	}

	private void PutInNest(MyPart p) {
		//print("The nest is taking the parts at the end of the lane and putting it into the nest");
		guinest.DoPutInNest(p.p.partgui);
		synchronized(parts) {
			for(MyPart mp:parts) {
				if(mp == p) {
					mp.state = PartState.INNEST;
				}
			}
		}
		numParts++;
		print("PUT IN NEST Nest size: " + numParts + " Nest capacity: " + capacity);
		camera.msgIHaveAnotherPart(numParts, this);
		stateChanged();
	}

	private void RemoveFromNest(MyPart p) {
		print("The nest is removing parts the part robot has taken");
		guinest.DoRemoveFromNest(p.p.partgui);
		parts.remove(p);
		numParts--;
		//print("REMOVEFROMNEST Nest size: " + numParts + " Nest capacity: " + capacity);
		stateChanged();
	}
	
	private void PurgeNestPart (MyPart p) {
		print("The nest is purging bad parts");
		guinest.purgePart(p.p.partgui);
		parts.remove(p);
		numParts--;
		//print("Nest size: " + numParts + " Nest capacity: " + capacity);
		stateChanged();
	}
	
	/**
	 * Animation Callback
	 *
	 */
	
	public void RemovePartFromNest(GuiCandy gc) {
		List<MyPart> toremove = new ArrayList<MyPart>();
		synchronized (parts) {
			for(MyPart mp:parts) {
				if(mp.p == gc.agent) {
					toremove.add(mp);
				}
			}
			
			for(MyPart mp:toremove) {
				parts.remove(mp);
				numParts--;
			}
		}
		stateChanged();
	}
	
	//_____________SETTERS FOR THE AGENTS & GUI__________________
	
	public void setLane(Lane lane) {
		this.lane = lane;
	}
	
	public void setLaneCamera(LaneCamera camera) {
		this.camera = camera;
	}
	
	public void setPartsRobot(PartsRobot partsrobot) {
		this.partsrobot = partsrobot;
	}
	
	public void setGuiNest(GuiNest guinest) {
		this.guinest = guinest;
	}
	
	public String toString() {
		return name;
	}
}