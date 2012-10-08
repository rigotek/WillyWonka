/**
 * @author Tyler Gregg
 * The GuiBelt serves as a superclass for belt
 * objects like the GuiFeeder.
 */
package gui.superclasses;
import java.awt.Graphics;
import gui.interfaces.Gui;
public abstract class GuiBelt implements Gui {
  protected int currX;
  protected int currY;
  protected int destX;
  protected int destY;
  
  public GuiBelt(int newX, int newY){
	  currX = newX;
	  currY = newY;
	  destX = currX; //stationary
	  destY = currY; //stationary
  }
  
  public void draw(Graphics g){
	  //delegated to each subclass
  }
  
  public void updateLocation(){
	  //delegated to each subclass
  }
  
  public void partsCallback(){
    //do nothing;
  }
  
  public int getCurrentX(){
    return currX;
  }
  
  public int getCurrentY(){
    return currY;
  }
 
  public int getDestinationX(){
    return destX;
  }
  
  public int getDestinationY(){
    return destY;
  }
 
  
  public void stateChanged(){
    //check the 'state' variable and update
    //xDest and yDest accordingly
	//delegated to each subclass
  }
}

//==========================================================