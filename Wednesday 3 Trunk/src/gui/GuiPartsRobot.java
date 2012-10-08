
package gui;

import java.util.ArrayList;

import engine.Agent;
import engine.agents.PartsRobotAgent;
import gui.holdable.GuiCandy;
import gui.holdable.GuiKit;
import gui.holdable.Holdable;
import gui.interfaces.Gui;
import gui.interfaces.GuiPartsRobotInterface;
import gui.locationsimg.Images;
import gui.locationsimg.Locations;
import gui.superclasses.GuiRobot;

//@author Sumukh Anand

public class GuiPartsRobot extends GuiRobot implements Gui, GuiPartsRobotInterface {
	public enum State {
		FREE,
		TO_IDLE,
		AT_IDLE,
		TO_KIT,
		AT_KIT,
		TO_NEST, 
		AT_NEST
	}

	private State currState;
	private ArrayList<GuiCandy> partsToLoad;
	private PartsRobotAgent partsRobot;
	private GuiKit destKit;

	public GuiPartsRobot(int x, int y) {
		super(x, y);
		currState = State.FREE;
	}

	public GuiPartsRobot() {
		super();
		currX = Locations.partsRobotX;
		currY = Locations.partsRobotY;
		destX = Locations.partsRobotX;
		destY = Locations.partsRobotY;
		currState = State.FREE;
	}

	@Override
	public void updateLocation(){
		if (isRevolting || broken){
			super.updateLocation();
			return;
		}

		//Send messages according to State
		switch(currState){
		case TO_IDLE:
			break;

		case AT_IDLE :
			currState = State.FREE;
			break;

		case TO_NEST:
			if (currX==destX && currY==destY && currAngle==destAngle){
				if (partsToLoad.isEmpty()){
					currState = State.AT_NEST;
				}
				else {
					//rotate and pick up all objects from partsToLoad
					if (waitForRotation){
						break;
					}
					else if (numContents<4 && !partsToLoad.isEmpty()){
						armContents[numContents] = (Holdable) partsToLoad.get(0);
						numContents++;
						partsToLoad.remove(0);
						if (!partsToLoad.isEmpty()){
							destAngle+=90;
							waitForRotation = true;
						}
					}
				}

			}
			break;

		case AT_KIT:
			partsRobot.msgPartsSent();
			DoGoToIdle();
			break;

		case TO_KIT:
			if (currX==destX && currY==destY && currAngle==destAngle){
				//rotate and drop off all objects from armContents
				if (numContents==0){
					currState = State.AT_KIT;
				}
				else {
					if (waitForRotation){
						break;
					}
					else if (numContents>0) {
						int i = 0;
						while (armContents[i]==null){
							i++;
						}
						armContents[i].resetImage();
						armContents[i] = null;
						numContents--;
						destKit.resetImage();
						if (numContents!=0){
							destAngle+=90;
							waitForRotation = true;
						}
					}
				}
			}
			break;

		case AT_NEST:
			partsRobot.msgPartsReceived();
			currState = State.FREE;
			break;
		}

		super.updateLocation();
	}

	@Override
	public void setAgent(Agent agent) {
		//Set agent
		partsRobot = (PartsRobotAgent) agent;
	}

	@Override
	public void DoGoToIdle() {
		//Set destination X and Y to idle X and Y
		destX = Locations.partsRobotX;
		destY = Locations.partsRobotY;
		destAngle = 0;
		currState = State.TO_IDLE;
		
		//emptyHands();
	}

	@Override
	public void DoRetrieveParts(Gui nest, ArrayList<GuiCandy> parts) {
		//Set destination X and Y to the GuiNest X and Y
		destX = nest.getCurrentX() - Images.Robot.getIconWidth();
		destY = nest.getCurrentY() - 40;
		destAngle = 0;
		partsToLoad = parts;
		currState = State.TO_NEST;

		//emptyHands();
	}

	@Override
	public void DoSendParts(Gui kitStand, Gui kit, ArrayList<GuiCandy> parts) {
		//Set destination X and Y to GuiKit X and Y
		destX = kit.getCurrentX() + Images.Kit.getIconWidth();
		destY = kit.getCurrentY() - 40;
		destAngle = 180;
		destKit = (GuiKit)kit;
		currState = State.TO_KIT;
	}
}
