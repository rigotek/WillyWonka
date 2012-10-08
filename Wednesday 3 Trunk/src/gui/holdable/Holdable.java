package gui.holdable;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import gui.interfaces.Gui;
import gui.interfaces.Gui.Power;

public abstract class Holdable implements Gui{
	public boolean update = true;
	public ImageIcon image;
	protected int currX;
	protected int currY;
	protected int currAngle;
	protected int destAngle;
	protected int destX;
	protected int destY;
	protected int transX;
	protected int transY;
	private Gui linkedGui;

	public Holdable(int x, int y) {
		currX = x;
		currY = y;
		destX = x;
		destY = y;
		currAngle = 0;
		destAngle = 0;
		transX = 0;
		transY = 0;
	}

	public Holdable(int x, int y, Gui o) {
		// Constructor
		currX = x;
		currY = y;
		destX = x;
		destY = y;
		currAngle = 0;
		destAngle = 0;
		transX = 0;
		transY = 0;
		linkedGui = o;
	}

	@Override
	public void draw(Graphics g){ 
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform affineTransform;
		affineTransform = new AffineTransform();
		affineTransform.setToTranslation(currX, currY); 
		if (transX == 0 && transY == 0){
			affineTransform.rotate(Math.toRadians(currAngle), image.getIconWidth()/2, image.getIconHeight()/2);
		}
		else {
			affineTransform.rotate(Math.toRadians(currAngle), 0, 0);
		}
		affineTransform.translate(transX, transY);
		g2d.drawImage(image.getImage(), affineTransform, null);
	};

	@Override
	public void updateLocation(){
		//move to destX and destY values;
		//if destination reached
		//linkedGui.partsCallback();
		
		if (Math.abs(currAngle) > 360){
			destAngle = destAngle%360;
			currAngle = currAngle%360;
		}

		if (currX < destX) {
			currX++;
		} else if (currX > destX) {
			currX--;
		}
		if (currY < destY) {
			currY++;
		} else if (currY > destY) {
			currY--;
		}
		
		if (currAngle < destAngle)
        	currAngle++;
        else if (currAngle > destAngle)
        	currAngle--;

		if (currX == destX && currY == destY && linkedGui!=null) {
			callBackGui();
		}
	};

	public void callBackGui() {
		linkedGui.partCallback(this);
	}
	
	@Override
	public void partCallback(Gui part){ };

	@Override
	public int getCurrentX(){
		return currX;
	}

	@Override
	public int getCurrentY(){
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
	public void setDestination(int x, int y){
		destX = x;
		destY = y;
	}
	
	public void setLocation(int x, int y){
		currX = x;
		currY = y;
		setDestination(x, y);
	}
	
	public void setLinkedGui(Gui o) {
		linkedGui = o;
	}
	
	public void setDestRotation(int a){
		destAngle = a;
	}

	public void setCurrRotation(int a){
		currAngle = a;
		setDestRotation(a);
	}

	public void setTranslation(int x, int y) {
		transX = x;
		transY = y;
	}
	
	public void resetImage(){
		transX = 0;
		transY = 0;
	}
	
	public boolean isRotated(){
		if (transX!=0 || transY!=0)
			return true;
		else return false;
	}
	
	@Override
	public Power checkPower() {
		return null;
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
	}

	@Override
	public void enable() {
	}

	@Override
	public void repair() {
	}
}