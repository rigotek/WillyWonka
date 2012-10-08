package gui;

import engine.agents.FeederAgent;
import gui.holdable.GuiCandy;
import gui.interfaces.Gui;
import gui.interfaces.GuiDiverterInterface;
import gui.locationsimg.Images;
import gui.ux.UserData;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * Graphical representation of the Diverter part of the Feeder agent, only interprets method
 * calls from the parent agent and alters the graphics to depict such. Contains no logic or
 * any ability to check data.
 * 
 * @author David Tan
 */
public class GuiDiverter implements Gui, GuiDiverterInterface {

	// --------------- DATA ---------------
	
				// ------ VARIABLES ------
	
	/** Represents completion status of the GuiCandy, used in partCallback(). */
	private boolean flag;
	/** Represents whether the GuiDiverter has been previously damaged or not. */
	private boolean damaged;
	
	/** Represents current x-coordinate of the GuiDiverter. */
	private int currX;
	/** Represents current y-coordinate of the GuiDiverter. */
	private int currY;
	/** Represents current height of the GuiDiverter. */
	private int currHeight;
	/** Represents current width of the GuiDiverter. */
	private int currWidth;
	/** Represents current y-coordinate of the top exit of the GuiDiverter. */
	private int topY;
	/** Represents current y-coordinate of the centre of the GuiDiverter. */
	private int centreY;
	/** Represents current y-coordinate of the bottom exit of the GuiDiverter. */
	private int bottomY;
	/** Represents current increment of the lines on the GuiDiverter belt. */
	private int beltCount;
	/** Represents the current vibration speed of the GuiDiverter as specified by the UserData class. */
	private int vibrationSpeed;
	
    			// ------ ENUMS ------
	
	/** Holds all possible states of the GuiDiverter. */
	private enum State {
		/** Signifies the GuiDiverter is currently idle. */
		IDLE,
		/** Signifies the GuiDiverter is moving an object. */
		NOT_IDLE,
		/** Signifies non-normative condition where the GuiDiverter does not function properly. */
		BROKEN
	}
	/** Holds all possible states of the GuiCandy. */
	private enum Candy {
		/** Signifies the GuiCandy is not present. */
		NULL,
		/** Signifies the GuiCandy is moving to the centre. */
		CENTRE,
		/** Signifies the GuiCandy is moving vertically towards an exit. */
		VERTICAL,
		/** Signifies the GuiCandy is moving horizontally towards an exit. */
		HORIZONTAL,
		/** Signifies the GuiCandy has finished moving. */
		DONE
	}
	/** Holds all possible directions of the GuiDiverter. */
	private enum Direction {
		/** Signifies the GuiDiverter is oriented upwards. */
		UP,
		/** Signifies the GuiDiverter is oriented downwards. */
		DOWN
	}
	
				// ------ MISCELLANEOUS ------

	/** Enum that represents the current power status. */
	private Power		currentPower;
	/** Enum that represents current state of the GuiDiverter. */
	private State		currentState;
	/** Enum that represents current direction of the GuiDiverter. */
	private Direction	currentDirection;
	/** Enum that represents current state of the GuiCandy. */
	private Candy		candyState;
	/** FeederAgent reference that represents the parent agent that controls the GuiDiverter. */
	private FeederAgent	agent;
	/** GuiCandy reference that represents current GuiCandy being moved by the GuiDiverter. */
	private GuiCandy	candy;
	
	// --------------- CONSTRUCTOR ---------------
	
	/**
	 * Public constructor for the GuiDiverter. Note that currX and currY
	 * refer to the top-left corner of the diverter.
	 * 
	 * @param x Sets the x-coordinate of the GuiDiverter
	 * @param y Sets the y-coordinate of the GuiDiverter
	 */
	public GuiDiverter(int x, int y)
	{
		flag = false; // default state is false, there are no GuiCandy objects
		damaged = false; // default state is false, GuiDiverter was just created
		
		currX = x;
		currY = y;
		currHeight = Images.Diverter.getImage().getHeight(null);
		currWidth = Images.Diverter.getImage().getWidth(null);
		topY = (currY + Images.Lane.getImage().getHeight(null) / 2) - Images.CandyA.getImage().getHeight(null) / 2;
		centreY = (currY + currHeight / 2) - Images.CandyA.getImage().getHeight(null) / 2;
		bottomY = (currY + currHeight - Images.Lane.getImage().getHeight(null) / 2) - Images.CandyA.getImage().getHeight(null) / 2;
		
		beltCount = 0;
		vibrationSpeed = UserData.getVibrationSpeed();
		
		currentPower = Gui.Power.ON; // default state is on
		currentDirection = Direction.UP; // default state is up
		currentState = State.IDLE; // default state is idle
		candyState = Candy.NULL; // default state is null
		candy = null; // default candy reference is null; no candy
	}
	
