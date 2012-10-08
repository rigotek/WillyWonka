package engine.agents;

import java.util.*;

import structures.*;
import engine.*;
import engine.agents.KitStandAgent.mykit;
import engine.interfaces.*;
import gui.*;
import gui.holdable.*;
import gui.interfaces.*;

/**
 * Agent representation of the parts robot as a somewhat FSM. The nature of the parts robot
 * is to receive messages asynchronously from various cameras saying that more parts are available
 * and once it finally decides to act and retrieve those parts, it becomes a FSM in retrieving the 
 * parts and sending them to the correct kit.
 * 
 * @author Pierre Tasci
 */
public class PartsRobotAgent extends Agent implements PartsRobot {
	
	//---------------STRUCTURES-------------------------
	//The various states for the parts robot FSM
	public enum PartsRobotState {
		IDLE,
		GUIGOINGTOPART,
		HOLDINGPARTS,
		GUIHOLDINGPARTS,
		DELIVERINGPARTS,
		GUIDELIVERINGPARTS
	};
	public class Missing {
		PartType switchto;
		NestPart n;
		MissingState s;
		
		public Missing(PartType pt) {
			switchto = pt;
			s = s.IDLE;
		}
	}
	public enum MissingState { IDLE, CHANGING, DONE };
	
	/**
	 * Class to associate a nest with all of the known parts it contains
	 * 
	 * @author Pierre Tasci
	 */
	public class NestPart {
		public Nest nest;
		public List<AgentPart> parts;
		public PartType type;
		
		public NestPart(Nest nest2) {
			this.nest = nest2;
			parts = new ArrayList<AgentPart>();
			type = PartType.NOTYPE;
		}
		
		public boolean contains(Object o) {
			if(o == type)
				return true;
			else 
				return false;
		}
		
		public String toString() {
			return nest.toString();
		}
	}
	
	/**
	 * Class to designate the parts i am currently holding and
	 * information about them.
	 * 
	 * @author Pierre Tasci
	 *
	 */
	public class MyParts {
		public ArrayList<AgentPart> parts;
		public Nest source;
		public AgentKit dest;
		
		public MyParts() {
			parts = new ArrayList<AgentPart>();
		}
	}
	
	public class MyKit {
		public List<Missing> missing;
		public AgentKit kit;
		public ArrayList<AgentPart> pik;
		
		public MyKit(AgentKit newkit) {
			missing = new ArrayList<Missing>();
			kit = newkit;
			pik = new ArrayList<AgentPart>();
		}
	}
	
	//---------------DATA-------------------------------
	private String name;
	public PartsRobotState state;
	public List<NestPart> nestparts;
	public MyParts myparts;
	public MyKit current;
	public MyKit waiting;
	public GuiPartsRobot guirobot;
	public KitStand kitstand;
	public ArrayList<PartType> queried;
	
	/**
	 * Public constructor for the robot
	 */
	public PartsRobotAgent(String name, List<Nest> nests) {
		this.name = name;
		nestparts = new ArrayList<NestPart>();
		//print("I have " + nests.size() + " regular nests");
		for(int i = 0; i < nests.size(); i++) {
			nestparts.add(new NestPart(nests.get(i)));
		}
		//print("I have " + nestparts.size() + " nests");
		//missing = new ArrayList<Missing>();
		myparts = new MyParts();
		state = PartsRobotState.IDLE;
		queried = new ArrayList<PartType>();
	}
	
	public void setGuiPartsRobot(GuiPartsRobot gr) {
		this.guirobot = gr;
	}
	
	public void setKitStand(KitStand kt) {
		this.kitstand = kt;
	}

	//---------------MESSAGES---------------------------
	/**
	 * Message from the kitting stand to the parts robot telling him
	 * which kits he needs to make so that the parts robot can 
	 * optimize his time usage and which kits he picks up
	 * 
	 * @param kit The Kit that the stand needs to fulfill
	 */
	public void msgINeedThese(AgentKit kit, KitStand kitstand) {
		print("I have recieved another kit from the kitstand");
		MyKit np = new MyKit(kit);
		if(current != null) {
			waiting = np;
			//print("Also, second kit needs " + waiting.kit.config.getType(PartType.TYPE1));
		}
		else {
			current = np;
		}
		
		for(PartType p : kit.config.configuration().keySet()) {
			Missing miss = new Missing(p);
			//print("Checking if I am missing "+p.toString());
			np.missing.add(miss);
			if(queried.contains(p)) {
				np.missing.remove(miss);
				//print("I have already queried for it. Not missing.");
				break;
			}
			//There is a nest that has that type so it is not missing
			for(NestPart nest : nestparts) {
				if(nest.type == p) {
					//print("I have a nest that has this part. Not missing.");
					np.missing.remove(miss);
					break;
				}
			}	
		}
		
		stateChanged();
	}
	
