//Alberto Marroquin

package engine.agents;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import structures.AgentKit;
import structures.Configuration;
import structures.KitPosition;

import engine.Agent;
import engine.interfaces.KitCamera;
import engine.interfaces.KitRobot;
import engine.interfaces.KitStand;
import gui.GuiBoxBelt;
import gui.GuiCamera;
import gui.GuiKitBoat;
import gui.GuiKitRobot;
import gui.holdable.GuiKit;

public class KitRobotAgent extends Agent implements KitRobot{
	boolean guiEnable = true;//Control the GUI when JUnit testing.
	boolean isAvailable = true;
	GuiKitBoat boatGui;
	GuiBoxBelt KitStationGUI;
	GuiKitRobot kitRobotGUI;
	//GuiCamera kitCameraGUI;
	KitStand kitStand;
	KitCamera kitCamera;
	List<MyKit> myKits;
	MyKitStand myStand;
	Semaphore guiControl = new Semaphore(0,true);
	
	class MyKitStand{	 //Serves as a monitor. It monitors the kit stand's slots.
		private boolean slotOneTaken = false, slotTwoTaken = false;
		private boolean slotThreeTaken = false;
		
		public void setSlotTaken(int slot){
			if(slot == 1){
				slotOneTaken = true;			
			}
			else if(slot == 2){
				slotTwoTaken = true;
			}
		}
		
		public void setSlotAvilable(int slot){
			if(slot == 1){
				slotOneTaken = false;			
			}
			else if(slot == 2){
				slotTwoTaken = false;
			}
		}
		
		public int getAvailableSlotNum(){
			if(!slotOneTaken){
				return 1; 
			}
			else if(!slotTwoTaken){
				return 2;
			}
			else{
				print("This should never happen: If no slot is available, this method" +
						"shouldn't be called");
				return 0;
			}
		}
		 
		public void setSlotThreeTaken(){
			this.slotThreeTaken = true;
		}
		public boolean isEmpty(){
			if(slotOneTaken == false && slotTwoTaken == false)
				return true;
			return false;
		}
		
		public void setSlotThreeAvailable(){
			this.slotThreeTaken = false;
		}
		
		public boolean isSlotThreeTaken(){
			return slotThreeTaken;
		}
		
		public boolean isKitStandFull(){
			if(slotOneTaken && slotTwoTaken)
				return true;
			else
				return false;
		}
	}
	
	enum KitStatus  { Unknown, Available, Ready_For_Inspection, Ready_For_Delivery, On_KitStand, 
		On_CameraInspection, Being_Animated,Gone, Bad_Kit};
	
	class MyKit
	{
		AgentKit kit;
		KitStatus status;
		public int slot= 0;
		
		public MyKit(AgentKit kit){
			this.kit = kit;
			status = KitStatus.Unknown;
		}
	}
	String name;
	public KitRobotAgent(String name){
		this.name = name;
		myKits =  new LinkedList<MyKit>();
		myStand = new MyKitStand();
	}
	
	//////////////////Messages///////////////////////////////////
	/**
	 * This method gets a configuration for a Kit, and it creates a kit according to the specification.
	 */
	public synchronized void msgHereIsKitConfiguration(Configuration c) {
		AgentKit k = new AgentKit(c,(int)(Math.random()*1000));
		MyKit mk = new MyKit(k);
		mk.status = KitStatus.Available;
		myKits.add(mk);
		stateChanged();
	}
	
