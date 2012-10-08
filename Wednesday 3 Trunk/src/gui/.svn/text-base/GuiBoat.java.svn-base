/**
 * @author Tyler Gregg
 * The GuiBoat is the visual representation of the
 * GuiBin delivery system.  The GuiBoat starts
 * off screen with a full GuiBin, waiting for the
 * GantryAgent to tell it to move to a position
 * on screen to unload the bin.  The GuiBoat then
 * waits for the GantryRobot to give it an empty
 * bin, after which it moves downward off screen.
 */
package gui;
import engine.agents.GantryAgent;
import gui.interfaces.Gui;
import gui.locationsimg.Images;
import gui.locationsimg.Locations;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import gui.holdable.GuiBin;
public class GuiBoat implements Gui {
	private GuiBin bin = null;
	private int currX, currY, destX, destY;
	private boolean hasBin, isOn;
	private ImageIcon image;
	private GantryAgent agent = null;
	private final int speed = 5;
	public enum State { 
       	READY,
       	MOVETOGANTRY,
       	MOVEDOWN,
		UNLOAD
	};
    public State presentState;
    public GuiBoat(){
    	image = Images.Boat;
    	presentState = State.READY;
    	hasBin = false;
    	currX = Locations.boatArriveOffScreenX;
    	currY = Locations.boatArriveOffScreenY;
    	isOn = true;
    }
    public void setAgent(GantryAgent newagent){
    	agent = newagent;
    }
	@Override
	public void draw(Graphics g) {
		//if(isOn){
			Image imagetemp = image.getImage();
			g.drawImage(imagetemp, currX, currY, null);
			if(hasBin){
				bin.draw(g);
				for(int i = 0; i < speed; i++){
					bin.updateLocation();
				}
			}
		//}
		//updateLocation();
	}
    public void removeBin(){
    	bin = null;
    	hasBin = false;
    	presentState = State.MOVEDOWN;
    	stateChanged();
    }
	@Override
	public void updateLocation() {
		if(!isOn){
			return;
		}
        if(presentState == State.MOVETOGANTRY){
        	if((currX == destX) && (currY == destY)){
        		if(hasBin){
        			agent.DoneMovingToUnload(bin);
        		}
        		presentState = State.UNLOAD;
        		//stateChanged();
        		return;
        	}
        	else{
        		if (currX < destX) {
        			currX = currX + speed;
        		}
        		else if (currX > destX) {
        			currX = currX - speed;
        		}
        		if (currY < destY) {
        			currY = currY + speed;
        		} 
        		else if (currY > destY) {
        			currY = currY - speed;
        		}
        	}
		}
        else if(presentState == State.MOVEDOWN){
        	if((currX == destX) && (currY == destY)){
            	currX = Locations.boatArriveOffScreenX;
            	currY = Locations.boatArriveOffScreenY;
            	setDestination(currX, currY);
            	if(hasBin){
            		bin.setLocation(Locations.binArriveOffScreenX, Locations.binArriveOffScreenY);
            		bin = null;
            		hasBin = false;
            		agent.RefillBoat();
            	}
            	else{
            		presentState = State.READY;
            	}
        		return;
        	}
        	else{
        		if (currX < destX) {
        			currX = currX + speed;
        		}
        		else if (currX > destX) {
        			currX = currX - speed;
        		}
        		if (currY < destY) {
        			currY = currY + speed;
        		} 
        		else if (currY > destY) {
        			currY = currY - speed;
        		}
        	}
        }
	}
	private void stateChanged(){
		switch(presentState){
		case MOVETOGANTRY:
			destX = Locations.boatArrivalX;
			destY = Locations.boatArrivalY;
			if(hasBin){
				bin.setDestination(Locations.binOnArrivalX, Locations.binOnArrivalY);
			}
			break;
		case MOVEDOWN:
			destX = Locations.boatArriveDoneOffScreenX;
			destY = Locations.boatArriveDoneOffScreenY;
			if(hasBin){
				bin.setDestination(Locations.binArriveDoneOffScreenX, Locations.binArriveDoneOffScreenY);
			}
			break;
		case UNLOAD:
			//stay at gantry
			break;
		}
	}
	public void doBringBinToGantry(){
		if(isOn){
			bin = new GuiBin(Locations.binArriveOffScreenX, Locations.binArriveOffScreenY);
			hasBin = true;
			presentState = State.MOVETOGANTRY;
			stateChanged();
		}
	}
	public void doBringEmptyBoatToGantry(){
		if(isOn){
			presentState = State.MOVETOGANTRY;
			stateChanged();
		}
	}
	public void doReceiveBinFromGantry(GuiBin newbin){
		bin = newbin;
		hasBin = true;
		presentState = State.MOVEDOWN;
		stateChanged();
	}

	public void wave(){
		if(hasBin){
			bin = null;
			hasBin = false;
		}
	}
	public void flipOnOffState(){ //name will need to be changed to conform with non-norm api
		presentState = State.MOVEDOWN; //send the boat offscreen
		stateChanged();
	}
	public boolean getHasBin(){
		return hasBin;
	}
	@Override
	public void partCallback(Gui part) {
		//do nothing
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