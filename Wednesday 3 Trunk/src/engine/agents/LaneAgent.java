package engine.agents;

import java.util.*;

import structures.*;
import engine.*;
import engine.interfaces.*;
import gui.GuiLane;
import gui.holdable.GuiCandy;

/** Agent representation of the Lane in the Kitting Cell Factory.
 * This agent is responsible for notifying the feeder of how many spots for parts are open on the 
 * lane whenever the feeder asks, and for holding the parts the feeder gives to travel down
 * the lane. It also interacts with the nest by placing parts into the nest after the parts have 
 * reached the end of the lane, and as long as the nest is not full. The agent also interacts with 
 * the lane animation by telling it when to animate a part traveling down the lane.
 * 
 * @author Dana Li
 *
 */

public class LaneAgent extends Agent implements Lane {
	
	// ____________ DATA _____________________
	
	String name;
	public int size;
	int capacity;
	public boolean nestopen;
	public boolean partwaiting;
	public boolean changetype;
	public boolean nestpurge;
	public boolean timerreset;
	
	private Timer timer = new Timer();
	private TimerTask task;
	
	private GuiLane guilane;
	
	PartType typechange;
	PartType currenttype;

	Feeder feeder;
	Nest nest;
	
	enum LaneState {
		IDLE,
		RUNNING,
		NEEDSREFILL
	}
	
	LaneState state;

	enum PartState {
		FROMFEEDER,
		ONLANE,
		ENDOFLANE
	};

	public class MyPart {
		public AgentPart p;
		public PartState state;
		
		MyPart (AgentPart p, PartState ps) {
			this.p = p;
			state = ps;
		}
	}

	public List<MyPart> parts = Collections.synchronizedList(new ArrayList<MyPart>());
	
	// ____________ CONSTRUCTOR ______________________
	
	public LaneAgent(String name, int capacity)
	{
		this.name = name;
		size = 0;
		this.capacity = capacity;
		nestopen = true;
		partwaiting = false;
		changetype = false;
		nestpurge = true; 
		timerreset = true;
		state = LaneState.IDLE;
	}
	
	
	// _____________ MESSAGES ___________________________
	
	/**
	 * This message is sent by the feeder when it wants to know how many spots are open on the lane.
	 */
	public void msgRequestNumberOfOpenSpots() {
		print("The feeder is requesting to know the number of spots open in the lane");
		partwaiting = true;
		stateChanged();
	}
	
	/**
	 * This message is sent by the feeder whenever it contains parts to give to the lane and it knows 
	 * the lane is not yet full. 
	 */
	public void msgPartForLane(AgentPart p) {
		//print("The feeder is sending a part to the lane");
		MyPart mp = new MyPart(p, PartState.FROMFEEDER);
		parts.add(mp);
		currenttype = p.type;
		stateChanged();
	}

	/**
	 * This message is sent by the nest each time the nest's scheduler runs so the lane knows whether 
	 * it can continue to give parts to the nest or not.
	 */
	public void msgIAmNotFull(Boolean b) {
		//print("The nest is notifying the lane that it is not full: " + b);
		nestopen = b;
		stateChanged();
	}
	
	/**
	 * This message is sent by the nest when the part type needs to change, and will trickle back
	 * to the feeder and gantry.
	 */
	
	public void msgChangeToThisPart(PartType type) {
		print("The nest is notifying the lane to change to this part type: " + type);
		changetype = true;
		typechange = type;
		stateChanged();
	}

	// ______________ SCHEDULER _______________________
	
