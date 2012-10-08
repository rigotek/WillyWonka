package structures;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is how a configuration is defined
 * 
 * @author Pierre Tasci
 *
 */
public class Configuration {
	private final HashMap<PartType,Integer> config;
	public ArrayList<PartType> types;
	
	public Configuration() {
		config = new HashMap<PartType,Integer>();
	}
	
	public Configuration(HashMap<PartType,Integer> input) {
		//config = new HashMap<PartType,Integer>();
		types = new ArrayList<PartType>();
		config = input;
		for(PartType p : PartType.values())	{
			if(input.containsKey(p)) {
				types.add(p);
			}
		}
	}
	
	public Configuration(HashMap<PartType,Integer> input, ArrayList<PartType> order) {
		config = input;
		types = order;
	}
	
	public HashMap<PartType,Integer> configuration() {
		return config;
	}
	
	public int getType(PartType type) {
		//System.out.println("Config is: " + config + " and the type is: " + type);
		return config.get(type);
	}
	
	/**
	 * Tells how many of that type of part you need more
	 * 
	 * @param type
	 * @param added
	 * @return
	 */
	public int remaining(PartType type, int added) {
		return config.get(type) - added;
	}
}
