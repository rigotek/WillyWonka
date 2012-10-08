package engine.interfaces;

import gui.holdable.GuiKit;
import structures.AgentKit;
//Alberto Marroquin
import structures.Configuration;

public interface  KitRobot {
	/**
	 * This message will be called every time the factory needs to build a kit. The Configuration parameter will
	 * indicate the type of the kit.
	 * @param c - The configuration of the kit.
	 */
	public abstract void msgHereIsKitConfiguration( Configuration c);

	/**
	 * The method will notify that the specify kit is done and ready to proceed to inspection.
	 * @param kit - The finished kit with parts.
	 */
	public abstract void msgKitIsDone(AgentKit kit);

	/**
	 * When the kit passes inspection, this method will notify the kit that it has successfully passed inspection
	 * and ready to go.
	 * @param kit - A good kit.
	 */
	public abstract void msgKitIsGood(AgentKit kit);
	
	/**
	 * When the kit fails inspection, this method will notify the kit that it has failed inspection
	 * and ready to go.
	 * @param kit - A good kit.
	 */
	public abstract void msgKitIsBad(AgentKit kit);
	
	/**
	 * Message from the animation. It Notifies that the animation has finished with a
	 * certain task requested.
	 */
	public abstract void msgAnimationDone();
	public abstract void msgDoneCreatingKit(AgentKit kit);
	public abstract void msgDonePlacingKit(GuiKit kit);

}
