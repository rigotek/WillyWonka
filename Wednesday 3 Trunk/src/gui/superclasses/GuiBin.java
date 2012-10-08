//=========================TylerGregg=========================
package gui.superclasses;
import java.awt.Graphics;
import java.util.Stack;

import gui.holdable.Holdable;
import gui.interfaces.Gui;
public class GuiBin extends Holdable { //empty box
    int currX;
    int currY;
    int destX;
    int destY;
        
    //private BinAgent agent = null;
    Stack<Holdable> objectStack = new Stack<Holdable>(); //box can contain objects
    
    
    public GuiBin(/*BinAgent agent,*/ int locX, int locY){
      	//this.agent = agent;
    	super(locX, locY);
	}
	
	
	public void draw(Graphics g){
		//draw the box in the correct location
	}
	
	public void fillBin(Holdable newCandy){
		//add to the stack
		objectStack.push(newCandy);
	}


	@Override
	public void partCallback(Gui part) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getCurrentX() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getCurrentY() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getDestinationX() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int getDestinationY() {
		// TODO Auto-generated method stub
		return 0;
	}
}
//==========================================================