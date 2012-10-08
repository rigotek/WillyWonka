/**
 * @author Jungho Lee
 * Here we have a ripple on the ocean.
 * The ripple will take candy, bin, or whatever
 * implements holdable.
 */
package gui;

import java.awt.Graphics;
import java.util.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

import gui.holdable.Holdable;
import gui.interfaces.Gui;
import gui.interfaces.GuiCandyXYInterface;
import gui.locationsimg.Images;

public class GuiOcean implements Gui {


	private LinkedList<Holdable> candyQueue;
	private LinkedList<GuiCandyXYInterface> candyXYQueue;
	
	private int currX;

	private int currY;

	private int currAngle;

	private int centerX;

	private int centerY;
	
	private boolean showUp;

public GuiOcean (int x, int y) {

	currX = x;
	currY = y;
	centerX = x + Images.Ripple.getIconWidth()/2;
	centerY = y + Images.Ripple.getIconHeight()/2;	
	showUp = false;
	candyQueue = new LinkedList<Holdable>();
	candyXYQueue = new LinkedList<GuiCandyXYInterface>();
}


public void draw(Graphics g) {
// draw ripple
	Graphics2D g2d = (Graphics2D) g;
	AffineTransform tx;
	
	tx = new AffineTransform(); 
	tx.setToTranslation(currX, currY); 
	tx.rotate(Math.toRadians(currAngle), Images.Ripple.getIconWidth()/2, Images.Ripple.getIconHeight()/2); 
	
	if(showUp)
	{
		g2d.drawImage(Images.Ripple.getImage(), tx, null);
		for (int i = 0; i < candyQueue.size(); i++) 
		{
		
			g2d.drawImage(candyQueue.get(i).image.getImage(), candyQueue.get(i).getCurrentX(), candyQueue.get(i).getCurrentY(),null);
		}
	}
	// Draw all parts on the ripple
	
}

public void updateLocation() {
	// ripple angle update
	currAngle += 5;
	currAngle = currAngle % 360;
	// move holdable items
	AdvanceCandies();
	for (int i = 0; i < candyQueue.size(); i++) {
		candyQueue.get(i).setLocation(centerX + (candyXYQueue.get(i).getCandyX() - (candyQueue.get(i).image.getIconWidth()/2)), centerY + (candyXYQueue.get(i).getCandyY() - (candyQueue.get(i).image.getIconHeight()/2)));		
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
// take holdable items, and gives an initial position
public void DoMoveToRipple(Holdable p) 
{
	showUp = true;
	p.setLinkedGui(this);
	GuiCandyXYInterface toAdd = new GuiCandyXYInterface(p.getCurrentX() + (p.image.getIconWidth()/2), p.getCurrentY() + (p.image.getIconHeight()/2), centerX, centerY);
	candyQueue.add(p);
	candyXYQueue.add(toAdd);
}
// advance holdable items
public void AdvanceCandies() {
	
	for (int i = 0; i < candyQueue.size(); i++) {
		candyXYQueue.get(i).advCandyAngle();
		candyXYQueue.get(i).advCandyRadius();
		//System.out.println(candyXYQueue.get(i).getCandyRadius() + " " + candyQueue.get(i).getCurrentX() + " " + candyQueue.get(i).getCurrentY());
		
		if (candyXYQueue.get(i).getCandyRadius() <= 1) {
			candyQueue.removeFirst();
			candyXYQueue.removeFirst();
			if(candyQueue.size() == 0)
			{
				showUp = false;
			}
		}
	}
}

public void partCallback(Gui part) {
	// TODO Auto-generated method stub
	
}


public void setDestination(int x, int y) {
	// TODO Auto-generated method stub
	
}


@Override
public void enable() {
	// TODO Auto-generated method stub
	
}


@Override
public void disable() {
	// TODO Auto-generated method stub
	
}


@Override
public Power checkPower() {
	// TODO Auto-generated method stub
	return Power.ON;
}


@Override
public void destroy() {
	// TODO Auto-generated method stub
	
}


@Override
public void repair() {
	// TODO Auto-generated method stub
	
}


@Override
public boolean checkRepair() {
	// TODO Auto-generated method stub
	return false;
}


}