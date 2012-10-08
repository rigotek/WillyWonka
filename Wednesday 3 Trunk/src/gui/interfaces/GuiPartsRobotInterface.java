package gui.interfaces;

import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;

import engine.Agent;
import gui.holdable.GuiCandy;
import gui.holdable.Holdable;

//@author Sumukh Anand

public interface GuiPartsRobotInterface{
	
	public void draw(Graphics g);
		//Draw parts that robot is holding
		
	public void updateLocation();
		//Update movement location
	
	public void setAgent(Agent agent);
		//Set the agent
		
	public void DoGoToIdle();
		//Set destination X and Y to idle X and Y
		
	public void DoRetrieveParts(Gui nest, ArrayList<GuiCandy> parts);
		//Set destination X and Y to the GuiNest X and Y

	
	public void DoSendParts(Gui kitStand, Gui kit, ArrayList<GuiCandy> parts);
		//Set destination X and Y to GuiKit X and Y
}
