package gui.ux;

import java.util.ArrayList;
import java.util.HashMap;

import structures.PartType;

/**
 * Effectively a struct in Java that contains all data that is produced by the UI for use
 * internally within the Factory simulation. Only classes of type GuiInterface may interact
 * directly with the variables, and all other classes must call one of the functions.
 * 
 * @author David Tan
 */
public class UserData {

	// --------------- DATA ---------------
	
	/** HashMap that stores the data for the configuration of a kit. */
	private static HashMap <PartType, Integer> kitConfiguration;
	/** HashMap that stores the data for part-string pairs. */
	private static HashMap <PartType, String> referenceMap;
	/** ArrayList that stores the arrangement for the configuration of a kit. */
	private static ArrayList <PartType> kitArrangement;
	/** Integer that represents the current vibration speed for all belts. */
	private static int vibrationSpeed;
	
	/** Holds all possible identification keys for various panels. */
	private static enum Identification {
		/** A generic key. */
		WQOHKOWBYWCW,
		/** A generic key. */
		QCXQFQFTEHNI,
		/** A generic key. */
		TFDZOFAUYCCV,
		/** A generic key. */
		MMSEWUYTWFII,
		/** A generic key. */
		DFCLXGFTERQTR,
		/** A generic key. */
		QMSVGYPYCLIB,
		/** A generic key. */
		KEUAUYEOCH
	}
	
	// There is no constructor due to all data being static.
	// --------------- ACTIONS ---------------
	
				// ------ KIT HASHMAP ------
	
	/**
	 * Sets the configuration of the kit.
	 * 
	 * @param parent Reference to the GuiInterface that calls this method
	 * @param param New kit configuration
	 */
	public static void setHashMap(GuiInterface parent, HashMap <PartType, Integer> param) {
		if (checkID(parent)) {
			kitConfiguration = param;
		}
	}
	
	/** Returns the configuration of the kit. */
	public static HashMap <PartType, Integer> returnMap() {
		return kitConfiguration;
	}
	
				// ------ REFERENCE HASHMAP ------
	
	/**
	 * Sets the reference map that associates all part types and strings.
	 * 
	 * @param parent Reference to the GuiInterface that calls this method
	 * @param param New kit configuration
	 */
	public static void setReferenceMap(GuiInterface parent, HashMap <PartType, String> param) {
		if (checkID(parent)) {
			referenceMap = param;
		}
	}
	
	/** Returns the reference hashmap. */
	public static HashMap <PartType, String> returnRef() {
		return referenceMap;
	}
	
				// ------ KIT ARRANGEMENT ------
	
	public static void setArrangement(GuiInterface parent, ArrayList <PartType> param) {
		if (checkID(parent)) {
			kitArrangement = param;
		}
	}
	
	public static ArrayList <PartType> returnArrangement() {
		return kitArrangement;
	}
	
				// ------ VIBRATION SPEED ------
	
	/**
	 * Sets the vibration speed.
	 * 
	 * @param parent Reference to the GuiInterface that calls this method
	 * @param param New vibration speed
	 */
	public static void setVibrationSpeed(GuiInterface parent, int param) {
		if (checkID(parent)) {
			vibrationSpeed = param;
		}
	}
	
	/** Returns the global vibration speed. */
	public static int getVibrationSpeed() {
		return vibrationSpeed;
	}
	
				// ------ SECURITY CHECK ------
	
	/**
	 * Matches the ID of a GuiInterface class with an ID stored in a database to ensure that
	 * only GuiInterface classes may alter data in this class.
	 * 
	 * @param parent Reference to the GuiInterface that called a UserData setter
	 */
	private static boolean checkID(GuiInterface parent) {
		Identification ident = Identification.valueOf(parent.returnPanelID());
		switch ( ident ) {
		case WQOHKOWBYWCW:	// consolepanel
		case QCXQFQFTEHNI:	// controlpanel
		case TFDZOFAUYCCV:	// factorypanel
		case MMSEWUYTWFII:	// kitselection
		case DFCLXGFTERQTR:	// maininterface
		case QMSVGYPYCLIB:	// nonnormpanel
		case KEUAUYEOCH:	// powerpanel
			return true;
		default: break;
		}
		return false;
	}
}
