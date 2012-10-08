package gui.interfaces;
import java.awt.Graphics;
import engine.Agent;
import gui.holdable.Holdable;


//AUTHOR: Rodrigo J. Santos

public interface GuiBoatInterface{
	int currX=0;
	int currY=0;
	int destX=0;
	int destY=0;
	boolean HasArrived=true;
	
	//this is an extension of the holdable class, which holds all objects, such as the bin and the completed
	//kits that each boat is able to move with
	//Holdable holds; 
	
	//draws the boat in each clock signal
	public void draw(Graphics g);

	//updates the x and y coordinates of the boat
	void updateLocation();

	//does nothing as of now (conventional method applied to all gui)
	public void partsCallback();
		
	//gets current x coordinate
    public int getCurrentX();
	
    //gets current y coordinate
	public int getCurrentY();
	
	//sets current x
	public void setCurrentX(int cx);
		
	//sets current y
	public void setCurrentY(int cy);
		
	//gets the destination x
	public int getDestinationX();
		
	//gets the destination y
	public int getDestinationY();
	
	//checks to see if it needs a certain part by a signal received from the gantry robot,
	//and if so what part it needs
	void FactoryNeedsPart(Object ob);

	//checks to see if there is a bin on top of the boat
	boolean BinDetection();

	//checks to see if boat has arrived at the docking station
	boolean ArrivedAtDestination();

	//gantry robot arrives and picks bin from boat
	void Unload(Object ob);
	
	//dispatch robot places completed kit onto the boat (pick up station - Part C)
	void Load();

	//while the boat is empty, boat remains in place. Once bin spits off enough parts,
	//gantry robot will place the bin back on the boat (part A). Once it does, boolean
	//goes to false and boat moves
	boolean BoatEmpty();

	//Boolean that Makes Boat Visible/Invisible as It Arrives/Leaves the Screen
	boolean IsVisible();
	
	//movement method. Allows for the boat to sail across the screen
	void Move();
	
	//method that tells boat when to stop and stay put
	void Stay();
}