	/**
	 * The Kit Stand Agent will  notify that the Kit Robot about the a finished Kit 
	 */
	public void msgKitIsDone(AgentKit kit) {
		for(MyKit mk: myKits){
			if(mk.kit.config.equals(kit.config) && mk.kit.position.equals(kit.position)){
				mk.status = KitStatus.Ready_For_Inspection;
				stateChanged();
				return;
			}
		}
	}
	/**
	 * The Kit Camera Agent will notify that the Kit passed inspection.
	 */
	public void msgKitIsGood(AgentKit kit) {
		for(MyKit mk: myKits){
			if(mk.kit.equals(kit)){
				mk.status = KitStatus.Ready_For_Delivery;
				stateChanged();
				return;
			}
		}
	}
	/**
	 * The Kit Camera Agent will notify that the Kit failed inspection.
	 */
	public void msgKitIsBad(AgentKit kit) {
		for(MyKit mk: myKits){
			if(mk.kit.equals(kit)){
				mk.status = KitStatus.Bad_Kit;
				GuiKit k = mk.kit.guikit;
				k.clearKit();
				mk.kit = new AgentKit(mk.kit.config, mk.kit.position,mk.kit.name);
				mk.kit.guikit = k;
				stateChanged();
				return;
			}
		}
	}
	/**
	 * The GUI will notify that the requested animation is done. This will wake up the agent.
	 */
	public void msgAnimationDone() {
		//print("done Animating!!!!!");
		guiControl.release();
		
	}
	//////////////////Scheduler///////////////////////////////////
	public boolean pickAndExecuteAnAction() {
		synchronized(myKits){
			for ( MyKit mk : myKits){
				if(mk.status.equals(KitStatus.Available) && !myStand.isKitStandFull() && !myStand.isSlotThreeTaken() 
						|| mk.status.equals(KitStatus.Available) && myStand.isEmpty()){
					placeKitOnStand(mk);
					return true;
				}
			}
		}
		
		synchronized(myKits){
			for ( MyKit mk : myKits){
				if(mk.status.equals(KitStatus.Bad_Kit) && !myStand.isKitStandFull()){
					moveKitBackToStand(mk);
					return true;
				}
			}
		}
		
		synchronized(myKits){
			for ( MyKit mk : myKits){
				if(mk.status.equals(KitStatus.Ready_For_Inspection) && !myStand.isSlotThreeTaken()){
					placeKitOnInspection(mk);
					return true;
				}
			}
		}
		
		synchronized(myKits){
			for ( MyKit mk : myKits){
				if(mk.status.equals(KitStatus.Ready_For_Delivery)){
					placeKitOnDeliveryBoat(mk);
					return true;
				}
			}
		}
		return false;
	}
	
