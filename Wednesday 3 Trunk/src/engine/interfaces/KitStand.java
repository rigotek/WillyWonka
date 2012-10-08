package engine.interfaces;

import java.util.ArrayList;
import java.util.List;

import structures.AgentKit;
import structures.AgentPart;

public interface KitStand {

	public abstract void msgHereIsNextAgentKit(AgentKit ak);

	public abstract void msgGiveParts(ArrayList<AgentPart> p, AgentKit kit);
}