package gui;

import java.awt.Graphics;
import java.util.*;
import java.awt.*;

import structures.AgentPart;
import engine.agents.LaneAgent;
import gui.holdable.GuiCandy;
import gui.holdable.Holdable;
import gui.interfaces.Gui;
import gui.interfaces.GuiLaneInterface;
import gui.locationsimg.Images;


public class GuiLane implements Gui, GuiLaneInterface {

	  private boolean flag;
	
	  private Power Power;
	  
	  private LaneAgent AgentLane; 
	  
	  private GuiOcean Ocean;
	  
	  private int VibrationSpeed;
	  
	  private LinkedList<GuiCandy> CandyQueue;
	  
	  private LinkedList<GuiCandy> LostCandy;
	  
	  private LinkedList<GuiBubble> Bubbles;
	  
	  private int LaneSize;
	  
	  private static int BORDERSIZE = 5;
	  
	  private int Belt; // Used in animation
	  
	  private int currX;
	  
	  private int currY;
	  
	  private int[] Pos;  // Final positions still need to be decided
	  
	  public GuiLane(int x, int y) {
	   
		Power = Power.ON;
		currX = x;
		currY = y;
	    VibrationSpeed = gui.ux.UserData.getVibrationSpeed();
	    Ocean = new GuiOcean(currX + 70, currY + 40);
	    Belt = 10;
	    LaneSize = Images.Lane.getIconHeight();
	    Pos = new int[LaneSize];
	    for (int i = 0; i < LaneSize; i++) {
	    	Pos[i] = currX + (i*10);
	    }
	    
	    CandyQueue = new LinkedList<GuiCandy>();
	    LostCandy = new LinkedList<GuiCandy>();
	    Bubbles = new LinkedList<GuiBubble>();
	  }
	  
	  public void setAgent(LaneAgent a) {
		  AgentLane = a;
	  }
	  
	  public void draw(Graphics g) {
	    //Displays the lane and all of the candies on it
	    
		Ocean.draw(g);
		  g.drawImage(gui.locationsimg.Images.Lane.getImage(), currX, currY, null);
	    g.setColor(Color.GRAY);
	    
	    if (Power == Power.ON) {
	    	if (Belt == 1) {
	    		Belt = 10;
	    	} else {
	    		Belt--;
	    	}
	    }
	    
	    for (int i = 0; i < 20; i++){
	    	g.drawLine(currX+Belt + (10*i), currY + BORDERSIZE, currX+Belt + (10*i), currY+LaneSize - BORDERSIZE - 1);
	    }
	    
	    
	    // Draw all candy on belt
	    for (int i = 0; i < CandyQueue.size(); i++) {
	      CandyQueue.get(i).draw(g);
	    }
	    
	    for(int i = 0; i < LostCandy.size(); i++) {
	    	LostCandy.get(i).draw(g);
	    }
	    
	  }
	  
	  public void updateLocation(){
		  
		  if (Power == Power.ON) {
		  
		  VibrationSpeed = gui.ux.UserData.getVibrationSpeed();
		  
		  if (!CandyQueue.isEmpty()) {
			  if (CandyQueue.getFirst().getCurrentX() == Pos[0]) {
				  CandyQueue.getFirst().callBackGui();
			  } 
			  
			  AdvanceCandies(); 
			  
			  for (int i = 0; i < CandyQueue.size(); i++) {
				  CandyQueue.get(i).updateLocation();
			  }
			  
			  vibrationCheckIfFalls();
		  }
		  
		  for (int i = 0; i < LostCandy.size(); i++) {
			  LostCandy.get(i).updateLocation();
		  }
		  
		  int b = Bubbles.size();
			
			for (int i = 0; i < b; i++) {
				if (Bubbles.get(i).getOffScreen()) {
					Bubbles.remove(i);
					b--;
					i--;
				}
			}
		  
		  for (int i = 0; i < Bubbles.size(); i++) {
			  Bubbles.get(i).updateLocation();
		  }
		  
		  Ocean.updateLocation();
		  
		  }
	  }
	  
