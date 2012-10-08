package gui;

import java.awt.Graphics;

import gui.interfaces.Gui;


/**
 * 
 * @author Jonathan Chu
 *
 */
public class GuiWave implements Gui 
{
	private int location;
	
	public GuiWave()
	{
		location = -1000;
	}
	
	public void draw(Graphics g) 
	{
			g.drawImage(gui.locationsimg.Images.Wave.getImage(), location, 0, null);
	}

	public void updateLocation() 
	{
		if(location >= 0)
		{
			//DO NOTHING
		}
		else
		{
			//System.out.println(location);
			location+=5;
		}
	}
	
	public boolean getDone()
	{
		if(location >= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void partCallback(Gui part) 
	{

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
