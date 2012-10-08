package engine.agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import structures.*;
import engine.Agent;
import engine.interfaces.*;
import gui.GuiBoat;
import gui.GuiFeeder;
import gui.GuiGantryRobot;
import gui.holdable.GuiBin;
import gui.interfaces.Gui;
import gui.locationsimg.Locations;
import gui.GuiBoat;

public class GantryAgent extends Agent implements Gantry{

	String name;
	GuiGantryRobot guigantry;
	GuiBoat boat;
	ArrayList<myBin> bins;
	myBin tmpbin;
	GuiBin tmpguibin;
	
	boolean atTurningPoint = false;
	boolean done = false;
	
	ArrayList<Gui> guilist = new ArrayList<Gui>();

	enum myBinState { ReturnAndRequest, ReturnBin, RequestBin, None }

	myBinState gantryState = myBinState.None;
	
	enum STATE {NO_ACTION,C_BIN, R_BIN, W_BIN};
	STATE state;
	
	public class myBin{
		myBinState binState;
		AgentBin bin;
		Feeder feeder;
		PartType part;
		boolean binfilled, binloaded, haveBin;
				
		public myBin(Feeder f, PartType p, AgentBin b){
			bin = b;
			feeder = f;
			part = p;
			binfilled = false;
			binloaded = false;
			haveBin = false;
		}
	
	public myBin(Feeder f, PartType p, AgentBin b, boolean t){
		bin = b;
		feeder = f;
		part = p;
		binfilled = false;
		binloaded = false;
		haveBin = t;
	}
	
	public myBin(Feeder f, PartType p, AgentBin b, myBinState t){
		bin = b;
		feeder = f;
		part = p;
		binState = t;
	}
	
	public myBin(myBin mb){
		this.bin = mb.bin;
		this.feeder = mb.feeder;
		this.part = mb.part;
		this.binfilled = mb.binfilled;
		this.binloaded = mb.binloaded;
		this.haveBin = mb.haveBin;
		this.binState = mb.binState;
	}
	
	
}
	
	public GantryAgent(String n){
		name = new String(n);
		bins = new ArrayList<myBin>();
		state = state.NO_ACTION;
	}
	
	public void msgINeedThisPartType(Feeder f, PartType parttype){
		print("Gantry has received request to create a bin of type " +
				parttype.toString());
		bins.add(new myBin(f,parttype,new AgentBin(),myBinState.RequestBin));
		print("size of the new bins: " + bins.size() );
		stateChanged();
		//should work as before by getting a bin from nowhere
	}
	
	public void msgReturnAndRequestBin(Feeder f, AgentBin b, PartType pt){
		print("Received request of bin from " + f.toString());
		bins.add(new myBin(f,pt,b, myBinState.ReturnAndRequest));
		print("updated bin");
		print("size of the new bins: " + bins.size() );
		stateChanged();
		//should bring a bin back and should get a new bin and return to feeder
	}
	
	public void msgReturnBin(Feeder f, AgentBin b) {
		print("Received message to pick up a bin from " + f.toString() +
			"and NOT send a new bin.");
		bins.add(new myBin(f, null, b, myBinState.ReturnBin));
		stateChanged();
		//at this point, want to get the boat to pick up the bin and leave.  that's it.
	}
	
	
	public boolean createBin(myBin mb){
				
		mb.bin = new AgentBin();
		
		boat.doBringBinToGantry();
		tmpbin = mb;

		return true;
	}
	
	public void refillBin(myBin mb){

		print("Gantry is refilling a bin.");
		guigantry.DoRetrieveBin(mb.bin.guibin(), boat);
		mb.bin.refillParts(mb.part, 20);
		mb.binfilled = true;
		tmpbin = new myBin(mb);
	}

	public void loadBin(Feeder f){
		//loads Bin in Feeder
		guigantry.DoLoadBin(((FeederAgent) f).getFeederGui());
		state = state.W_BIN;
		stateChanged();
	}
	
	
	protected boolean pickAndExecuteAnAction() {
		
		if (atTurningPoint == true) {
			atTurningPoint = false;
			if (tmpbin.binState == myBinState.ReturnBin) {
				//you should stop here...
				gantryState = myBinState.None;
				return true;
			}
			else if ((tmpbin.binState == myBinState.RequestBin) || (tmpbin.binState == myBinState.ReturnAndRequest)) {
				refillBin(tmpbin);
				return true;
			}
		}
		
		if (done == true) {
			done = false;
			gantryState = myBinState.None;
			return true;
		}
		
		if (gantryState == myBinState.None) {
			for (myBin mb:bins) {
				tmpbin = new myBin(mb);
				bins.remove(mb);
				if (mb.binState == myBinState.RequestBin) {
					gantryState = myBinState.RequestBin;
					//need to get a new bin
					createBin(mb);
					return true;
				}
				else if (mb.binState == myBinState.ReturnAndRequest) {
					gantryState = myBinState.ReturnAndRequest;
					pickUpBin(mb);
					return true;
				}	
				else if (mb.binState == myBinState.ReturnBin) {
					gantryState = myBinState.ReturnBin;
					pickUpBin(mb);
					return true;
				}
			}
		}
		
		return false;		
	}
	public void setGuiGantryRobot(GuiGantryRobot ggr){
		guigantry = ggr;
	}
	
	public void setGuiList(ArrayList<Gui> guiList) {
		this.guilist = guiList;
	}
	
	public void setGuiBoat(GuiBoat gb){
		boat = gb;
	}
	
	public String toString(){
		return name;
	}
	
	public void DoneRefillingBin(){
		print("The GuiGantry has finished refilling the bin.");
		loadBin(tmpbin.feeder);		
	}
	
	public void DoneLoadingBin(){
		tmpbin.feeder.msgHereIsBin(tmpbin.bin);
		state = state.NO_ACTION;
		done = true;
		guigantry.DoGoToIdle();
		stateChanged();
	}
	
	public void RefillBoat(){
		if ((tmpbin.binState == myBinState.RequestBin) || (tmpbin.binState == myBinState.ReturnAndRequest)) {
			boat.doBringBinToGantry();
		}
		else {
			done = true;
		}
	}
	
	//added to return bin
	public void pickUpBin(myBin mb) {
		boat.doBringEmptyBoatToGantry();
		guigantry.DoReturnEmptyBin(mb.bin.guibin(), ((FeederAgent) mb.feeder).getFeederGui());
	}
	
	public void DonePurgingBin() { //will be called by the gantry gui
		boat.doReceiveBinFromGantry(tmpbin.bin.guibin());
		guigantry.DoGoToIdle();
		state = state.NO_ACTION;
		stateChanged();
	}

	public void DoneMovingToUnload(GuiBin gb){
		tmpbin.bin.setGuiBin(gb);
		atTurningPoint = true;
		stateChanged();
	}
//	
//	//TODO: Implement drain and wave non-norm methods, call them in boat as well
//	public void wave(){
//		
//	}
//	
//	public void drain(){
//		
//	}
	
}