	// --------------- GRAPHICS ---------------
	
	/**
	 * Draws the GuiDiverter depending on whether there is a GuiCandy object
	 * being moved or not; animated belts draw depending on current direction
	 * of the two arms. The GuiDiverter belts are always moving regardless of
	 * presence of a GuiCandy.
	 * 
	 * @param g Graphics component
	 */
	@Override
	public void draw(Graphics g) {
		
		// Draw the base image.
		g.drawImage(Images.Diverter.getImage(), currX, currY, null);
		// Set the tool to grey for drawing lines.
		g.setColor(Color.GRAY);
		
		// Draw lines on the GuiDiverter to simulate movement.
	    for (int i = 0; i < 10; i++){
	    	g.drawLine(currX + 5, currY + beltCount + (12*i), currX + currWidth - 6, currY + beltCount + (12*i));
	    }
	    
	    // Draw the candy if the GuiDiverter is currently active and the candy exists.
		if (currentState == State.NOT_IDLE && candy != null) {
			candy.draw(g);
		}
		
		// Draw all images for the GuiDiverter scrolling upwards.
		if (currentDirection == Direction.UP) {
			if (currentPower == Gui.Power.ON) {
				if (beltCount == 0) {
					beltCount = 11;
				} else {
					beltCount--;
				}
			}
			
			if (currentPower == Gui.Power.ON) {
				g.drawImage(Images.DiverterGoUp.getImage(), currX, currY, null);
			} else {
				g.drawImage(Images.DiverterGoUpIdle.getImage(), currX, currY, null);
			}
			g.drawImage(Images.DiverterGoDownIdle.getImage(), currX, currY + currHeight - 40, null);
		}
		// Draw all images for the GuiDiverter scrolling downwards.
		else if (currentDirection == Direction.DOWN) {
			if (currentPower == Gui.Power.ON) {
				if (beltCount == 11) {
					beltCount = 0;
				} else {
					beltCount++;
				}
			}
			
			g.drawImage(Images.DiverterGoUpIdle.getImage(), currX, currY, null);
			if (currentPower == Gui.Power.ON) {
				g.drawImage(Images.DiverterGoDown.getImage(), currX, currY + currHeight - 40, null);
			} else {
				g.drawImage(Images.DiverterGoDownIdle.getImage(), currX, currY + currHeight - 40, null);
			}
		}
		
		// Draw the hood over the GuiDiverter.
		g.drawImage(Images.DiverterCap.getImage(), currX + 30, currY + 10, null);
	}
	
	// --------------- ACTIONS ---------------
	
	/**
	 * Called by the GuiCandy object after it reaches the destination to which the GuiDiverter
	 * instructed it to go. Sets a flag in GuiDiverter to store this information.
	 * 
	 * @param part Part that called partCallback()
	 */
	@Override
	public void partCallback(Gui part) {
		flag = true;
	}
	
	/**
	 * Called by draw() to update the location of the candy if the candy is present.
	 * Otherwise, updateLocation() does nothing.
	 */
	@Override
	public void updateLocation() {
		if (currentPower == Gui.Power.OFF) {
			return;
		}
		if (currentState == State.NOT_IDLE) {
			vibrationSpeed = UserData.getVibrationSpeed();
			vibrateDiverter();
			candy.updateLocation();
			// System.out.println("Moving: "
			//		+ candy.getCurrentX() + ", " + candy.getCurrentY()
			//		+ " : "
			//		+ candy.getDestinationX() + ", " + candy.getDestinationY());
			if (candyState == Candy.CENTRE && flag) {
				// System.out.println("GuiDiverter: Candy moved to centre.");
				candyState = Candy.VERTICAL;
				flag = false;
				stateChanged();
			}
			else if (candyState == Candy.VERTICAL && flag) {
				// System.out.println("GuiDiverter: Candy moved to end of belt.");
				candyState = Candy.HORIZONTAL;
				flag = false;
				stateChanged();
			}
			else if (candyState == Candy.HORIZONTAL && flag) {
				// System.out.println("GuiDiverter: Candy has left diverter.");
				candyState = Candy.DONE;
				flag = false;
				stateChanged();
			}
			else if (candyState == Candy.DONE) {
				// System.out.println("GuiDiverter: Candy has been removed from diverter.");
				candyState = Candy.NULL;
				flag = false;
				stateChanged();
				agent.doneUnloadPartIntoLane();
			}
		}
	}
	
