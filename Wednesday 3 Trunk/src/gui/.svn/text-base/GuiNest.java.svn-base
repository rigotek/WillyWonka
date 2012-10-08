package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.*;

import engine.agents.NestAgent;
import gui.holdable.*;
import gui.interfaces.Gui;
import gui.interfaces.GuiNestInterface;
import gui.interfaces.Gui.Power;

public class GuiNest implements Gui, GuiNestInterface {

	private Power Power;
	
	private NestAgent AgentNest; 
	
	private GuiOcean Ocean;

	private LinkedList<Holdable> CandyQueue;
	
	private LinkedList<Holdable> LostCandy;
	
	private LinkedList<GuiBubble> Bubbles;

	private int NestSize;

	private int currX;

	private int currY;

	private int[] PosX;
	private int[] PosY;

	public GuiNest(int x, int y) {
		currX = x;
		currY = y;

		Power = Power.ON;
		//CHANGED NEST SIZE TO 8 - SUMUKH
		NestSize = 8;
		PosX = new int[NestSize];
		PosY = new int[NestSize];
		if (currY == gui.locationsimg.Locations.nestAY) {
			PosX[0] = gui.locationsimg.Locations.candyNestAX1;
			PosX[1] = gui.locationsimg.Locations.candyNestAX2;
			PosX[2] = gui.locationsimg.Locations.candyNestAX3;
			PosX[3] = gui.locationsimg.Locations.candyNestAX4;
			PosX[4] = gui.locationsimg.Locations.candyNestAX5;
			PosX[5] = gui.locationsimg.Locations.candyNestAX6;
			PosX[6] = gui.locationsimg.Locations.candyNestAX7;
			PosX[7] = gui.locationsimg.Locations.candyNestAX8;
			//PosX[8] = gui.locationsimg.Locations.candyNestAX9;
			//PosX[9] = gui.locationsimg.Locations.candyNestAX10;

			PosY[0] = gui.locationsimg.Locations.candyNestAY1;
			PosY[1] = gui.locationsimg.Locations.candyNestAY2;
			PosY[2] = gui.locationsimg.Locations.candyNestAY3;
			PosY[3] = gui.locationsimg.Locations.candyNestAY4;
			PosY[4] = gui.locationsimg.Locations.candyNestAY5;
			PosY[5] = gui.locationsimg.Locations.candyNestAY6;
			PosY[6] = gui.locationsimg.Locations.candyNestAY7;
			PosY[7] = gui.locationsimg.Locations.candyNestAY8;
			//PosY[8] = gui.locationsimg.Locations.candyNestAY9;
			//PosY[9] = gui.locationsimg.Locations.candyNestAY10;
		}	//ADDED NEW LOCATIONS FOR DIFFERENT NESTS - SUMUKH 
		else if (currY == gui.locationsimg.Locations.nestBY) {
			PosX[0] = gui.locationsimg.Locations.candyNestBX1;
			PosX[1] = gui.locationsimg.Locations.candyNestBX2;
			PosX[2] = gui.locationsimg.Locations.candyNestBX3;
			PosX[3] = gui.locationsimg.Locations.candyNestBX4;
			PosX[4] = gui.locationsimg.Locations.candyNestBX5;
			PosX[5] = gui.locationsimg.Locations.candyNestBX6;
			PosX[6] = gui.locationsimg.Locations.candyNestBX7;
			PosX[7] = gui.locationsimg.Locations.candyNestBX8;
			//PosX[8] = gui.locationsimg.Locations.candyNestAX9;
			//PosX[9] = gui.locationsimg.Locations.candyNestAX10;

			PosY[0] = gui.locationsimg.Locations.candyNestBY1;
			PosY[1] = gui.locationsimg.Locations.candyNestBY2;
			PosY[2] = gui.locationsimg.Locations.candyNestBY3;
			PosY[3] = gui.locationsimg.Locations.candyNestBY4;
			PosY[4] = gui.locationsimg.Locations.candyNestBY5;
			PosY[5] = gui.locationsimg.Locations.candyNestBY6;
			PosY[6] = gui.locationsimg.Locations.candyNestBY7;
			PosY[7] = gui.locationsimg.Locations.candyNestBY8;
			//PosY[8] = gui.locationsimg.Locations.candyNestAY9;
			//PosY[9] = gui.locationsimg.Locations.candyNestAY10;
			
		} else if (currY == gui.locationsimg.Locations.nestCY) {
			PosX[0] = gui.locationsimg.Locations.candyNestCX1;
			PosX[1] = gui.locationsimg.Locations.candyNestCX2;
			PosX[2] = gui.locationsimg.Locations.candyNestCX3;
			PosX[3] = gui.locationsimg.Locations.candyNestCX4;
			PosX[4] = gui.locationsimg.Locations.candyNestCX5;
			PosX[5] = gui.locationsimg.Locations.candyNestCX6;
			PosX[6] = gui.locationsimg.Locations.candyNestCX7;
			PosX[7] = gui.locationsimg.Locations.candyNestCX8;
			//PosX[8] = gui.locationsimg.Locations.candyNestAX9;
			//PosX[9] = gui.locationsimg.Locations.candyNestAX10;

			PosY[0] = gui.locationsimg.Locations.candyNestCY1;
			PosY[1] = gui.locationsimg.Locations.candyNestCY2;
			PosY[2] = gui.locationsimg.Locations.candyNestCY3;
			PosY[3] = gui.locationsimg.Locations.candyNestCY4;
			PosY[4] = gui.locationsimg.Locations.candyNestCY5;
			PosY[5] = gui.locationsimg.Locations.candyNestCY6;
			PosY[6] = gui.locationsimg.Locations.candyNestCY7;
			PosY[7] = gui.locationsimg.Locations.candyNestCY8;
			//PosY[8] = gui.locationsimg.Locations.candyNestAY9;
			//PosY[9] = gui.locationsimg.Locations.candyNestAY10;
			
		} else if (currY == gui.locationsimg.Locations.nestDY) {
			PosX[0] = gui.locationsimg.Locations.candyNestDX1;
			PosX[1] = gui.locationsimg.Locations.candyNestDX2;
			PosX[2] = gui.locationsimg.Locations.candyNestDX3;
			PosX[3] = gui.locationsimg.Locations.candyNestDX4;
			PosX[4] = gui.locationsimg.Locations.candyNestDX5;
			PosX[5] = gui.locationsimg.Locations.candyNestDX6;
			PosX[6] = gui.locationsimg.Locations.candyNestDX7;
			PosX[7] = gui.locationsimg.Locations.candyNestDX8;
			//PosX[8] = gui.locationsimg.Locations.candyNestBX9;
			//PosX[9] = gui.locationsimg.Locations.candyNestBX10;

			PosY[0] = gui.locationsimg.Locations.candyNestDY1;
			PosY[1] = gui.locationsimg.Locations.candyNestDY2;
			PosY[2] = gui.locationsimg.Locations.candyNestDY3;
			PosY[3] = gui.locationsimg.Locations.candyNestDY4;
			PosY[4] = gui.locationsimg.Locations.candyNestDY5;
			PosY[5] = gui.locationsimg.Locations.candyNestDY6;
			PosY[6] = gui.locationsimg.Locations.candyNestDY7;
			PosY[7] = gui.locationsimg.Locations.candyNestDY8;
			//PosY[8] = gui.locationsimg.Locations.candyNestBY9;
			//PosY[9] = gui.locationsimg.Locations.candyNestBY10;
		}
		else if (currY == gui.locationsimg.Locations.nestEY) {
			PosX[0] = gui.locationsimg.Locations.candyNestEX1;
			PosX[1] = gui.locationsimg.Locations.candyNestEX2;
			PosX[2] = gui.locationsimg.Locations.candyNestEX3;
			PosX[3] = gui.locationsimg.Locations.candyNestEX4;
			PosX[4] = gui.locationsimg.Locations.candyNestEX5;
			PosX[5] = gui.locationsimg.Locations.candyNestEX6;
			PosX[6] = gui.locationsimg.Locations.candyNestEX7;
			PosX[7] = gui.locationsimg.Locations.candyNestEX8;
			//PosX[8] = gui.locationsimg.Locations.candyNestAX9;
			//PosX[9] = gui.locationsimg.Locations.candyNestAX10;

			PosY[0] = gui.locationsimg.Locations.candyNestEY1;
			PosY[1] = gui.locationsimg.Locations.candyNestEY2;
			PosY[2] = gui.locationsimg.Locations.candyNestEY3;
			PosY[3] = gui.locationsimg.Locations.candyNestEY4;
			PosY[4] = gui.locationsimg.Locations.candyNestEY5;
			PosY[5] = gui.locationsimg.Locations.candyNestEY6;
			PosY[6] = gui.locationsimg.Locations.candyNestEY7;
			PosY[7] = gui.locationsimg.Locations.candyNestEY8;
			//PosY[8] = gui.locationsimg.Locations.candyNestAY9;
			//PosY[9] = gui.locationsimg.Locations.candyNestAY10;
		}	//ADDED NEW LOCATIONS FOR DIFFERENT NESTS - SUMUKH 
		
		else if (currY == gui.locationsimg.Locations.nestFY) {
			PosX[0] = gui.locationsimg.Locations.candyNestFX1;
			PosX[1] = gui.locationsimg.Locations.candyNestFX2;
			PosX[2] = gui.locationsimg.Locations.candyNestFX3;
			PosX[3] = gui.locationsimg.Locations.candyNestFX4;
			PosX[4] = gui.locationsimg.Locations.candyNestFX5;
			PosX[5] = gui.locationsimg.Locations.candyNestFX6;
			PosX[6] = gui.locationsimg.Locations.candyNestFX7;
			PosX[7] = gui.locationsimg.Locations.candyNestFX8;
			//PosX[8] = gui.locationsimg.Locations.candyNestAX9;
			//PosX[9] = gui.locationsimg.Locations.candyNestAX10;

			PosY[0] = gui.locationsimg.Locations.candyNestFY1;
			PosY[1] = gui.locationsimg.Locations.candyNestFY2;
			PosY[2] = gui.locationsimg.Locations.candyNestFY3;
			PosY[3] = gui.locationsimg.Locations.candyNestFY4;
			PosY[4] = gui.locationsimg.Locations.candyNestFY5;
			PosY[5] = gui.locationsimg.Locations.candyNestFY6;
			PosY[6] = gui.locationsimg.Locations.candyNestFY7;
			PosY[7] = gui.locationsimg.Locations.candyNestFY8;
			//PosY[8] = gui.locationsimg.Locations.candyNestAY9;
			//PosY[9] = gui.locationsimg.Locations.candyNestAY10;
			
		} else if (currY == gui.locationsimg.Locations.nestGY) {
			PosX[0] = gui.locationsimg.Locations.candyNestGX1;
			PosX[1] = gui.locationsimg.Locations.candyNestGX2;
			PosX[2] = gui.locationsimg.Locations.candyNestGX3;
			PosX[3] = gui.locationsimg.Locations.candyNestGX4;
			PosX[4] = gui.locationsimg.Locations.candyNestGX5;
			PosX[5] = gui.locationsimg.Locations.candyNestGX6;
			PosX[6] = gui.locationsimg.Locations.candyNestGX7;
			PosX[7] = gui.locationsimg.Locations.candyNestGX8;
			//PosX[8] = gui.locationsimg.Locations.candyNestAX9;
			//PosX[9] = gui.locationsimg.Locations.candyNestAX10;

			PosY[0] = gui.locationsimg.Locations.candyNestGY1;
			PosY[1] = gui.locationsimg.Locations.candyNestGY2;
			PosY[2] = gui.locationsimg.Locations.candyNestGY3;
			PosY[3] = gui.locationsimg.Locations.candyNestGY4;
			PosY[4] = gui.locationsimg.Locations.candyNestGY5;
			PosY[5] = gui.locationsimg.Locations.candyNestGY6;
			PosY[6] = gui.locationsimg.Locations.candyNestGY7;
			PosY[7] = gui.locationsimg.Locations.candyNestGY8;
			//PosY[8] = gui.locationsimg.Locations.candyNestAY9;
			//PosY[9] = gui.locationsimg.Locations.candyNestAY10;
			
		} else if (currY == gui.locationsimg.Locations.nestHY) {
			PosX[0] = gui.locationsimg.Locations.candyNestHX1;
			PosX[1] = gui.locationsimg.Locations.candyNestHX2;
			PosX[2] = gui.locationsimg.Locations.candyNestHX3;
			PosX[3] = gui.locationsimg.Locations.candyNestHX4;
			PosX[4] = gui.locationsimg.Locations.candyNestHX5;
			PosX[5] = gui.locationsimg.Locations.candyNestHX6;
			PosX[6] = gui.locationsimg.Locations.candyNestHX7;
			PosX[7] = gui.locationsimg.Locations.candyNestHX8;
			//PosX[8] = gui.locationsimg.Locations.candyNestBX9;
			//PosX[9] = gui.locationsimg.Locations.candyNestBX10;

			PosY[0] = gui.locationsimg.Locations.candyNestHY1;
			PosY[1] = gui.locationsimg.Locations.candyNestHY2;
			PosY[2] = gui.locationsimg.Locations.candyNestHY3;
			PosY[3] = gui.locationsimg.Locations.candyNestHY4;
			PosY[4] = gui.locationsimg.Locations.candyNestHY5;
			PosY[5] = gui.locationsimg.Locations.candyNestHY6;
			PosY[6] = gui.locationsimg.Locations.candyNestHY7;
			PosY[7] = gui.locationsimg.Locations.candyNestHY8;
			//PosY[8] = gui.locationsimg.Locations.candyNestBY9;
			//PosY[9] = gui.locationsimg.Locations.candyNestBY10;
		}

		CandyQueue = new LinkedList<Holdable>();
		LostCandy = new LinkedList<Holdable>();
		Bubbles = new LinkedList<GuiBubble>();
		
		Ocean = new GuiOcean(currX - 50, currY + 25);
	}

