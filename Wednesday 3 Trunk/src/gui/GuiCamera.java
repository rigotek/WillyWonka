package gui;

import engine.agents.KitCameraAgent;
import engine.agents.LaneCameraAgent;
import gui.interfaces.Gui;
import gui.interfaces.GuiCameraInterface;
import gui.locationsimg.Images;

import java.awt.Graphics;


/**
 * 
 * @author Jonathan Chu
 *
 */
public class GuiCamera implements GuiCameraInterface, Gui 
{
	 int currX;
     int currY;
     int waitCount = 0;
     int destX = 0;
     int destY = 0;
     int flashCount;
     int whichVersion; //Tells if it is a Kit Camera or a Lane Camera
     //1 -Lane Camera --> 2 -Kit Camera
     static final int radius = Images.Flash.getIconHeight()/2;
     private LaneCameraAgent myLaneCamera = null;
     private KitCameraAgent myKitCamera = null;
     private boolean readyToTakePicture;
     private boolean alreadyBroken;
     private Power myState;
     //currX, currY denote the center of the flash
     
     public GuiCamera(int locX, int locY)
     {
    	 readyToTakePicture = false;
    	 alreadyBroken = false;
    	 currX = locX;
    	 currY = locY;
    	 flashCount = 0;
    	 whichVersion = 0;
    	 myState= Power.ON;
     }
     
     public void draw(Graphics g)
     {
    	 //This draws the idle flash station on top of the checking station
		 //g.drawImage(gui.locationsimg.Images.FlashIdle.getImage(), gui.locationsimg.Locations.cameraCheckingStationX, gui.locationsimg.Locations.cameraCheckingStationY, null);
		 if(myState == Power.ON){
	    	 g.drawImage(gui.locationsimg.Images.FlashIdle.getImage(), currX, currY, null);
	    	 //System.out.print("I am drawing");
	    	 //if(myState == Power.ON){
		    		if(readyToTakePicture)
		    		{
		    			if(waitCount >= 40)
		    			{
		    				//DRAW Flash
		    				//g.setColor(Color.GRAY);
		    				//g.fillRect(currX, currY, 300, 300);
		    				g.drawImage(gui.locationsimg.Images.Flash.getImage(), currX, currY, null);
		    			}
		    		}
	    	 //}
		 }
		 else{
			 g.drawImage(gui.locationsimg.Images.FlashOff.getImage(), currX, currY, null);
		 }
     }

     public void updateLocation()
     {
    	 //Does Nothing
    	 if(myState == Power.ON)
    	 {
    		 if(readyToTakePicture)
    		 {
    			 if(waitCount >= 80)
    			 {
    				 flashCount++; 
    				 if(flashCount >= 30)
    				 {
    					 flashCount = 0;
    					 if(whichVersion == 1)
    					 {
    						 myLaneCamera.msgDoneTakingPicture();
    					 }
    					 else
    					 {
    						 if(whichVersion == 2)
    						 {
    							 myKitCamera.msgDoneTakingPicture();
    						 }	
    					 }
    					 readyToTakePicture = false;
    					 waitCount = 0;
    				 }	
    			 }
    			 waitCount++;
		 	}
    	 }
    	 else
    	 {
    		 readyToTakePicture = false;
    		 flashCount = 0;
    	 }
     }



     public void partsCallback()
     {
         //Tells lane that part has moved to specific location. Starts lining up part in lane.
     }
     
     public void setAgent(LaneCameraAgent newLaneCameraAgent)
     {
    	 whichVersion = 1;
    	 myLaneCamera = newLaneCameraAgent;
     }

     
     public void setAgent(KitCameraAgent newKitCameraAgent)
     {
    	 whichVersion = 2;
    	 myKitCamera = newKitCameraAgent;
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

     public void doTakePicture()
     {
          readyToTakePicture = true;
     }

	public void partCallback(Gui part) 
	{
		//Does Nothing
	}

	public void setDestination(int x, int y) 
	{
		//Does Nothing
		
	}

	public void enable() 
	{
		myState = Power.ON;
	}

	public void disable() 
	{
		myState = Power.OFF;
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