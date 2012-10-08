package gui;

import structures.KitPosition;
import engine.Agent;
import engine.agents.KitRobotAgent;
import gui.holdable.GuiKit;
import gui.interfaces.Gui;
import gui.interfaces.GuiKitRobotInterface;
import gui.locationsimg.Images;
import gui.locationsimg.Locations;
import gui.superclasses.GuiRobot;

//@author Sumukh Anand

public class GuiKitRobot extends GuiRobot implements Gui, GuiKitRobotInterface {

	public enum State {
		FREE,
		TO_IDLE,
		AT_IDLE,
		TO_KIT_STAND_PICKUP,
		TO_KIT_STAND_DROPOFF,
		AT_KIT_STAND,
		TO_KIT_BELT, 
		AT_KIT_BELT,
		TO_KIT_CAMERA_PICKUP,
		TO_KIT_CAMERA_DROPOFF,
		AT_KIT_CAMERA,
		TO_KIT_BOAT,
		AT_KIT_BOAT
	}

	private boolean badKit;
	private State currState;
	private GuiKit kitToCheck;
	private GuiKit kitToMove;
	private KitPosition kitPos;
	private KitRobotAgent kitRobot;

	public GuiKitRobot(int x, int y){
		super(x, y);
		currState = State.FREE;
	}

	public GuiKitRobot(){
		super();
		currX = Locations.kitRobotX;
		currY = Locations.kitRobotY;
		destX = Locations.kitRobotX;
		destY = Locations.kitRobotY;
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

		case TO_KIT_STAND_PICKUP:
			if (currX==destX && currY==destY && currAngle==destAngle){
				armContents[0] = kitToMove;
				numContents++;
				destX = Locations.checkingStationX - Images.Robot.getIconWidth();
				destY = Locations.checkingStationY - 40;
				destAngle = 0;
				currState = State.TO_KIT_CAMERA_DROPOFF;
			}
			break;

		case TO_KIT_STAND_DROPOFF:
			if (currX==destX && currY==destY && currAngle==destAngle){
				if (kitPos == KitPosition.POSITION1){
					armContents[0].resetImage();
					armContents[0].setLocation(Locations.kitAX, Locations.kitAY);
				}
				else{
					armContents[0].resetImage();
					armContents[0].setLocation(Locations.kitBX, Locations.kitBY);
				}
				armContents[0] = null;
				numContents--;
				currState = State.AT_KIT_STAND;
			}
			break;

		case AT_KIT_STAND:
			if (badKit){
				badKit = false;
			}
			kitRobot.msgAnimationDone();
			DoGoToIdle();
			break;

		case TO_KIT_BELT:
			if (currX==destX && currY==destY && currAngle==destAngle){
				armContents[0] = kitToMove;
				numContents++;
				if (kitPos == KitPosition.POSITION1){
					destX = Locations.kitAX - Images.Robot.getIconWidth();
					destY = Locations.kitAY - 40;
				}
				else{
					destX = Locations.kitBX - Images.Robot.getIconWidth();
					destY = Locations.kitBY - 40;
				}
				destAngle = 0;
				GuiBoxBelt.takenAway = true;
				currState = State.TO_KIT_STAND_DROPOFF;
			}
			break;

		case AT_KIT_BELT:
			kitRobot.msgAnimationDone();
			DoGoToIdle();
			break;

		case TO_KIT_CAMERA_PICKUP:
			if (currX==destX && currY==destY && currAngle==destAngle){
				GuiCheckingStation.drawKit = false;
				armContents[0] = kitToMove;
				numContents++;

				if (!badKit){
					destX = Locations.binOnDepartureX + Images.Bin.getIconWidth();
					destY = Locations.binOnDepartureY;
					destAngle = 180;
					currState = State.TO_KIT_BOAT;
				}
				else {
					if (kitPos == KitPosition.POSITION1){
						destX = Locations.kitAX - Images.Robot.getIconWidth();
						destY = Locations.kitAY;
					}
					else{
						destX = Locations.kitBX - Images.Robot.getIconWidth();
						destY = Locations.kitBY;
					}
					destAngle = 180;
					currState = State.TO_KIT_STAND_DROPOFF;
				}
			}
			break;

		case TO_KIT_CAMERA_DROPOFF:
			if (currX==destX && currY==destY && currAngle==destAngle){
				GuiCheckingStation.drawKit = true;
				armContents[0].resetImage();
				armContents[0].setLocation(Locations.checkingStationX, Locations.checkingStationY);
				armContents[0] = null;
				numContents--;
				currState = State.AT_KIT_CAMERA;
			}
			break;

		case AT_KIT_CAMERA:
			kitRobot.msgAnimationDone();
			DoGoToIdle();
			break;

		case TO_KIT_BOAT:
			if (currX==destX && currY==destY && currAngle==destAngle){
				armContents[0].resetImage();
				armContents[0].setLocation(Locations.binOnDepartureX, Locations.binOnDepartureY);
				armContents[0] = null;
				numContents--;
				currState = State.AT_KIT_BOAT;
			}
			break;

		case AT_KIT_BOAT:
			kitRobot.msgAnimationDone();
			DoGoToIdle();
			break;
		}

		super.updateLocation();
	}

	@Override
	public void setAgent(KitRobotAgent agent) {
		//Set agent
		kitRobot = agent;
	}

	@Override
	public void DoGoToIdle() {
		//Set destination X and Y to idle X and Y
		destX = Locations.kitRobotX;
		destY = Locations.kitRobotY;
		destAngle = 0;
		currState = State.TO_IDLE;
		
		emptyHands();
	}

	@Override
	public void DoMoveKitToStand(GuiKit kit, KitPosition pos) {
		//Pick up empty kit and set destination X and Y to kit X and Y
		destX = Locations.boxBeltX - Images.Kit.getIconWidth()/2;
		destY = Locations.boxBeltY - Images.Robot.getIconHeight();
		kitPos = pos;
		destAngle = 90;
		kitToMove = kit;
		currState = State.TO_KIT_BELT;
		
		emptyHands();
	}

	@Override
	public void DoMoveKitToCameraInspection(GuiKit kit, KitPosition pos) {
		//Pick up full kit and set destination X and Y to camera X and Y
		if (pos == KitPosition.POSITION1){
			destX = Locations.kitAX - Images.Robot.getIconWidth();
			destY = Locations.kitAY - 40;
		}
		else {
			destX = Locations.kitBX - Images.Robot.getIconWidth();
			destY = Locations.kitBY - 40;
		}
		destAngle = 0;
		kitToMove = kit;
		currState = State.TO_KIT_STAND_PICKUP;
		
		emptyHands();
	}

	@Override
	public void DoMoveFinishedKitToDeliveryStation(GuiKit kit) {
		//Pick up checked kit and set destination X and Y to boat X and Y
		destX = Locations.checkingStationX - Images.Robot.getIconWidth();
		destY = Locations.checkingStationY - 40;
		destAngle = 0;
		kitToMove = kit;
		currState = State.TO_KIT_CAMERA_PICKUP;
		
		emptyHands();
	}

	@Override
	public void DoMoveKitBackToStand(GuiKit kit, KitPosition pos) {
		destX = Locations.checkingStationX - Images.Robot.getIconWidth();
		destY = Locations.checkingStationY - 40;
		destAngle = 0;
		kitToMove = kit;
		kitPos = pos;
		badKit = true;
		currState = State.TO_KIT_CAMERA_PICKUP;
		
		emptyHands();
	}
}