	public void setAgent(NestAgent a) {
		AgentNest = a;
	}

	public void draw(Graphics g) {
		
		Ocean.draw(g);
		
		// Displays the Nest and the candies on it
		if (currY == gui.locationsimg.Locations.nestAY || currY == gui.locationsimg.Locations.nestCY || currY == gui.locationsimg.Locations.nestEY
				|| currY == gui.locationsimg.Locations.nestGY) {
			g.drawImage(gui.locationsimg.Images.NestA.getImage(), currX, currY, null);
		} else {
			g.drawImage(gui.locationsimg.Images.NestB.getImage(), currX, currY, null);
		}
		
		// Draw all candy in nest
		for (int i = 0; i < CandyQueue.size(); i++) {
			CandyQueue.get(i).draw(g);
		}
		
		for (int i = 0; i < LostCandy.size(); i++) {
			LostCandy.get(i).draw(g);
		}
		
	}

	public void updateLocation(){
		
		if (Power == Power.ON) {
		
			for (int i = 0; i < CandyQueue.size(); i++) {
				CandyQueue.get(i).updateLocation();
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
	
	public void purgeNest() {
		LostCandy.addAll(CandyQueue);
		CandyQueue.clear();
		for (int i = 0; i < LostCandy.size(); i++) {
			LostCandy.get(i).setDestination(currX - 25, currY + 25);
		}
	}
	
	public void purgePart(Holdable h) {
			LostCandy.add(h);
			CandyQueue.remove(h);
			h.setDestination(currX - 10, currY + 25);
			AdvanceCandies();
	}
	
	public void bubbleCandies() {
		 while(!CandyQueue.isEmpty()) {
			 AgentNest.RemovePartFromNest((GuiCandy)CandyQueue.getFirst());
			 Bubbles.add(new GuiBubble((GuiCandy)CandyQueue.removeFirst()));
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

	public void DoPutInNest(Holdable p) {
		// Agent call from backend
		// takes a part from the last space in the lane and move it to the nest
		p.setLinkedGui(this);
		CandyQueue.add(p);
		AdvanceCandies();
	}

	public void AdvanceCandies() {
		for (int i = 0; i < CandyQueue.size(); i++) {
			CandyQueue.get(i).setDestination(PosX[i], PosY[i]);  // Set all candies to go to their locations
		}
	}

	public void partCallback(Gui o) {
		// Does nothing for the nest
		if (LostCandy.contains(o)) {
			LostCandy.remove(o);
			Ocean.DoMoveToRipple((GuiCandy)o);
		}
	}

	public void DoRemoveFromNest(Holdable p) {
		// Agent call from backend
		// takes a part from the nest and removes it

		CandyQueue.removeFirst();  // part p should be refering to the first item on the list if everything is working right
		AdvanceCandies();
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