	///////////////////Actions///////////////////////////////////
	private void moveKitBackToStand(MyKit mk){
		if(myStand.getAvailableSlotNum() == 1){
			mk.kit.setPosition(KitPosition.POSITION1);
		}
		else if(myStand.getAvailableSlotNum() == 2){
			mk.kit.setPosition(KitPosition.POSITION2);
		}
		mk.slot = myStand.getAvailableSlotNum();
		print("Kit Slot number : "+mk.slot+" is Taken");
		myStand.setSlotTaken(myStand.getAvailableSlotNum());
		if(guiEnable){
			mk.status = KitStatus.Being_Animated;
			this.kitRobotGUI.DoMoveKitBackToStand(mk.kit.guikit, mk.kit.position);
			try {
				//guiControl =  new Semaphore(0,true);//TODO: take away after gui fixes it
				this.guiControl.acquireUninterruptibly();
				print("done with kit robot");
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		else
			print("Placing kit back into Stand");
		
		myStand.setSlotThreeAvailable();
		mk.status = KitStatus.On_KitStand;
		kitStand.msgHereIsNextAgentKit(mk.kit);
		stateChanged();
	}
	/**
	 * Tell GUI to create a Kit and tell GUI to move Kit to kit stand.
	 */
	private void placeKitOnStand(MyKit mk) //MultiTask
	{
		if(myStand.getAvailableSlotNum() == 1){
			mk.kit.setPosition(KitPosition.POSITION1);
		}
		else if(myStand.getAvailableSlotNum() == 2){
			mk.kit.setPosition(KitPosition.POSITION2);
		}
		mk.slot = myStand.getAvailableSlotNum();
		print("Kit Slot number : "+mk.slot+" is Taken");
		myStand.setSlotTaken(myStand.getAvailableSlotNum());
		if(guiEnable){
			mk.status = KitStatus.Being_Animated;
			this.KitStationGUI.doCreateAKit(mk.kit);
			try {
				//guiControl =  new Semaphore(0,true);//TODO: take away after gui fixes it
				this.guiControl.acquireUninterruptibly();
				print("done with boxbelt");
			} catch (Exception e) {
				//e.printStackTrace();
			}
			this.kitRobotGUI.DoMoveKitToStand(mk.kit.guikit , mk.kit.position);
			try {
				//guiControl =  new Semaphore(0,true);//TODO: take away after gui fixes it
				this.guiControl.acquireUninterruptibly();
				print("done with kit robot");
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}else{
			print("GUI Creating  kit. Finished Cearting Kit. Telling Agent.");
			print("GUI Moving Kit To Stand. Finished moving. Telling Agent.");
		}
		mk.status = KitStatus.On_KitStand;
		kitStand.msgHereIsNextAgentKit(mk.kit);
		stateChanged();
	}
	/**
	 * Tell GUI to place the Kit on the furthest-left position of kit stand.
	 * @param mk
	 */
	private void placeKitOnInspection(MyKit mk)
	{
		myStand.setSlotThreeTaken();
		if(guiEnable){
			this.kitRobotGUI.DoMoveKitToCameraInspection(mk.kit.guikit, mk.kit.position);
			print("Moving Kit to Inspection Slot");
			try {
				//guiControl =  new Semaphore(0,true);//TODO: take away after gui fixes it
				guiControl.acquireUninterruptibly();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		else
			print("GUI moving Kit to Camera Inspection. Finished moving. Telling Agent.");


		myStand.setSlotAvilable(mk.slot);
		mk.kit.position = KitPosition.POSITION3;
		print("Kit Slot number : "+mk.slot+" is Available but slot 3 is taken");
		mk.status = KitStatus.On_CameraInspection;
		kitCamera.msgInspectKit(mk.kit);
		stateChanged();
	}

	/**
	 * Tell GUI to place Kit on boat, which exits the factory.
	 * @param mk
	 */
	private void placeKitOnDeliveryBoat(MyKit mk)
	{
		if(guiEnable){
			this.boatGui.msgDoGetKit();
			try {
				print("Boat moving!!");
				guiControl.acquireUninterruptibly();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			print("Boat has finished moving.");
			this.kitRobotGUI.DoMoveFinishedKitToDeliveryStation (mk.kit.guikit);
			try {
				//guiControl =  new Semaphore(0,true);//TODO: take away after gui fixes it
				guiControl.acquireUninterruptibly();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		else
			print("GUI moving Kit to Boat. Finished moving. Telling Agent.");
		
		myStand.setSlotThreeAvailable();
		mk.slot = 0;
		print("Kit Slot number : "+3 +" is available");
		mk.status = KitStatus.Gone; 
		if(guiEnable)
			boatGui.msgDoReceiveKit(mk.kit.guikit);//telling boat
		stateChanged();
	}
	
	/////////////////Extra//////////////////
	
	public void setGuiKitBoat(GuiKitBoat boat){
		this.boatGui = boat;
	}
	public void setKitStand(KitStand ks){
		this.kitStand = ks;
	}
	
	public void setKitCamera( KitCamera km){
		this.kitCamera = km;
	}
	
	/*public void setGuiCamera(GuiCamera gui){
		this.kitCameraGUI = gui;
	}*/
	
	public void setGuiKitRobot(GuiKitRobot gui){
		this.kitRobotGUI = gui;
	}
	
	public void setGuiBoxBelt(GuiBoxBelt gui){
		this.KitStationGUI = gui;
	}
	
	public void setEnableGUI(boolean b){
		this.guiEnable = b;
	}
	public boolean isAgentAvailableForKit(){
		return isAvailable;
	}
	
	public String toString() {
		return this.name;
	}

	@Override
	public void msgDoneCreatingKit(AgentKit kit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDonePlacingKit(GuiKit kit) {
		// TODO Auto-generated method stub
		
	}
}