	public boolean pickAndExecuteAnAction() {
		
		if(changetype) {
			ChangePartType();
			return true;
		}
		
		if(partwaiting) {
			AcceptParts();
			return true;
		}
		
		synchronized(parts) {
			for(MyPart p:parts) {
				
				if(p.state == PartState.FROMFEEDER) {
					MoveToLane(p);
					return true;
				}
				
				if(p.state == PartState.ENDOFLANE && nestopen == true) {
					GiveToNest(p);
					return true;
				}
			}
		}
		
		if(state == LaneState.RUNNING && parts.size() == 0 && timerreset) {
			resetTimer();
			//RequestRefill();
			return true;
		}
		
		if(size >= capacity-10 && nestpurge) {
			NestShouldPurge();
			return true;
		}
		
		return false;
	}
	
	// _________________ ACTIONS __________________________
	
	private void ChangePartType() {
		print("The lane is going to tell the feeder to change to new part type " + typechange);
		feeder.msgChangeToThisPart(this, typechange);
		changetype = false;
		stateChanged();
	}
	
	private void RequestRefill() {
		print("The lane is running low and requesting more parts from feeder of type " + currenttype);
		state = LaneState.NEEDSREFILL;
		feeder.msgChangeToThisPart(this, currenttype);
		timerreset = true;
		stateChanged();
	}
	
	private void AcceptParts() {
		print("The lane is telling the feeder the number of spots open it has");
		feeder.msgLaneSpotsOpen(this, capacity-size);
		partwaiting = false;
		stateChanged();
	}

	private void MoveToLane(MyPart p) {
		//print("The lane is taking a part from the feeder and placing it on the lane");
		guilane.DoMoveToLane(p.p.partgui);
		if(task != null) {
			task = null;
			print("Timertask being canceled");
		}
		state = LaneState.RUNNING;
		synchronized(parts) {
			for(MyPart mp:parts) {
				if(mp == p) {
					mp.state = PartState.ONLANE;
				}
			}
		}
		size++;
		stateChanged();
	}

	private void GiveToNest(MyPart p) {
		//print("The lane is giving parts at the end of the lane to the nest");
		guilane.DoRemoveFromLane(p.p.partgui);
		nest.msgNextPart(p.p);
		parts.remove(p);
		size--;
		nestpurge = true;
		stateChanged();
	}
	
	private void NestShouldPurge() {
		nest.msgPurgeTheNest();
		nestpurge = false;
		stateChanged();
	}
	
	/**
	 * ANIMATION CALLBACK
	 * @param p
	 */
	public void DonePartMoving(GuiCandy gc) {
		//print("The animation notifies the agent that the part has moved to the end of the lane");
		synchronized (parts) {
			for(MyPart mp:parts) {
				if(mp.p == gc.agent) {
					mp.state = PartState.ENDOFLANE;
				}
			}
		}
		stateChanged();
	}
	
	public void RemovePartFromLane(GuiCandy gc) {
		List<MyPart> toremove = new ArrayList<MyPart>();
		synchronized (parts) {
			for(MyPart mp:parts) {
				if(mp.p == gc.agent) {
					toremove.add(mp);
				}
			}
			
			for(MyPart mp:toremove) {
				parts.remove(mp);
				print("From GuiNest - removing a part fallen off lane");
				size--;
			}
		}
		stateChanged();
	}
	
	private void resetTimer() {
		if (task != null)
			task.cancel();
		task = getTimerTask();
		timer.schedule(task, 6000);
		print("Reset the refill timer");
		timerreset = false;
		stateChanged();
	}
	
	private TimerTask getTimerTask() {
		TimerTask reset = new TimerTask() {
			public void run() {
				RequestRefill();
			}
		};
		return reset;
	}
	

	public TimerTask getTask() {
		return task;
	}

	
	//___________SETTERS FOR AGENTS & GUI_______________
	
	public void setFeeder(Feeder feeder)
	{
		this.feeder = feeder;
	}
	
	public void setNest(Nest nest)
	{
		this.nest = nest;
	}
	
	public void setGuiLane(GuiLane guilane)
	{
		this.guilane = guilane;
	}
	
	public GuiLane getGuiLane() {
		return guilane;
	}
	
	public String toString() {
		return name;
	}
	
}