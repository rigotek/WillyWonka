package gui.interfaces;

import java.awt.Graphics;

import engine.Agent;
import engine.agents.GantryAgent;
import gui.GuiFeeder;
import gui.holdable.GuiBin;
import gui.GuiBoat;

//@author Sumukh Anand

public interface GuiGantryRobotInterface{
	
	public void draw(Graphics g);
		//Draw parts that robot is holding
		
	public void updateLocation();
		//Update movement location
	
	public void setAgent(GantryAgent agent);
		//Set the agent
		
	public void DoGoToIdle();
		//Set destination X and Y to idle X and Y
		
	public void DoRetrieveBin(GuiBin bin, GuiBoat boat);
		//Set destination X and Y to belt X and Y
		
	public void DoLoadBin(GuiFeeder feeder);
		//Pick up empty kit and set destination X and Y to kit X and Y
	
	public void DoReturnEmptyBin(GuiBin bin, GuiFeeder feeder);
}
