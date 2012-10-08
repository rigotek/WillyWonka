//Alberto Marroquin

package engine.agents;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import structures.AgentKit;
import structures.AgentPart;
import structures.PartType;

import engine.Agent;
import engine.interfaces.KitCamera;
import engine.interfaces.KitRobot;
import gui.GuiCamera;
import gui.GuiCheckingStation;

public class KitCameraAgent extends Agent implements KitCamera{
	boolean guiEnable = true;//Control the GUI when JUnit testing.
	//GuiCheckingStation cameraInspectionGUI;
	GuiCamera cameraInspectionGUI;
	GuiCheckingStation checkstation;
	KitRobot kitRobot;
	List<MyKit> kits;
	Timer timer = new Timer();
	Semaphore guiControl = new Semaphore(0,true);
	enum KitStatus{ Unknown, Ready_To_Inspect, Inspection_Passed, Inspection_Failed, Being_Inspected, Gone}

	class MyKit
	{
		AgentKit kit;
		KitStatus status;
		public MyKit(AgentKit kit){
			this.kit  = kit;
			status = KitStatus.Unknown;
		}
	}
	String name;
	public KitCameraAgent(String name){
		this.name = name;
		kits = Collections.synchronizedList( new LinkedList<MyKit>());	
	}
	/////////////////////////Messages/////////////////////////////////

	/**
	 * The Kit Robot Agent notifies that the Kit must be inspected.
	 */
	public void msgInspectKit(AgentKit kit) {
		MyKit mk = new MyKit(kit);
		mk.status = KitStatus.Ready_To_Inspect;
		kits.add(mk);
		stateChanged();
	}
	
	public void setGuiCheckStation(GuiCheckingStation check) {
		checkstation = check;
	}
	
	/**
	 * Notifies the inspection is over for a specific Kit. If true, Kit passed. Otherwise, it failed.
	 * @param kit
	 * @param passed
	 */
	public void msgInpectionDone(AgentKit kit, boolean passed)
	{
		for(MyKit k: kits){
			if(k.kit.equals(kit)){
				if(passed){
					k.status = KitStatus.Inspection_Passed;
				}
				else
					k.status = KitStatus.Inspection_Failed;
				stateChanged();
				return;
			}
		}
	}

	/**
	 * The GUI notifies the Kit Robot that a picture has been taken of the Kit being inspected. 
	 * The kit robot will then proceed as necessary. 
	 */
	public void msgDoneTakingPicture() {
		guiControl.release();		
	}
	
	//////////////////////////Scheduler/////////////////////////////////////
	
	public boolean pickAndExecuteAnAction() {
		synchronized(kits){
			for ( MyKit mk : kits){
				if(mk.status.equals(KitStatus.Ready_To_Inspect)){
					checkIfKitIsGood(mk);
					return true;
				}
			}
		}
		
		synchronized(kits){
			for ( MyKit mk : kits){
				if(mk.status.equals(KitStatus.Inspection_Passed)){
					notifyKitIsGood(mk);
					return true;
				}
			}
		}
		
		synchronized(kits){
			for ( MyKit mk : kits){
				if(mk.status.equals(KitStatus.Inspection_Failed)){
					notifyKitIsBad(mk);
					return true;
				}
			}
		}
		return false;
	}

	////////////////////////////////Action/////////////////////////////////
	
	/**
	 * Tell GUI to inspect the Kit. The inspection will take time. 
	 */
	private void checkIfKitIsGood(final MyKit mk)
	{
		if(guiEnable){
			//this.checkstation.DoRecieveKit(mk.kit.guikit, 3);
			this.cameraInspectionGUI.doTakePicture();
			print("Taking Picture of Kit");
			try {
				guiControl.acquireUninterruptibly();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
		else
			print("Taking Picture of Kit");
		mk.status = KitStatus.Being_Inspected;
		timer.schedule(new TimerTask(){
	    public void run(){		    
	    inspection(mk);
	    }
	}, 5000);//5000
	stateChanged();
	}

	/**
	 * Perform Inspection on Kit
	 * @param mk
	 */
	private void inspection(MyKit mk)
	{
		print("Kit being inspected");
		//Determine if parts and amount of parts satisfy the configuration
		List<AgentPart> parts = mk.kit.parts;
		HashMap<PartType, Integer> c = mk.kit.config.configuration();
		HashMap<PartType, Integer> q = (HashMap<PartType, Integer>) mk.kit.kitParts;
		boolean passed =true;
		int num = 1;
		PartType type ;
		//Check if kit has amount of parts necessary.
		while(num <=17){
			type = getPartType(num);
			if(c.containsKey(type) ){
				if(!(q.containsKey(type) && q.get(type) == c.get(type))){
					print("kit has " + q.get(type)+ " of type: "+type);
					print("kit needs " + c.get(type)+ " of type: "+type+"!");
					print("Kit Missing Parts");
					passed  = false;
					break;
				}
			}
			num++;
		}
		//After checking kit has all parts, check if all parts are good. If one part is bad, the
		//entire kit is bad.
		if(passed == true){
			if(parts.size() > 0){//Parts must exist!!Otherwise, It is a defective kit!
				for(AgentPart p : parts){
					if(p.goodpart == false){
						print("Kit has bad parts");
						passed = false;
						break;
					}
				}
			}
			else{
				print("Kit has not parts");
				passed = false;
			}
		}
		if(passed){
			msgInpectionDone(mk.kit, true);
		}
		else
			msgInpectionDone(mk.kit, false);
	}
	
	public PartType getPartType(int n){
		switch(n){
		case 1:
			return PartType.TYPE1;
		case 2:
			return PartType.TYPE2;
		case 3:
			return PartType.TYPE3;
		case 4:
			return PartType.TYPE4;
		case 5:
			return PartType.TYPE5;
		case 6:
			return PartType.TYPE6;
		case 7:
			return PartType.TYPE7;
		case 8:
			return PartType.TYPE8;
		case 9:
			return PartType.TYPE9;
		case 10:
			return PartType.TYPE10;
		case 11:
			return PartType.TYPE11;
		case 12:
			return PartType.TYPE12;
		case 13:
			return PartType.TYPE13;
		case 14:
			return PartType.TYPE14;
		case 15:
			return PartType.TYPE15;
		case 16:
			return PartType.TYPE16;
		case 17:
			return PartType.NOTYPE;
		default: 
			print("No Type found");
			}
		print("This Type shouldn't happen!!!!!!!!");
		return PartType.NOTYPE;
	}

	/**
	 * Tell Kit Robot Agent that the Kit passed.
	 * @param mk
	 */
	private void notifyKitIsGood(MyKit mk)
	{
		print("Notifying KitRobot that kit is good.");
		mk.status = KitStatus.Gone;
		kitRobot.msgKitIsGood(mk.kit) ;
		stateChanged();
	}

	/**
	 * Tell Kit Robot Agent that the Kit Failed. Note: It will be used in the future.
	 * @param mk
	 */
	private void notifyKitIsBad(MyKit mk)
	{
		print("Notifying KitRobot that kit is bad.");
		mk.status = KitStatus.Gone;
		kitRobot.msgKitIsBad(mk.kit) ;
		stateChanged();
	}
	
	///////////////Extra////////////////
	
	public void setKitRobot(KitRobot kr){
		this.kitRobot = kr;
	}
	
	public void setGuiCheckingStation(GuiCamera gui){
		this.cameraInspectionGUI = gui;
	}

	public void setEnableGUI(boolean b){
		this.guiEnable = b;
	}
}