package gui.superclasses;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import gui.holdable.Holdable;
import gui.interfaces.Gui;
import gui.interfaces.Gui.Power;
import gui.locationsimg.Images;

//@author Sumukh Anand

public abstract class GuiRobot implements Gui {
	protected int currX;
	protected int currY;
	protected int destX;
	protected int destY;
	protected int currAngle;
	protected int destAngle;
	
	protected int numContents;
	protected boolean waitForRotation;
	protected Holdable[] armContents;
	
	protected boolean isRevolting;
	protected int oldDestX;
	protected int oldDestY;
	protected int oldAngle;
	
	protected boolean broken;

	public GuiRobot(int x, int y){
		currX = x;
		currY = y;
		destX = x;
		destY = y;
		armContents = new Holdable[4];
		numContents = 0;
		waitForRotation = false;
		isRevolting = false;
		broken = false;
	}
	
	public GuiRobot(){
		currX = 0;
		currY = 0;
		destX = 0;
		destY = 0;
		armContents = new Holdable[4];
		numContents = 0;
		waitForRotation = false;
		isRevolting = false;
		broken = false;
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform affineTransform;
		
		affineTransform = new AffineTransform(); 
		affineTransform.setToTranslation(currX, currY); 
		affineTransform.rotate(Math.toRadians(currAngle), Images.Robot.getIconWidth()/2, Images.Robot.getIconHeight()/2); 
		g2d.drawImage(Images.Robot.getImage(), affineTransform, null); 
		
		for (int i=0; i<4; i++){
			if (numContents>0 && armContents[i]!=null){
				armContents[i].setLocation(currX+Images.Robot.getIconWidth()/2, currY+Images.Robot.getIconHeight()/2);
				armContents[i].setCurrRotation(currAngle - i*90);
				armContents[i].setTranslation(Images.Robot.getIconWidth()/2, -armContents[i].image.getIconHeight()/2);
				armContents[i].draw(g);
			}
		}
	}

	@Override
	public void updateLocation() {
		if (broken)
			return;
		
		if (Math.abs(currAngle) > 360){
			destAngle = destAngle%360;
			currAngle = currAngle%360;
		}
		
		for (int i=0; i<4; i++){
			if (numContents>0 && armContents[i]!=null){
				armContents[i].updateLocation();
			}
		}
		
		if (currX < destX)
        	currX++;
        else if (currX > destX)
        	currX--;
        if (currY < destY)
        	currY++;
        else if (currY > destY)
        	currY--;
        else if (isRevolting){
        	destY = (int)(Math.random()*700);
    		destX = (int)(Math.random()*1000);
        }
        
        if (currAngle < destAngle)
        	currAngle++;
        else if (currAngle > destAngle)
        	currAngle--;
        else{
        	waitForRotation = false;
        	if (isRevolting)
        		destAngle = (int)(Math.random()*360);
        }
	}

	@Override
	public void partCallback(Gui part) {
	}

	@Override
	public int getCurrentX() {
		return currX;
	}

	@Override
	public int getCurrentY() {
		return currY;
	}

	@Override
	public int getDestinationX() {
		return destX;
	}

	@Override
	public int getDestinationY() {
		return destY;
	}

	@Override
	public void setDestination(int x, int y) {
		destX = x;
		destY = y;
	}
	
	public void revolt(){
		isRevolting = true;
		oldDestX = destX;
		oldDestY = destY;
		oldAngle = destAngle;
		destY = (int)(Math.random()*700);
		destX = (int)(Math.random()*1000);
		destAngle = (int)(Math.random()*360);
	}
	
	public void endRevolt(){
		destX = oldDestX;
		destY = oldDestY;
		destAngle = oldAngle;
		isRevolting = false;
	}
	
	@Override
	public Power checkPower() {
		if (broken)
			return Power.OFF;
		else
			return Power.ON;
	}

	@Override
	public boolean checkRepair() {
		return false;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void disable() {
		broken = true;
		for (int i=0; i<numContents; i++){
			armContents[i].disable();
		}
	}

	@Override
	public void enable() {
		broken = false;
		for (int i=0; i<numContents; i++){
			armContents[i].enable();
		}
	}

	@Override
	public void repair() {
	}
	
	public void emptyHands() {
		//Clear hands
		for (int i=0; i<numContents; i++){
			armContents[i] = null;
		}
		numContents = 0;
	}
}
