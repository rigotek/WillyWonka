/**
 * @author Tyler Gregg
 * The GuiFeeder waits for the FeederAgent to give
 * it a GuiBin from the GantryRobot, and moves the
 * bin to the end of the feeder, where its GuiCandy
 * can be unloaded onto the GuiDiverter.
 */
package gui;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import engine.agents.FeederAgent;
import gui.superclasses.GuiBelt;
import gui.holdable.GuiBin;
import gui.interfaces.Gui;
import gui.interfaces.GuiFeederInterface;
import gui.locationsimg.Images;

public class GuiFeeder extends GuiBelt implements GuiFeederInterface {
    private FeederAgent agent = null;
    private int width;
    private int height;
    private GuiBin bin = null;
    private boolean hasBin, binAtEnd, isOn;
    private int xStart, yStart, xEnd, yEnd;
    private ImageIcon image, red, green, idle;
	public enum State { 
       	READY,
       	MV_BIN,
       	PRG_BIN,
       	RETURN,
		UNLOAD
	};
    public State presentState; 
    
    public GuiFeeder(int xVal, int yVal){
    	super(xVal, yVal);
    	presentState = State.READY;
    	binAtEnd = false;
    	xEnd = xVal;
    	yEnd = yVal;
    	image = Images.Feeder;
    	Image tempimage = image.getImage();
    	width = tempimage.getWidth(null);
    	height = tempimage.getHeight(null);
    	xStart = xEnd + width;
    	yStart = yEnd;
    	red = Images.FeederStop;
    	green = Images.FeederGo;
    	idle = Images.FeederIdle;
    	hasBin = false;
    	isOn = true;
    }
    
    public void setAgent(FeederAgent newagent){
    	agent = newagent;
    }
    
	public void draw(Graphics g){
		//extract/draw the feeder image
		Image imagetemp = image.getImage();
		g.drawImage(imagetemp, currX, currY, null);
		//if the feeder has a bin, draw it
		//if(isOn){
			if(hasBin){
				bin.draw(g);
				bin.updateLocation();
			}
			//if the bin is not at the end, draw red light
			if(isOn){
				if(!binAtEnd){
					Image redtemp = red.getImage();
					g.drawImage(redtemp, currX, currY, null);
				}
				//if the bin is at the end, draw green light
				else{
					Image greentemp = green.getImage();
					g.drawImage(greentemp, currX, currY, null);
				}
			}
			else{
				Image idletemp = idle.getImage();
				g.drawImage(idletemp, currX, currY, null);
			}
			updateLocation();
		//}
	}
	
    public void updateLocation(){
    	//compare x with xDest and y with yDest,
        //updating x and y accordingly
        //super.updateLocation();
        if(hasBin){
        	//bin has reached end of feeder
	        if((presentState == State.MV_BIN) && (bin.getCurrentX() == bin.getDestinationX()) && (bin.getCurrentY() == bin.getDestinationY())){
	        	agent.doneMoveBinDownFeederLane();
	        	//System.out.println("Gui: done.");
	        	presentState = State.UNLOAD;
	        	stateChanged();    
	        }
	        //bin has reached start of feeder
	        else if((presentState == State.PRG_BIN) && (bin.getCurrentX() == bin.getDestinationX()) && (bin.getCurrentY() == bin.getDestinationY())){
	        	agent.donePurgeBin();
	        	System.out.println("Gui: done purging");
				//bin = null;
				//hasBin = false;
	        	presentState = State.READY;
				stateChanged();
	        }
        }
        //the feeder does not have a bin, is ready
        else{
        	presentState = State.READY;
        	stateChanged();
        }
    }
     
    public void stateChanged(){
     	//check the 'state' variable and update
     	//xDest and yDest of the bin accordingly	
     	switch(presentState){
     		case MV_BIN:
     	    	//System.out.println("Gui: Move bin in stateChange");
     			//set xDest and yDest to end of belt
     	    	if(hasBin){
     	    		bin.setDestination(currX, currY + (height/2 - bin.getHeight() / 2));
     	    		binAtEnd = false;
     	    	}
     		break;
     		case PRG_BIN:
     			//set xDest and yDest to start of belt
     			if(hasBin){
     				bin.setDestination(xStart - bin.getWidth(), yStart + (height/2 - bin.getHeight() / 2));
     				binAtEnd = false;
     			}
     		break;
     		case RETURN:
     			//set xDest and yDest to start of belt
     			binAtEnd = false;
     		break;
			case UNLOAD:
				//wait at the end of the belt
				binAtEnd = true;
			break;
     	}
    }
     
    public void doMoveBinDownFeederLane(GuiBin newbin) {
     	//System.out.println("Gui: Moving bin down lane");
    	//Called by the FeederAgent
    	//Set the new bin
    	bin = newbin;
     	hasBin = true;
     	//Move one bin to the end of the belt
     	presentState = State.MV_BIN;
     	stateChanged();
    }
     
     public void doPurgeBin(){
     	//return the bin to the robot
    	//called by the FeederAgent
     	presentState = State.PRG_BIN;
     	stateChanged();
    }
     
    public void removeBin(){
    	bin = null;
    	hasBin = false;
    }

	public boolean beltHasBin(){
		return hasBin;
	}

	public void partCallback(Gui part) {
		// Do nothing
		
	}

	public void setDestination(int xNew, int yNew) {
		destX = xNew;
		destY = yNew;
	}
	
	public void doFlipOnOffState(boolean onOff){
		if(!onOff){
			isOn = false;
		}
		else{
			isOn = true;
		}
	}
	public void doChocolateWave(){
		if(hasBin){
			bin = null;
			hasBin = false;
		}
	}

	@Override
	public Power checkPower() {
		// TODO Auto-generated method stub
		if(isOn){
			return Power.ON;
		}
		else{
			return Power.OFF;
		}
	}

	@Override
	public boolean checkRepair() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		isOn = false;
		if(hasBin){
			bin.disable();
		}
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		isOn = true;
		if(hasBin){
			bin.enable();
		}
	}

	@Override
	public void repair() {
		// TODO Auto-generated method stub
		
	}
}

//==========================================================