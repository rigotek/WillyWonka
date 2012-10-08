package gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import gui.interfaces.Gui;


/**
 * 
 * @author Jonathan Chu
 *
 */
public class GuiGates implements Gui, MouseListener 
{
	private static int WIDTH = 1000;
	private int displacement;
	enum gateState {CLOSED, OPENING, OPEN, CLOSING};
	gateState myState;
	
	public GuiGates()
	{
		displacement = 0;
		myState = gateState.CLOSED;
	}
	
	public void draw(Graphics g) 
	{
		switch(myState)
		{
			case CLOSED:
			{
				g.drawImage(gui.locationsimg.Images.GatesA.getImage(), 0, 0, null);
				g.drawImage(gui.locationsimg.Images.GatesB.getImage(), 0, 0, null);
				break;
			}
			case OPENING:
			{
				if(displacement > WIDTH)
				{
					myState = gateState.OPEN;
					//g.drawImage(gui.locationsimg.Images.GatesA.getImage(), 0 - (WIDTH + 1), 0, null);
					//g.drawImage(gui.locationsimg.Images.GatesB.getImage(), WIDTH + 1, 0, null);
				}
				else
				{
					g.drawImage(gui.locationsimg.Images.GatesA.getImage(), (0 - displacement), 0, null);
					g.drawImage(gui.locationsimg.Images.GatesB.getImage(), (displacement), 0, null);
				}
				break;
			}
			case OPEN:
			{
				//No Need To Draw
				break;
			}
			case CLOSING:
			{
				if(displacement <= WIDTH)
				{
					g.drawImage(gui.locationsimg.Images.GatesA.getImage(), (0 - displacement), 0, null);
					g.drawImage(gui.locationsimg.Images.GatesB.getImage(), (displacement), 0, null);
				}
				else
				{
					myState = gateState.CLOSED;
				}
				break;
			}
		};
	}

	public void updateLocation() 
	{
		switch(myState)
		{
			case CLOSED:
			{
				displacement = 0;
				break;
			}
			case OPENING:
			{
				displacement += 2;
				break;
			}
			case OPEN:
			{
				displacement = 1000;
				break;
			}
			case CLOSING:
			{
				displacement -= 2;
				break;
			}
		};

	}
	
	public boolean isOpen()
	{
		return (myState == gateState.OPEN);
	}
	
	public boolean isClosed()
	{
		return (myState == gateState.CLOSED);
	}
	
	public void openGates()
	{
		myState = gateState.OPENING;
	}
	
	public void closeGates()
	{
		myState = gateState.CLOSING;
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


	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println("Meow");
		openGates();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