	/**
	 * Called by updateLocation() whenever the state changes so that the GuiDiverter may update
	 * the destination of the GuiCandy passed to the GuiDiverter in doUnloadPartIntoLane().
	 */
	public void stateChanged() {
		switch ( candyState ) {
		// Sets the current state of the GuiDiverter to idle and removes any references to
		// the candy.
		case NULL:
			currentState = State.IDLE;
			candy = null;
			break;
		// Sets destination of the candy to the middle of the GuiDiverter.
		case CENTRE:
			candy.setDestination(currX + currWidth / 2 - 10, centreY);
			break;
		// Sets destination of the candy to either top or bottom of the GuiDiverter.
		case VERTICAL:
			// Determines which end of the GuiDiverter to send candy.
			switch ( currentDirection ) {
			case UP:
				candy.setDestination(currX + currWidth / 2 - 10, topY);
				break;
			case DOWN:
				candy.setDestination(currX + currWidth / 2 - 10, bottomY);
				break;
			}
			break;
		// Sets destination of candy to either top or bottom exit of the GuiDiverter.
		case HORIZONTAL:
			// Determines which end of the GuiDiverter to send candy.
			switch ( currentDirection ) {
			case UP:
				candy.setDestination(currX, topY);
				break;
			case DOWN:
				candy.setDestination(currX, bottomY);
				break;
			}
			break;
		// Candy reached end of GuiDiverter, remove candy from GuiDiverter.
		case DONE:
			// System.out.println("GuiDiverter: Done moving part.");
			break;
		default:
			break;
		}
	}
	