	/**
	 * Message from the lane camera to the robot that he has usable parts
	 * that the robot might want to know about.
	 * 
	 * @param gp A list of parts that the nest has available that I don't know about already
	 * @param nt The nest to which these parts belong
	 */
	public void msgIHaveGoodParts(List<AgentPart> gp, Nest nt, LaneCamera camera) {
		print(camera.toString() + "has notified me that " + nt.toString() + " has usable parts for me.");
		
		for(int i = 0; i < nestparts.size(); i++) {
			if(nestparts.get(i).nest == nt) {
				nestparts.get(i).parts = new ArrayList<AgentPart>(gp);
				nestparts.get(i).type = gp.get(0).type;
				int remove = 0;
				boolean doremove = false;
				//print("I am missing " + mykits.get(0).missing.size() + " parts types");
				if(current != null) {	
					if(current.missing.size() > 0) {
						for(Missing m : current.missing) {
							if(m.switchto.equals(gp.get(0).type)) {
								remove = current.missing.indexOf(m);
								doremove = true;
							}
						}
						if(doremove) {
							current.missing.remove(remove);
						}
					}
				}
			}
		}
		stateChanged();
	}
	
	/**
	 * Message from the gui that the animation to go retrieve the parts from the
	 * nests has completed.
	 */
	public void msgPartsReceived() {
		print("GuiPartsRobot has finished picking up parts");
		state = state.GUIHOLDINGPARTS;
		stateChanged();
	}
	
	/**
	 * Message from the gui that the animation to send the parts and dump it in the 
	 * kit on the kit stand has completed.
	 */
	public void msgPartsSent() {
		print("GuiPartsRobot has finished placing parts in kit");
		state = state.DELIVERINGPARTS;
		stateChanged();
	}
	
	//---------------SCHEDULER--------------------------
	public boolean pickAndExecuteAnAction() {
		/*if(mykits.size() > 1) {
			print("Upper PAEA: "+mykits.get(1).name + " needs: "+mykits.get(1).kit.config.configuration().get(PartType.TYPE1));
		}*/
		//print("I am currently in the scheduler.");
		if(state == state.GUIHOLDINGPARTS) {
			notifyPartsTaken();
			return true;
		}
		
		if(myparts != null && myparts.parts.size() > 0 && state == state.HOLDINGPARTS) {
			deliverParts();
			return true;
		}
			
		if(state == state.DELIVERINGPARTS) {
			sendParts();
			return true;
		}
		
		if(state != state.IDLE) {
			//we are not in a state that performs an action but we are in the state machine so don't do anything else.
			return false;
		}
		
		//print("I am not in the state machine, I could be missing parts");
		
		//If we are not currently in the FSM mode, we need to first see if there is any lanes that need part type changes
		if(current != null) {	
			for(Missing m : current.missing) {
				print("I am missing " + current.missing.size() + " this many types");
				if(m.s == MissingState.IDLE) {
					switchType(current.missing.get(0));
					break;
					//return true;
				}
			}
		}
				
		//tries with the first kit
		if(current != null) {
			/*if(mykits.size() > 1) {
				print(mykits.get(1).name + " needs: "+mykits.get(1).kit.config.configuration().get(PartType.TYPE1));
			}*/
			//print("I am now checking to see if I should go ask for parts because I have " + mykits.size() + " kits");
			//go through each part type to see if it needs to be filled
			for(PartType p : current.kit.quantities.keySet()) {
				int count = current.kit.quantities.get(p);
				//print("Count for " + p.toString()+": "+count);
				//this part type needs to be filled by the current kit
				if(count > 0) {
					//print("Nest Parts size = " + nestparts.size());
					//iterate through all the nests to see which nest is providing that part type
					for(NestPart nest : nestparts) {
						//checks if this nest satisfies the part type we need
						//print(nest.type.toString() + " = " + p.toString());
						if(nest.type == p) {
							//print("found the correct nest type");
							//it is the right type of part, now do we have a enough
							if(nest.parts.size() > 4 && count >= 4)  {
								//we have enough parts of the right type, ask for those parts
								askForParts(current,nest,4);
								return true;
							}
							else if(nest.parts.size() > 0) {
								//we don't have everything we need to satisfy but try to fulfill what we have so far
								askForParts(current,nest,Math.min(nest.parts.size(), count));
								return true;
							}
						}
					}
				}
			}
		}
		
		//print("Going to Sleep");
		//This should never happen in a FSM, that means we have an error unless we have not established FSM yet
		return false;
	}
	
