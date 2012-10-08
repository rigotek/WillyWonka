/**
 * @author Tyler Gregg
 * The GuiFeederInterface serves as the interface
 * class for the GuiFeeder, and ensures that the
 * GuiFeeder implements the following methods.
 */
package gui.interfaces;
import gui.holdable.GuiBin;
public interface GuiFeederInterface {
	public abstract void stateChanged();
	public void doMoveBinDownFeederLane(GuiBin newbin);
	public void doPurgeBin();
	public boolean beltHasBin();
}