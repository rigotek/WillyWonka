/**
 * @author Tyler Gregg
 * The GuiBin serves as the container for the 
 * GuiCandy before it is unloaded from the
 * GuiFeeder onto the GuiDiverter.
 */
package gui.holdable;
import java.awt.Graphics;
import java.awt.Image;
//import java.util.Stack;

//import gui.GuiFeeder.State;
import gui.holdable.Holdable;
import gui.interfaces.Gui;
import gui.locationsimg.Images;
public class GuiBin extends Holdable {
    //Stack<Holdable> objectStack = new Stack<Holdable>(); //box can contain objects
    private int width;
    private int height;
    private boolean isOn;
    
    public GuiBin(int locX, int locY){
    	super(locX, locY);
    	image = Images.Bin;
    	Image tempimage = image.getImage();
    	width = tempimage.getWidth(null);
    	height = tempimage.getHeight(null);
    	isOn = true;
	}
	
	public void draw(Graphics g){
		//draw the box in the correct location
		//extract the bin image from the imageicon
		super.draw(g);
		
		//Image imagetemp = image.getImage();
		//g.drawImage(imagetemp, currX, currY, null);
		//updateLocation();
	}
	public void updateLocation(){
		if(isOn){
			super.updateLocation();
		}
	}
	/*
	public void fillBin(Holdable newCandy){
		//add to the stack
		objectStack.push(newCandy);
	}
	 */
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}

	public void partCallback(Gui part) {
		// do nothing
	}
	
	public int getCurrentX() {
		return currX;
	}

	public int getCurrentY() {
		return currY;
	}

	public int getDestinationX() {
		return destX;
	}

	public int getDestinationY() {
		return destY;
	}

	public void enable(){
		isOn = true;
	}
	public void disable(){
		isOn = false;
	}
	public Power checkPower(){
		if(isOn){
			return Power.ON;
		}
		else{
			return Power.OFF;
		}
	}
}
//==========================================================