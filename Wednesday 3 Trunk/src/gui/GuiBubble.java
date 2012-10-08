package gui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;



import gui.holdable.GuiCandy;
import gui.interfaces.Gui;

/**
 * 
 * @author Jonathan Chu
 *
 */

public class GuiBubble implements Gui 
{
	private GuiCandy myCandy;
	private int initialX;
	private int initialY;
	private int currX;
	private int currY;
	private ArrayList<Integer> myXCoordinates;
	private ArrayList<Integer> myYCoordinates;
	private int[] finalXDest = {-50,1050};
	private int[] finalYDest = {-50,750};
	private boolean offScreen;
	private int coordinatesIndex;
	private static int listSize = 5;
	private static int displacement = 5;
	private static int BORDER = 35;
	private static int PANELHEIGHT = 700;
	private static int PANELWIDTH = 1000;
	
	public GuiBubble(GuiCandy candyPart)
	{
		offScreen = false;
		myCandy = candyPart;
		myCandy.setLinkedGui(this);
		myXCoordinates = new ArrayList<Integer>();
		myYCoordinates = new ArrayList<Integer>();
		initialX = candyPart.getCurrentX();
		initialY = candyPart.getCurrentY();
		currX = initialX;
		currY = initialY;
		for(int i = 0; i < listSize; i++)
		{
			int tempX = BORDER + (int)(Math.random() * (((PANELWIDTH - BORDER) - (BORDER)) + 1));
			int tempY = BORDER + (int)(Math.random() * (((PANELHEIGHT - BORDER) - (BORDER)) + 1));
			myXCoordinates.add(tempX);
			myYCoordinates.add(tempY);
		}
		
		Random rn = new Random();
		boolean xOrY = rn.nextBoolean();
		
		if(xOrY) //Go off on the X Side
		{
			int finalX;
			Random rn2 = new Random();
			boolean LeftOrRight = rn2.nextBoolean();
			if(LeftOrRight) //Goes Off on Left
			{
				finalX = finalXDest[0];
			}
			else //Goes off on Right
			{
				finalX = finalXDest[1];
			}
			int finalY = BORDER + (int)(Math.random() * (((PANELHEIGHT - BORDER) - (BORDER)) + 1));
			myXCoordinates.add(finalX);
			myYCoordinates.add(finalY);
		}
		else //Go off on Y Side
		{
			int finalY;
			Random rn2 = new Random();
			boolean LeftOrRight = rn2.nextBoolean();
			if(LeftOrRight) //Goes Off on Left
			{
				finalY = finalYDest[0];
			}
			else //Goes off on Right
			{
				finalY = finalYDest[1];
			}
			int finalX = BORDER + (int)(Math.random() * (((PANELWIDTH - BORDER) - (BORDER)) + 1));
			myXCoordinates.add(finalX);
			myYCoordinates.add(finalY);
		}		
		//System.out.println(myXCoordinates);
		//System.out.println(myYCoordinates);
		coordinatesIndex = -1;
	}
	public void draw(Graphics g) 
	{
		g.drawImage(myCandy.image.getImage(), myCandy.getCurrentX(), myCandy.getCurrentY(), null);
		g.drawImage(gui.locationsimg.Images.Bubble.getImage(), currX, currY, null);
	}

	public void updateLocation() 
	{
		if(coordinatesIndex == -1)
		{
			coordinatesIndex = 0;
			myCandy.setDestination(myXCoordinates.get(coordinatesIndex), myYCoordinates.get(coordinatesIndex));	
		}
		else //Going through the Coordinates Lists
		{
			if(coordinatesIndex <= listSize)
			{
				myCandy.setLocation(myCandy.getCurrentX(), myCandy.getCurrentY());
				myCandy.setDestination(myXCoordinates.get(coordinatesIndex), myYCoordinates.get(coordinatesIndex));	
			}
			else
			{
				offScreen = true;
			}
		}
			
		
		myCandy.updateLocation();
		currX = myCandy.getCurrentX()-displacement;
		currY = myCandy.getCurrentY()-displacement;
		
		
		
		//System.out.println(myCandy.getCurrentX() + " " + myCandy.getCurrentY());
	}

	public void partCallback(Gui part) 
	{
		//System.out.println("meow");
		coordinatesIndex++;
		if(coordinatesIndex <= listSize)
		{
			myCandy.setDestination(myXCoordinates.get(coordinatesIndex), myXCoordinates.get(coordinatesIndex));
			//System.out.println(myXCoordinates.get(coordinatesIndex) + " " + myXCoordinates.get(coordinatesIndex));
		}
	}

	public boolean getOffScreen()
	{
		return offScreen;
	}
	public void enable() 
	{

	}

	public void disable() 
	{

	}

	public Power checkPower() 
	{
		return Power.ON;
	}

	public void destroy() 
	{

	}

	public void repair() 
	{

	}

	public boolean checkRepair() 
	{
		return false;
	}

	public int getCurrentX() 
	{
		return 0;
	}

	public int getCurrentY() 
	{
		return 0;
	}

	public int getDestinationX() 
	{
		return 0;
	}

	public int getDestinationY() 
	{
		return 0;
	}

	public void setDestination(int x, int y) 
	{

	}

}
