package gui.interfaces;

import java.awt.Graphics;

import structures.KitPosition;

import engine.Agent;
import engine.agents.KitRobotAgent;
import gui.holdable.GuiKit;

//@author Sumukh Anand

public interface GuiKitRobotInterface{
	
	public void draw(Graphics g);
		//Draw parts that robot is holding
		
	public void updateLocation();
		//Update movement location
	
	public void setAgent(KitRobotAgent agent);
		//Set the agent
		
	public void DoGoToIdle();
		//Set destination X and Y to idle X and Y
		
	public void DoMoveKitToStand(GuiKit kit, KitPosition pos);
		//Pick up empty kit and set destination X and Y to kit X and Y
	
	public void DoMoveKitToCameraInspection(GuiKit kit, KitPosition pos);
		//Pick up full kit and set destination X and Y to camera X and Y
	
	public void DoMoveFinishedKitToDeliveryStation(GuiKit kit);
		//Pick up checked kit and set destination X and Y to boat X and Y
	
	public void DoMoveKitBackToStand(GuiKit kit, KitPosition pos);
		//Pick up checked kit with bad parts and move it back to KitStand
}
