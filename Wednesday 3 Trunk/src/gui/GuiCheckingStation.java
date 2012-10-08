package gui;

//import engine.Agent;
import java.awt.Graphics;
//import java.awt.List;
import java.util.ArrayList;

import engine.agents.KitStandAgent;
import engine.agents.KitStandAgent.STATE;
//import engine.agents.LaneCameraAgent;
import gui.holdable.GuiKit;
import gui.holdable.GuiCandy;
import gui.interfaces.Gui;
import gui.interfaces.Gui.Power;
import gui.locationsimg.Locations;
import gui.locationsimg.Images;

public class GuiCheckingStation implements Gui{
	

	private KitStandAgent MyKitStand = null;
	private GuiKit kit2 = null;
	private GuiKit kit1 = null;
	private GuiKit kit3 = null;

	int currX;
	int currY;
	public static boolean drawKit;
	ArrayList<GuiCandy> parts = new ArrayList<GuiCandy>();
	public enum KitStateA{
		EMPTY,
		HAS_KIT,
		RECEIVING
	};
	public enum KitStateB{
		EMPTY,
		HAS_KIT,
		RECEIVING
	};
	
	public enum PicState {
		EMPTY,
		PIC_READY,
		PIC_TAKEN

	};
	
	KitStateA currKitStateA;
	KitStateB currKitStateB;
	PicState currPicState;

	public void DoRecieveParts(ArrayList<GuiCandy> Parts){
		//parts.addAll(Parts);
		if(MyKitStand.kita.state == STATE.NO_ACTION){
			currKitStateA = KitStateA.EMPTY;
		}
		else if(MyKitStand.kita.state == STATE.RECEIVING_PARTS){
			if(kit1.kitIsFull()){
				currKitStateA = KitStateA.EMPTY;
			}
			else{
				currKitStateA = KitStateA.RECEIVING;
			}
		}
		if(MyKitStand.kitb.state == STATE.NO_ACTION){
			currKitStateB = KitStateB.EMPTY;
		}
		else if(MyKitStand.kitb.state == STATE.RECEIVING_PARTS){
			if(kit2.kitIsFull()){
				currKitStateB = KitStateB.EMPTY;
			}
			else{
				currKitStateB = KitStateB.RECEIVING;
			}
		}
	}
	public void NullKit(GuiKit Kit){
		if(Kit==kit1)
			kit1=null;
		if(Kit==kit2)
			kit2=null;
	}
	public void DoRecieveKit(GuiKit Kit, int x){
		
		//System.out.println("recieved a kit");
		
		if(x==1){
			currKitStateA=KitStateA.HAS_KIT;
			//System.out.println("recieved kit1");
			kit1=null;
			kit1 =Kit;
			kit1.setDestination(Locations.kitAX, Locations.kitAY);
			MyKitStand.DoneReceivingKit();
		}
		else if(x==2){
			currKitStateB=KitStateB.HAS_KIT;
			//System.out.println("recieved kit2");
			kit2=null;
			kit2 =Kit;
			kit2.setDestination(Locations.kitBX, Locations.kitBY);
			MyKitStand.DoneReceivingKit();	
		}
			else if(x==3){
			
				kit3=null;
				kit3 =Kit;
				kit3.setDestination(Locations.kitBX, Locations.kitBY);
				MyKitStand.DoneReceivingKit();
			
		}
		
	}

	public GuiCheckingStation(int x, int y){
		currX=x;
		currY=y;
		currKitStateA=KitStateA.EMPTY;
		currKitStateB=KitStateB.EMPTY;
		currPicState=PicState.PIC_READY;
	}


 public void SetKitStand(KitStandAgent Kit){
	 MyKitStand=Kit;
	 
 }

	public void doTakePicture(){
		
		//agent.msgDoneTakingPicture();
		currPicState=PicState.PIC_TAKEN;

	}


	void update(){
		if(kit1.getCurrentY()==currY && kit1.getCurrentX()==currX){
			currPicState=PicState.PIC_READY;
			doTakePicture();
			currPicState=PicState.PIC_TAKEN;

		}
	}

	@Override
	public void updateLocation() {
	

	}


	@Override
	public void partCallback(Gui part) {
		// TODO Auto-generated method stub

	}


	@Override
	public int getCurrentX() {
		// TODO Auto-generated method stub
		return currX;
	}


	@Override
	public int getCurrentY() {
		// TODO Auto-generated method stub
		return currY;
	}


	@Override
	public int getDestinationX() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getDestinationY() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setDestination(int x, int y) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void draw(Graphics g) {
		//kit1.updateLocation();
		//kit2.updateLocation();
		g.drawImage(Images.KitCradle.getImage(), currX, currY, null);
		//if(kit1!=null && GuiCheckingStation.drawKit == true)
		if(kit1!=null && !kit1.isRotated()){
			kit1.draw(g);
		}
		//if(kit2!=null && GuiCheckingStation.drawKit == true)
		if(kit2!=null && !kit2.isRotated()){
			//g.drawImage(Images.Kit.getImage(), kit2.getCurrentX(), kit2.getCurrentY(), null);
			kit2.draw(g);
			if(kit3!=null)
				kit3.draw(g);
		}
		
	}
	
	/** Called by the FactoryPanel to enable GUI agents. */
	public void enable(){}
	
	/** Called by the FactoryPanel to disable GUI agents. */
	public void disable(){}
	
	/** Called by the FactoryPanell to check current power status of a GUI agent. */
	public Power checkPower(){
		return Power.ON;}
	
	/**
	 * Called by the FactoryPanel to destroy GUI agents. Should display a unique animation
	 * particular to the GUI agent in question and set an internal flag to signify that the
	 * GUI agent has been damaged.
	 */
	public void destroy(){}
	
	/**
	 * Called by the FactoryPanel to repair GUI agents. GUI agents that have previously been
	 * destroyed cannot fully be repaired, and should have a higher chance of producing errors.
	 */
	public void repair(){}
	
	/** Called by the FactoryPanel to check whether the GUI agent has suffered prior damage. */
	public boolean checkRepair(){return false;}

}

