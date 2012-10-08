package gui.interfaces;

import engine.agents.FeederAgent;
import gui.holdable.GuiCandy;

/**
 * Interface of all unique methods used by the GuiDiverter class.
 * 
 * @author David Tan
 */
public interface GuiDiverterInterface {
	
	/**
	 * Sets state to change the current direction of the GuiDiverter
	 * only if the current direction is different from the input
	 * direction; reads either 'u' or 'd' as specified by API.
	 * 
	 * @param direction The direction needs to change
	 */
	public void doMoveDiverterToPosition(char direction);
	
	/**
	 * The direction is assumed to be correct and objects from
	 * the feeder unit are transferred through the GuiDiverter unit
	 * onto the conveyor belts to the rest of the kitting process.
	 * 
	 * @param param The item that is to be loaded
	 */
	public void doUnloadPartIntoLane(GuiCandy param);
	
	/**
	 * Sets then parent feeder agent for the GuiDiverter unit.
	 * 
	 * @param agent Parent FeederAgent that will be assigned to the GuiDiverter
	 */
	public void setAgent(FeederAgent agent);
}