	/**
	 * Vibrates the current candy being unloaded depending upon which location on the GuiDiverter
	 * it is located. If it is currently travelling horizontally, it can vibrate in one of three ways
	 * vertically; if it is currently travelling vertically, it can vibrate horizontally and travel
	 * depending on its prior destination.
	 */
	public void vibrateDiverter() {
		if (UserData.getVibrationSpeed() == 0) {
			return;
		}
		switch ( candyState ) {
		// Vibrates the candy to the centre of the GuiDiverter.
		case CENTRE:
			if (candy.getCurrentY() > centreY) {
				// If candy is above centre, move it back to the centre.
				candy.setLocation(candy.getCurrentX(), centreY - vibrationSpeed);
			}
			else if (candy.getCurrentY() < centreY) {
				// If candy is below centre, move it back to the centre.
				candy.setLocation(candy.getCurrentX(), centreY + vibrationSpeed);
			} else {
				// Randomly pick a direction in which to vibrate.
				Random r = new Random();
				Boolean rand = r.nextBoolean();
				if (rand) {
					candy.setLocation(candy.getCurrentX(), centreY + vibrationSpeed);
				} else {
					candy.setLocation(candy.getCurrentX(), centreY - vibrationSpeed);
				}
			}
			// Reset the destination to counteract the effects of setLocation().
			candy.setDestination(currX + currWidth / 2 - 10, candy.getCurrentY());
			break;
		case HORIZONTAL:
			// Vibrates the candy to the currently desired exit.
			switch ( currentDirection ) {
			// Vibrates the candy to the top exit.
			case UP:
				if (candy.getCurrentY() > topY) {
					// If candy is above centre, move it back to the centre.
					candy.setLocation(candy.getCurrentX(), topY - vibrationSpeed);
				}
				else if (candy.getCurrentY() < topY) {
					// If candy is below centre, move it back to the centre.
					candy.setLocation(candy.getCurrentX(), topY + vibrationSpeed);
				} else {
					// Randomly pick a direction in which to vibrate.
					Random r = new Random();
					Boolean rand = r.nextBoolean();
					if (rand) {
						candy.setLocation(candy.getCurrentX(), topY + vibrationSpeed);
					} else {
						candy.setLocation(candy.getCurrentX(), topY - vibrationSpeed);
					}
				}
				// Reset the destination to counteract the effects of setLocation().
				candy.setDestination(currX, candy.getCurrentY());
				break;
			case DOWN:
				// Vibrates the candy to the bottom exit.
				if (candy.getCurrentY() > bottomY) {
					// If candy is above centre, move it back to the centre.
					candy.setLocation(candy.getCurrentX(), bottomY - vibrationSpeed);
				}
				else if (candy.getCurrentY() < bottomY) {
					// If candy is below centre, move it back to the centre.
					candy.setLocation(candy.getCurrentX(), bottomY + vibrationSpeed);
				} else {
					// Randomly pick a direction in which to vibrate.
					Random r = new Random();
					Boolean rand = r.nextBoolean();
					if (rand) {
						candy.setLocation(candy.getCurrentX(), bottomY + vibrationSpeed);
					} else {
						candy.setLocation(candy.getCurrentX(), bottomY - vibrationSpeed);
					}
				}
				// Reset the destination to counteract the effects of setLocation().
				candy.setDestination(currX, candy.getCurrentY());
				break;
			default:
				break;
			}
			break;
		case VERTICAL:
			// Vibrates the candy as it moves up or down the GuiDiverter.
			if (candy.getCurrentX() > currX + currWidth / 2 - 10) {
				// If candy is right of centre, move it back to the centre.
				candy.setLocation(currX + currWidth / 2 - 10 - vibrationSpeed, candy.getCurrentY());
			}
			else if (candy.getCurrentX() < currX + currWidth / 2 - 10) {
				// If candy is left of centre, move it back to the centre.
				candy.setLocation(currX + currWidth / 2 - 10 + vibrationSpeed, candy.getCurrentY());
			} else {
				// Randomly pick a direction in which to vibrate.
				Random r = new Random();
				Boolean rand = r.nextBoolean();
				if (rand) {
					candy.setLocation(currX + currWidth / 2 - 10 + vibrationSpeed, candy.getCurrentY());
				} else {
					candy.setLocation(currX + currWidth / 2 - 10 - vibrationSpeed, candy.getCurrentY());
				}
			}
			// Reset the destination to counteract the effects of setLocation().
			switch ( currentDirection ) {
			case UP:
				candy.setDestination(candy.getCurrentX(), topY);
				break;
			case DOWN:
				candy.setDestination(candy.getCurrentX(), bottomY);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Called by the parent FeederAgent to switch direction to either up or
	 * down. The method checks to see if the GuiDiverter needs to switch direction,
	 * and if necessary, changes the current direction to the new direction. It
	 * assumes that the call is always correct and does not check for errors.
	 * 
	 * @param direction States the desired direction for the GuiDiverter
	 */
	@Override
	public void doMoveDiverterToPosition(char direction) {
		if (currentState == State.IDLE) {
			switch ( direction ) {
			// Switch direction upwards.
			case 'u':
				currentDirection = Direction.UP;
				break;
			// Switch direction downwards.
			case 'd':
				currentDirection = Direction.DOWN;
				break;
			// Default direction is upwards.
			default:
				currentDirection = Direction.UP;
				break;
			}
			// System.out.println("GuiDiverter: Diverter is now switching direction: " + currentDirection);
			agent.doneMoveDiverterToPosition();
		}
		// If the GuiDiverter is not currently idle, i.e. it is moving something, then
		// the attempt to make the GuiDiverter switch directions should return a failure.
	}

	/**
	 * Called by the parent FeederAgent to transfer an object of type GuiCandy
	 * down the current lane. The direction is assumed to be correct, and the
	 * GuiCandy object passed into the method is then given the order to move down
	 * the GuiDiverter to one of the two exits depending on which direction the
	 * GuiDiverter is currently facing.
	 * 
	 * @param param FeederAgent parent passes this GuiCandy parameter to move
	 */
	@Override
	public void doUnloadPartIntoLane(GuiCandy param) {
		// Make sure the GuiDiverter is currently idle, the process will fail if the
		// GuiDiverter is currently moving a candy.
		if (currentState == State.IDLE) {
			candy = param;
			candy.setLinkedGui(this);
			currentState = State.NOT_IDLE;
			candyState = Candy.CENTRE;
			stateChanged();
			return;
		}
	}
	
	// --------------- NON-NORMATIVES ---------------
	
	@Override
	public void enable() {
		currentPower = Gui.Power.ON;
	}
	
	@Override
	public void disable() {
		currentPower = Gui.Power.OFF;
	}
	
	@Override
	public Power checkPower() {
		return currentPower;
	}
	
	@Override
	public void destroy() {
		damaged = true;
		disable();
	}
	
	@Override
	public void repair() {
		enable();
	}
	
	@Override
	public boolean checkRepair() {
		return damaged;
	}
	
	// --------------- MISCELLANEOUS ---------------
	
	/**
	 * Setter for the parent agent.
	 * 
	 * @param agent Sets current parent FeederAgent
	 */
	@Override
	public void setAgent(FeederAgent agent) {
		this.agent = agent;
	}
	
	/** Getter for current x-coordinate of the GuiDiverter. */
	@Override
	public int getCurrentX() {
		return currX;
	}
	
	/** Getter for current y-coordinate of the GuiDiverter. */
	@Override
	public int getCurrentY() {
		return currY;
	}
	
	/**
	 * Getter for current x-coordinate of the destination of the GuiDiverter.
	 * Does nothing by nature of the immobile GuiDiverter and it will return currX.
	 */
	@Override
	public int getDestinationX() {
		return currX;
	}
	
	/**
	 * Getter for current y-coordinate of the destination of the GuiDiverter.
	 * Does nothing by nature of the immobile GuiDiverter and it will return currY.
	 */
	@Override
	public int getDestinationY() {
		return currY;
	}
	
	/**
	 * Setter for the destination coordinates of the GuiDiverter.
	 * Not used by nature of the immobile GuiDiverter.
	 * 
	 * @param x sets x-coordinate of destination
	 * @param y sets y-coordinate of destination
	 */
	@Override
	public void setDestination(int x, int y) {
		currX = x;
		currY = y;
	}
}
