package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.ImageIcon;

import engine.interfaces.KitRobot;
import gui.interfaces.Gui;
import gui.interfaces.GuiBoxBeltInterface;
import gui.locationsimg.Images;
import structures.AgentKit;
import gui.holdable.GuiKit;

/**
 * 
 * @author Jonathan Chu
 *
 */

public class GuiBoxBelt implements GuiBoxBeltInterface, Gui 
{
	private KitRobot myAgent = null;
	private GuiKit newKit = null;
	private AgentKit newKitAgent = null;
	static boolean takenAway = false;
	
	private static int WIDTH = Images.BoxBelt.getIconWidth();
	private static int HEIGHT = Images.BoxBelt.getIconHeight();
	
	private static int COVERWIDTH = Images.BoxCoverGo.getIconWidth();
	private static int COVERHEIGHT = Images.BoxCoverGo.getIconHeight();
	
	private static int BORDERWIDTH = 5;
	
	private static int WIDTHDISPLACEMENT = 20;
	private static int HEIGHTDISPLACEMENT = 20;
	
	private boolean createBox;
	private boolean hasKit;
	private boolean alreadyMessaged;
	private boolean firstTime;
	private boolean alreadyBroken;
	
	private Power myState;
	
	private int vibrationSpeed;
	int currX;
    int currY;
    int destX;
    int destY;
    int beltCount;
    int coverCount;
	
    ImageIcon horizontalKit = Images.EmptyKit;
    
    public GuiBoxBelt(int locX, int locY)
    {
    	currX = locX;
    	currY = locY;
    	destX = 0;
    	destY = 0;
    	coverCount = 12;
    	beltCount = 12;
    	vibrationSpeed = gui.ux.UserData.getVibrationSpeed();
    	myState = Power.ON;
    	createBox = false;
    	hasKit = false;
    	alreadyMessaged = false;
    	firstTime = true;
    }
    
    public void setAgent(KitRobot kitRobotAgent)
    {
    	myAgent = kitRobotAgent;
    }
    
    public void setVibrationSpeed(int newSpeed)
    {
    	vibrationSpeed = newSpeed;
    }
    
	public void draw(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
		g.drawImage(gui.locationsimg.Images.BoxBelt.getImage(), currX, currY, null);
		
		g.setColor(Color.GRAY);
		
		int holder = 0;		
		
		if(myState == Power.ON)
		{
			if((newKit != null) && (newKit.getCurrentY() != currY - HEIGHTDISPLACEMENT))
			{
				if (beltCount == 1) 
				{
					beltCount = 12;
				} 
				else 
				{
					beltCount--;
				}
				for (int i = 0; i < 15; i++)
				{
					holder = i;
					g.drawLine(currX + BORDERWIDTH, currY + beltCount + (12*i), currX + WIDTH - BORDERWIDTH - 1, currY + beltCount + (12*i));
				}
			}
			else
			{
				if(newKit != null)
				{
					for (int j = holder; j < 15; j++)
					{
						g.drawLine(currX + BORDERWIDTH, currY + beltCount + (12*j), currX + WIDTH - BORDERWIDTH - 1, currY + beltCount + (12*j));
					}
				}
				else
				{
					if (beltCount == 1) 
					{
						beltCount = 12;
					} 
					else 
					{
						beltCount--;
					}
					for (int k = 0; k < 15; k++)
					{
						g.drawLine(currX + BORDERWIDTH, currY + beltCount + (12*k), currX + WIDTH - BORDERWIDTH - 1, currY + beltCount + (12*k));
					}
				}
			}
		
			if((newKit != null) && (newKit.getCurrentY() == currY - HEIGHTDISPLACEMENT))
			{
				newKit.draw(g2d);
				//hasKit = true;
			}
			else
			{
				if(createBox)
				{
					newKit.draw(g2d);
					createBox = false;
					takenAway = false;
					hasKit = true;
				}
				else
				{
					if((!takenAway) && (newKit != null))
					{
						newKit.draw(g2d);
					}				
				}
			}
		}
		else//Power = Off
		{
			for (int m = holder; m < 15; m++)
			{
				g.drawLine(currX + BORDERWIDTH, currY + beltCount + (12*m), currX + WIDTH - BORDERWIDTH - 1, currY + beltCount + (12*m));
			}
			
			if((!takenAway) && (newKit != null))
			{
				newKit.draw(g2d);
			}	
		}
		
		if(coverCount > 50)
		{
			g.drawImage(gui.locationsimg.Images.BoxCoverStop.getImage(), currX, currY + HEIGHT - COVERHEIGHT, null);
		}
		else
		{
			g.drawImage(gui.locationsimg.Images.BoxCoverGo.getImage(), currX, currY + HEIGHT - COVERHEIGHT, null);
		}
	}

