package gui.interfaces;

import java.awt.Graphics;

/**
 * Contains methods that must be implemented by all GUI agents.
 * 
 * @author Sumukh Anand
 */
public interface Gui {
	
	/** Holds current enable status of a GUI agent. */
	public enum Power {
		/** Signifies the GUI agent is currently active. */
		ON,
		/** Signifies the GUI agent is currently inactive*/
		OFF
	}
	
	/**
	 * Draws a GUI agent based on its current state, its function, purpose, et cetera.
	 * 
	 * @param g Graphics component
	 */
	void draw(Graphics g);

	/**
	 * Can be used by GUI agents to manipulate the current state, check for conditions to
	 * move to another state, or any other functions that a GUI agent must perform.
	 */
	public void updateLocation();

	/**
	 * Called by Holdable units to inform the parent GUI agent class that an action or process
	 * was recently completed; serves as a notification to a GUI agent.
	 * 
	 * @param part Part that called partCallback()
	 */
	public void partCallback(Gui part);
	
	/** Called by the FactoryPanel to enable GUI agents. */
	public void enable();
	
	/** Called by the FactoryPanel to disable GUI agents. */
	public void disable();
	
	/** Called by the FactoryPanel to check current power status of a GUI agent. */
	public Power checkPower();
	
	/**
	 * Called by the FactoryPanel to destroy GUI agents. Should display a unique animation
	 * particular to the GUI agent in question and set an internal flag to signify that the
	 * GUI agent has been damaged.
	 */
	public void destroy();
	
	/**
	 * Called by the FactoryPanel to repair GUI agents. GUI agents that have previously been
	 * destroyed cannot fully be repaired, and should have a higher chance of producing errors.
	 */
	public void repair();
	
	/** Called by the FactoryPanel to check whether the GUI agent has suffered prior damage. */
	public boolean checkRepair();
	
	/** Getter for current x-coordinate of a GUI agent. */
	public int getCurrentX();
	
	/** Getter for current y-coordinate of a GUI agent. */
	public int getCurrentY();
	
	/** Getter for current x-coordinate of the destination of a GUI agent. */
	public int getDestinationX();
	
	/** Getter for current y-coordinate of the destination of a GUI agent. */
	public int getDestinationY();
	
	/**
	 * Setter for the destination coordinates of a GUI agent.
	 * 
	 * @param x sets x-coordinate of destination
	 * @param y sets y-coordinate of destination
	 */
	public void setDestination(int x, int y);
}