	  public void vibrationCheckIfFalls() {
		  // If the vibration speed is higher than 5 there is a chance that the part will fall off the belt
		  if (VibrationSpeed >= 5) {
			  Random rand = new Random();
			  int check = 0;
			  for (int i = 0; i < CandyQueue.size(); i++) {
				  check = rand.nextInt(1000);	
				  if (check < VibrationSpeed) {
					  
					  // Candy Falls off the lane and goes to the ocean
					  CandyQueue.get(i).setDestination(CandyQueue.get(i).getCurrentX(), CandyQueue.get(i).getCurrentY()+30);
					  LostCandy.add(CandyQueue.get(i));
					  AgentLane.RemovePartFromLane(CandyQueue.get(i));
					  CandyQueue.remove(i);
				  }
			  }
		  }
	  }
	  
	  public int getCurrentX(){
	    return currX;
	  }
	  
	  public int getCurrentY(){
	    return currY;
	  }
	  
	  public int getDestinationX(){
	    return currX;
	  }
	  
	  public int getDestinationY(){
	    return currY;
	  }
	  
	  public void DoMoveToLane(Holdable p) {
	    // Agent call from backend
	    // Takes a candy from the deflector and places it in the last position of the CandyQue
	    p.setLinkedGui(this);
	    CandyQueue.add((GuiCandy)p);
	    AdvanceCandies();	    
	  }
	  
	  public void AdvanceCandies() {
		
		  // For every candy in the lane
	    for (int i = 0; i < CandyQueue.size(); i++) { 
	    	
	    	// If the candy is above the midline
	    	 if (CandyQueue.get(i).getCurrentY() > currY+(Images.CandyA.getIconHeight()/2)) {
	    		 
	    		 // move the candy down
				  CandyQueue.get(i).setLocation(CandyQueue.get(i).getCurrentX(), currY+(Images.CandyA.getIconHeight()/2)-VibrationSpeed);
				  
			// If the candy is below the midline
			  } else if (CandyQueue.get(i).getCurrentY() < currY+(Images.CandyA.getIconHeight()/2)) {
				  
				  // move the candy up
				  CandyQueue.get(i).setLocation(CandyQueue.get(i).getCurrentX(), currY+(Images.CandyA.getIconHeight()/2)+VibrationSpeed);
				  
			// If the candy is on the midline, send the candy up or down randomly  
			  } else {
				  Random r = new Random();
				  Boolean rand = r.nextBoolean();
				  if (rand) {
					  CandyQueue.get(i).setLocation(CandyQueue.get(i).getCurrentX(), currY+(Images.CandyA.getIconHeight()/2)+VibrationSpeed);
				  } else {
					  CandyQueue.get(i).setLocation(CandyQueue.get(i).getCurrentX(), currY+(Images.CandyA.getIconHeight()/2)-VibrationSpeed);
				  }
			  } 
	    	 CandyQueue.get(i).setDestination(Pos[i], currY+(Images.CandyA.getIconHeight()/2));
	    }
	  }
	  
	  public void partCallback(Gui c) {
	    // Signifies that the candy has moved to its specified location
	    
	    // The candy in the front of the candyqueue will call this method once it gets to the end of the lane
	    // so the DonePartMoving method should be called for that candy only 
		
	    if (!CandyQueue.isEmpty() && c == CandyQueue.getFirst()) {
	    	AgentLane.DonePartMoving(CandyQueue.getFirst());
	    }
	    	
	    if (LostCandy.contains(c)) {
	    	System.out.println("SENDING CANDY TO OCEAN");
	    	LostCandy.remove(c);
			Ocean.DoMoveToRipple((GuiCandy)c);   	
	    }
	  }
	   
	  public void DoRemoveFromLane(Holdable p) {
	    // Agent call from backend
	    // Moves the candy at the front of the queue into the Nest
	    
	    CandyQueue.removeFirst();  // part p should be refering to the first item on the list if everything is working right
	    AdvanceCandies();
	  }
	  
	  public void bubbleCandies() {
		 while(!CandyQueue.isEmpty()) {
			 AgentLane.RemovePartFromLane(CandyQueue.getFirst());
			 Bubbles.add(new GuiBubble(CandyQueue.removeFirst()));
		 }
	  }
	  
	public LinkedList<GuiBubble> getBubbles() {
		return Bubbles;
	}
	

	@Override
	public void setDestination(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enable() {
		Power = Power.ON;
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		Power = Power.OFF;
	}

	@Override
	public Power checkPower() {
		// TODO Auto-generated method stub
		return Power;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		Power = Power.OFF;
	}

	@Override
	public void repair() {
		// TODO Auto-generated method stub
		Power = Power.ON;
	}

	@Override
	public boolean checkRepair() {
		// TODO Auto-generated method stub
		return false;
	}

}