	public void updateLocation() 
	{
		vibrationSpeed = gui.ux.UserData.getVibrationSpeed();
		if(myState == Power.ON)
		{
			if((takenAway) && (!firstTime))
			{
				newKit = null;
				//newKitAgent = null;
				//alreadyMessaged = false;
			}
			if(createBox)
			{
				//Tell NEw Kit to Draw Self
				//newKit = new GuiKit(currX + WIDTHDISPLACEMENT, currY + HEIGHTDISPLACEMENT);
				firstTime = false;
				alreadyMessaged = false;
				newKit = new GuiKit((currX + ((currX - Images.Kit.getIconWidth())/2)), currY + HEIGHT - COVERHEIGHT);
				newKit.setDestination((currX + ((currX - Images.Kit.getIconWidth())/2)), currY - HEIGHTDISPLACEMENT);
				newKitAgent.setGuiKit(newKit);
				newKit.setLinkedGui(this);
				//newKit.MakeHorizontal();
				newKit.setCurrRotation(90);
				coverCount = 0;
			}
		
			if(newKit != null)
			{
				if(newKit.getCurrentY() != currY-HEIGHTDISPLACEMENT)
				{
					newKit.setLocation((currX + ((currX - Images.Kit.getIconWidth())/2)), newKit.getCurrentY());
					
					Random r = new Random();
					Boolean rand = r.nextBoolean();
					if (rand) 
					{
						newKit.setLocation(newKit.getCurrentX() + vibrationSpeed, newKit.getCurrentY());
					} 
					else 
					{
						newKit.setLocation(newKit.getCurrentX() - vibrationSpeed, newKit.getCurrentY());
					}
				}
				newKit.setDestination((currX + ((currX - Images.Kit.getIconWidth())/2)), currY - HEIGHTDISPLACEMENT);
				newKit.updateLocation();

			}
		
			coverCount++;
		}
		/*
		else //Power = off
		{
			if(newKit != null)
			{
				newKit.setLocation(newKit.getCurrentX() + vibrationSpeed, newKit.getCurrentY());
			}
		}
		*/
	}
	
	public void partCallback(Gui part) 
	{
		if(!alreadyMessaged)
		{
			myAgent.msgAnimationDone();
			alreadyMessaged = true;
		}
	}

	public int getCurrentX() 
	{
		return currX;
	}

	public int getCurrentY() 
	{
		return currY;
	}

	public int getDestinationX() 
	{
		return destX;
	}

	public int getDestinationY() 
	{
		return destY;
	}

	public void setDestination(int x, int y) 
	{
		destX = x;
		destY = y;
	}

	public void doCreateAKit(AgentKit createdKit) 
	{
		createBox = true;
		newKitAgent = createdKit;
		hasKit = true;
	}

	public void msgPickedUpKit()
	{
		//newKit = null;
		//hasKit = false;
	}

	public void enable() 
	{
		myState = Power.ON;
	}

	public void disable() 
	{
		myState = Power.OFF;
		if(newKit != null){
			newKit.disable();
		}
	}

	public Power checkPower() 
	{
		return myState;
	}

	public void destroy() 
	{
		alreadyBroken = true;
		disable();
	}

	public void repair() 
	{
		enable();
	}

	public boolean checkRepair() 
	{
		return alreadyBroken;
	}
}
