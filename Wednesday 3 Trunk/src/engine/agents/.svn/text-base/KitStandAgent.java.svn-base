package engine.agents;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import java.util.TimerTask;

import structures.*;
import engine.Agent;
import engine.interfaces.*;
import gui.GuiCheckingStation;
import gui.holdable.GuiCandy;
import gui.locationsimg.Locations;

public class KitStandAgent extends Agent implements KitStand {
	String name;
	KitRobot kr;
	public mykit kita,kitb;
	public enum STATE {NO_ACTION,RECEIVE_KIT,RECEIVING_PARTS,DONE}
	PartsRobot pr;
	GuiCheckingStation guicheckingstation;
	
	public class mykit {
		public AgentKit agentkit;
		public STATE state;
		public ArrayList<AgentPart> parts;
		public ArrayList<GuiCandy> candy;
		
		public mykit() {
			state = state.NO_ACTION;
			parts = new ArrayList<AgentPart>();
			candy = new ArrayList<GuiCandy>(8);
		}
	}
	
	public KitStandAgent(String n){
		name = new String(n);
		kita = new mykit();
		kitb = new mykit();
		guicheckingstation = new GuiCheckingStation(Locations.checkingStationX, Locations.checkingStationY);
		guicheckingstation.SetKitStand(this);
	}
	
	public void setKitRobot(KitRobot kra){
		kr = kra;
	}
	
	public void setPartsRobot(PartsRobot pra){
		pr = pra;
	}
	
	public void msgHereIsNextAgentKit(AgentKit ak){
		print("Received a new kit from the kit robot");
		if(kita.agentkit == null) {
			kita.agentkit = ak;
			kita.state = STATE.RECEIVE_KIT;
		}
		else if(kitb.agentkit == null) {
			kitb.agentkit = ak;
			kitb.state = STATE.RECEIVE_KIT;
		}
		//agentkit = ak;
		//state = state.CHECKING_KIT;
		//pr.msgINeedThese(ak, this);
		//state = state.RECEIVE_KIT;
		//guicheckingstation.DoRecieveKit(agentkit.guikit);
		stateChanged();
	}
	
	public void msgGiveParts(ArrayList<AgentPart> p, AgentKit kit){
		print("Received more parts from the parts robot");
		if(kita.agentkit == kit) {
			kita.parts.addAll(p);
			for(AgentPart candy: p) {
				kita.candy.add(candy.partgui);
				//kitb.agentkit.guikit.fillKit(kitb.candy);
			}
			kita.state = STATE.RECEIVING_PARTS;
		}
		else if(kitb.agentkit == kit) {
			kitb.parts.addAll(p);
			for(AgentPart candy: p) {
				kitb.candy.add(candy.partgui);
				//kitb.agentkit.guikit.fillKit(kitb.candy);
			}
			kitb.state = STATE.RECEIVING_PARTS;
		}
		guicheckingstation.DoRecieveParts(null);
		stateChanged();
	}
	
	@Override
	protected boolean pickAndExecuteAnAction() {
		if(kita.state == STATE.RECEIVE_KIT) {
			bringKit(kita,1);
			return true;
		}
		else if(kitb.state == STATE.RECEIVE_KIT) {
			bringKit(kitb,2);
			return true;
		}
		
		if(kita.state == STATE.RECEIVING_PARTS) {
			receiveParts(kita,1);
			return true;
		}
		else if(kitb.state == STATE.RECEIVING_PARTS) {
			receiveParts(kitb,2);
			return true;
		}

		return false;
	}	
	
	public void bringKit(mykit kit,int alpha) {
		print("Telling gui to center kit");
		guicheckingstation.DoRecieveKit(kit.agentkit.guikit,alpha);
		/*for(PartType p : kit.agentkit.config.configuration().keySet()) {
			print("The kit I am about to send needs "+kit.agentkit.config.configuration().get(p) + " of " + p.toString());
		}*/
		//print("I am going to send kit: " + kit.agentkit.toString());
		
		kit.state = STATE.NO_ACTION;
		pr.msgINeedThese(kit.agentkit, this);
		
		//final AgentKit newkit = kit.agentkit;
		
		//this is for the sole purpose of testing for V0
		/*Timer t = new Timer();
		t.schedule(new TimerTask(){
			public void run() {
				msgGiveParts(newkit.fillParts(), newkit);	
			}
		}, 2000);*/
	}
	
	public void receiveParts(mykit kit, int alpha) {
		print("Received parts from the parts robot.");
		print("Giving "+kit.candy.size() + " candies to gui");
		kita.state = STATE.NO_ACTION;
		kit.agentkit.guikit.fillKit(kit.candy);
		kit.state = STATE.NO_ACTION;
		if(kit.agentkit.complete) {
			//guicheckingstation.NullKit(kit.agentkit.guikit);
			kr.msgKitIsDone(kit.agentkit);
			kit.agentkit = null;
			kit.candy.clear();
			kit.parts.clear();
		}
	}
	
	/*public void checkKitCompletion(){
		//change AgentKit state to done
		if(agentkit.dumpParts(parts)){
			state = state.KIT_DONE;
			stateChanged();
		}
	}
	
	public void kitDone(){
		kr.msgKitIsDone(agentkit);
		state = state.NO_ACTION;
		stateChanged();
	}*/

	public void setGuiCheckingStation(GuiCheckingStation gcs){
		guicheckingstation = gcs;
	}
	
	public String toString(){
		return name;
	}
	
	public void DoneReceivingKit(){
		stateChanged();
	}
}


