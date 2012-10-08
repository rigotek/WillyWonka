package gui;

import engine.Agent;
import engine.agents.GantryAgent;
import gui.GuiKitRobot.State;
import gui.holdable.GuiBin;
import gui.interfaces.Gui;
import gui.interfaces.GuiGantryRobotInterface;
import gui.locationsimg.Images;
import gui.locationsimg.Locations;
import gui.superclasses.GuiRobot;
import gui.GuiBoat;

//@author Sumukh Anand

public class GuiGantryRobot extends GuiRobot implements Gui, GuiGantryRobotInterface {

	private enum State {
		FREE,
		TO_IDLE,
		AT_IDLE,
		TO_BOAT_PICKUP,
		TO_BOAT_DROPOFF,
		AT_GANTRY_BOAT,
		TO_FEEDER_DROPOFF,
		TO_FEEDER_PICKUP,
		AT_FEEDER
	}

	private State currState;
	private GuiBin binToLoad;
	private GantryAgent gantryRobot;
	private GuiFeeder feederReference;
	private GuiBoat boatReference;

	public GuiGantryRobot() {
		super();
		currState = State.FREE;
		currX = Locations.gantryRobotX;
		currY = Locations.gantryRobotY;
		destX = Locations.gantryRobotX;
		destY = Locations.gantryRobotY;
	}

	public GuiGantryRobot(int x, int y){
		super(x,y);
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

		case AT_IDLE:
			currState = State.FREE;
			break;

		case TO_BOAT_PICKUP:
			if (currX==destX && currY==destY && currAngle==destAngle){
				armContents[0] = binToLoad;
				numContents++;
				boatReference.removeBin();
				currState = State.AT_GANTRY_BOAT;
			}
			break;
		
		case TO_BOAT_DROPOFF:
			if (currX==destX && currY==destY && currAngle==destAngle){
				if (numContents!=0){
					armContents[0].resetImage();
					armContents[0].setLocation(currX + Images.Robot.getIconWidth(), currY + 40);
					armContents[0] = null;
					numContents--;
					gantryRobot.DonePurgingBin();
				}
			}
			break;

		case AT_GANTRY_BOAT:
			gantryRobot.DoneRefillingBin();
			break;

		case TO_FEEDER_DROPOFF:
			if (currX==destX && currY==destY && currAngle==destAngle){
				armContents[0].resetImage();
				armContents[0].setLocation(currX - Images.Bin.getIconWidth(), currY + 40);
				armContents[0] = null;
				numContents--;
				currState = State.AT_FEEDER;
			}
			break;
		
		case TO_FEEDER_PICKUP:
			if (currX==destX && currY==destY && currAngle==destAngle){
				if (numContents==0){
					armContents[0] = binToLoad;
					numContents++;
					feederReference.removeBin();
				}
				destX = Locations.binOnArrivalX - Images.Robot.getIconWidth();
				destY = Locations.binOnArrivalY - 40;
				destAngle = 0;
				currState = State.TO_BOAT_DROPOFF;
			}
			break;

		case AT_FEEDER:
			gantryRobot.DoneLoadingBin();
			currState = State.AT_IDLE;
			break;
		}	
		
		super.updateLocation();
	}

	@Override
	public void setAgent(GantryAgent agent) {
		//Set the agent
		gantryRobot = agent;
	}

	@Override
	public void DoGoToIdle() {
		//Set destination X and Y to idle X and Y
		destX = Locations.gantryRobotX;
		destY = Locations.gantryRobotY;
		destAngle = 0;
		currState = State.TO_IDLE;
	}

	@Override
	public void DoRetrieveBin(GuiBin bin, GuiBoat boat) {
		//Set destination X and Y to belt X and Y
		destX = Locations.binOnArrivalX - Images.Robot.getIconWidth();
		destY = bin.getCurrentY() - 40;
		destAngle = 0;
		boatReference = boat;
		binToLoad = bin;
		binToLoad.update = false;
		currState = State.TO_BOAT_PICKUP;
	}

	@Override
	public void DoLoadBin(GuiFeeder feeder) {
		//Pick up empty kit and set destination X and Y to kit X and Y
		destX = feeder.getCurrentX() + Images.Feeder.getIconWidth();
		destY = feeder.getCurrentY() - 20;
		destAngle = 180;
		currState = State.TO_FEEDER_DROPOFF;
	}
	
	@Override
	public void DoReturnEmptyBin(GuiBin bin, GuiFeeder feeder){
		destX = feeder.getCurrentX() + Images.Feeder.getIconWidth();
		destY = bin.getCurrentY() - 40;
		feederReference = feeder;
		binToLoad = bin;
		destAngle = 180;
		currState = State.TO_FEEDER_PICKUP;
	}
}