	//----------------ACTIONS-----------------------------
	private void notifyPartsTaken() {
		print("Telling " + myparts.source.toString() + " that I have taken some parts from him");
		state = state.HOLDINGPARTS;
		myparts.source.msgINeedTheseParts(myparts.parts);
		stateChanged();
	}
	
	private void deliverParts() {
		print("Telling GuiPartsRobot to send parts to kit");
		state = state.GUIDELIVERINGPARTS;
		ArrayList<GuiCandy> tempparts = new ArrayList<GuiCandy>();
		for(int i = 0; i < myparts.parts.size(); i++) {
			tempparts.add(myparts.parts.get(i).partgui);
		}
		guirobot.DoSendParts(((KitStandAgent) (kitstand)).guicheckingstation, myparts.dest.guikit, tempparts);
	}
	
	private void sendParts() {
		print("Telling kitstand that I have put some parts in one of his kits.");
		//print("Sending " + myparts.parts.size() + " to " + current.kit.toString());
		//*doGoToIdle();*
		AgentKit working = myparts.dest;
	/*	if(waiting != null) {
			print("Dump "+ working.id + " instead of "+ waiting.kit.id);
		}*/
		working.dumpParts(myparts.parts);
	/*	if(waiting != null) {
			print("Also, kit "+ waiting.kit.id +" needs " + waiting.kit.config.getType(PartType.TYPE1));
		}*/
		kitstand.msgGiveParts(myparts.parts, working);
		if(working.complete) {	
			if(waiting != null) {
				current = waiting;
				waiting = null;
			}
			else {
				current = null;
			}
		}
		myparts.parts.clear();
		myparts.dest = null;
		myparts.source = null;
		state = state.IDLE;
		stateChanged();
	}
	
	private void askForParts(MyKit kit,NestPart nest,int count) {
		/*if(mykits.size() > 1) {
			print("Start of ask: "+mykits.get(1).name + " needs: "+mykits.get(1).kit.config.configuration().get(PartType.TYPE1));
		}*/
		print("Telling GuiPartsRobot to go retrieve some parts from " + nest.toString());
		//print("This nest has "+nest.parts.size()+" parts of type "+nest.type + " but I need "+count+" parts");
		ArrayList<AgentPart> nparts = new ArrayList<AgentPart>();
		ArrayList<GuiCandy> gnparts = new ArrayList<GuiCandy>();
		for(int i = 0; i<count; i++) {
			gnparts.add(nest.parts.get(0).partgui);
			nparts.add(nest.parts.remove(0));
		}
		print("I am passing along "+nparts.size()+" parts");
		guirobot.DoRetrieveParts(((NestAgent)(nest.nest)).guinest, gnparts);
		myparts.parts.addAll(nparts);
		myparts.source = nest.nest;
		myparts.dest = kit.kit;
		state = state.GUIGOINGTOPART;
		/*if(mykits.size() > 1) {
			print("End of ask: "+mykits.get(1).name + " needs: "+mykits.get(1).kit.config.configuration().get(PartType.TYPE1));
		}*/
		stateChanged();
	}
	
	private void switchType(Missing sw) {
		//missing.remove(sw);
		//print("Switching lane type to "+ sw.switchto.toString());
		boolean nextnest = false;
		for(NestPart n : nestparts) {
			nextnest = false;
			for(PartType p : current.kit.config.types) {
				if(n.type == p) {
					//this nest has a part type that is needed, keep it 
					print("Nest "+n.toString()+" is needed for "+p.toString());
					nextnest = true;
				}
			}
			if(!nextnest) {
				//we have found a nest that is not needed, lets tell them to change their type
				print("Nest "+ n.toString() + " is not needed, change to "+sw);
				sw.n = n;
				sw.s = MissingState.CHANGING;
				//print("I am missing "+ sw.switchto.toString() + " and its state is "+ sw.s.toString());
				queried.add(sw.switchto);
				n.nest.msgChangeToThisPart(sw.switchto);
				print("Switching " + n.nest.toString() + " type to "+ sw.switchto.toString());
				current.missing.remove(sw);
				n.type = sw.switchto;
				return;
			}
		}
	}
	
	private void goToIdle() {
		print("Going to Idle");
		guirobot.DoGoToIdle();
	}
	
	//-----------------OTHER-------------------------------
	public String toString() {
		return name;
	}
	
	/**
	 * Search function to see if any of the nest are currently producing this part type
	 */
	public NestPart searchNest(PartType t) {
		for(int i = 0; i < nestparts.size(); i++) {
			if(nestparts.get(i).type.equals(t)) {
				return nestparts.get(i);
			}
		}
		return null;
	}
}
