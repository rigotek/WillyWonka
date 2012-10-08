package gui.ux;

import gui.locationsimg.Images;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import structures.PartType;

/**
 * UI for configuration of the kit and its contents, allows the user to create a custom
 * configuration of parts in a kit for up to eight components. Any of the components may
 * be left blank or switched between the current candy types. Stores all reusable data in the
 * UserData file.
 * 
 * @author David Tan
 */
public class KitSelection extends JPanel implements ActionListener, GuiInterface {
	
	/** Refers to last update of the KitSelection class. */
	private static final long serialVersionUID = 20111126L;
	/** Refers to the internal ID of the KitSelection class. */
	private static final String KITSELECTIONID = "MMSEWUYTWFII";
	
	// --------------- DATA ---------------
	
	/** Represents the parent JFrame in which KitSelection resides. */
	MainInterface parentFrame;
	
	/** Represents current user selection. */
	private PartType currentSelection;
	
				// ------ KIT ------
	
	/** Represents the hashmap configuration for the kit. Local storage only. */
	private HashMap <PartType, Integer> hashMap;
	/** Represents the kit configuration for the kit. */
	private PartType[] kitConfig;
	/** Represents each individual slot of the kit. */
	private JButton[] kitButtons;
	/** Represents the configuration panel for the kit. */
	private JPanel kit;

				// ------ CONSOLE ------

	/** Represents the button to confirm the current configuration for the kit. */
	private JButton confirmKit;
	/** Represents the button that will send a user-specified number of kits. */
	private TextField textField;
	/** Represents a map containing string-part pairs of all options that can be selected by the user. */
	private Map<String, PartType> candyMap;
	/** Represents an array containing strings of all options that can be selected by the user. */
	private String[] candyNames;
	/** Represents all the options that can be selected by the user.  */
	private JComboBox candyOptions;
	/** Represents the current number of candies in the kit. */
	private int candyNumber;
	
				// ------ ENUMS ------
	
	/**
	 * Enum that represents all possible commands for KitSelection. Exists only to enable a
	 * switch-case statement in actionPerformed().
	 */
	private enum ActionCommand {
		/** Signifies that the current command pertains to kit slot 0. */
		KIT0,
		/** Signifies that the current command pertains to kit slot 1. */
		KIT1,
		/** Signifies that the current command pertains to kit slot 2. */
		KIT2,
		/** Signifies that the current command pertains to kit slot 3. */
		KIT3,
		/** Signifies that the current command pertains to kit slot 4. */
		KIT4,
		/** Signifies that the current command pertains to kit slot 5. */
		KIT5,
		/** Signifies that the current command pertains to kit slot 6. */
		KIT6,
		/** Signifies that the current command pertains to kit slot 7. */
		KIT7
	}
	
	// --------------- CONSTRUCTOR ---------------
	
	/**
	 * Creates a new KitSelection panel.
	 * 
	 * @param mainInterface Provides reference to parent JFrame
	 */
	public KitSelection(MainInterface mainInterface) {
		parentFrame = mainInterface;
		setPreferredSize(new Dimension(280, 235));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createTitledBorder("Kit Configuration"));
		
		// Initialisation of the internal panel that will provide the base upon which the user
		// may select kit configurations. Also set default candy selection to type 1.
		kit = new JPanel();
		kit.setPreferredSize(new Dimension(94, 160));
		JPanel buffer1 = new JPanel(); JPanel buffer2 = new JPanel();
		buffer1.setPreferredSize(new Dimension(93, 160)); buffer2.setPreferredSize(new Dimension(93, 160));
		kit.setName("Kit Configuration");
		
		// Initialisation of kit screen.
		kit.setLayout(new GridLayout(4, 2, 0, 0));
		kitButtons = new JButton[8];
		for (int i = 0; i < 8; i++) {
			kitButtons[i] = new JButton(Images.CandyA);
			kitButtons[i].setFocusPainted(false);
			kitButtons[i].setActionCommand("KIT" + i);
			kitButtons[i].addActionListener(new ButtonControl());
			kit.add(kitButtons[i]);
		}
		
		// Initialisation of the create kits button and text field.
		JPanel confirmRow = new JPanel();
		confirmRow.setLayout(new FlowLayout());
		confirmKit = new JButton("Create");
		confirmKit.addActionListener(this);
		textField = new TextField(4);
		confirmRow.add(confirmKit); confirmRow.add(textField);
		
		// Initialisation of the combo box with all the options available for selection.
		candyNames = new String[17];
		candyNames[0] = "Remove"; candyNames[1] = "Wonka Bar"; candyNames[2] = "Lollipop";
		candyNames[3] = "Everlasting Gobstopper"; candyNames[4] = "Nerds"; candyNames[5] = "Laffy Taffy";
		candyNames[6] = "SweeTARTS"; candyNames[7] = "Wonka Coin"; candyNames[8] = "ChocoBite";
		candyNames[9] = "Runts"; candyNames[10] = "Oompas"; candyNames[11] = "FunDip";
		candyNames[12] = "Dweebs"; candyNames[13] = "Chewy Spree"; candyNames[14] = "Pixy Stix";
		candyNames[15] = "Wonka Wish"; candyNames[16] = "Wonka Gum";
		candyOptions = new JComboBox(candyNames);
		candyOptions.setSelectedIndex(1);
		candyOptions.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
				"CURRENT CANDY SELECTION",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				new Font("Dialog", Font.PLAIN, 9)));
		candyOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        JComboBox cb = (JComboBox) e.getSource();
		        currentSelection = candyMap.get((String) cb.getSelectedItem());
			}
		});
		currentSelection = PartType.TYPE1;
		candyNumber = 8;
		
		// Initialisation of the hashmaps containing all string-part pairs.
		candyMap = new HashMap<String, PartType>();
		candyMap.put("Remove", PartType.NOTYPE);
		HashMap <PartType, String> candyRef = new HashMap<PartType, String>();
		for (int i = 1; i < 17; i++) {
			candyMap.put(candyNames[i], PartType.valueOf("TYPE" + i));
			candyRef.put(PartType.valueOf("TYPE" + i), candyNames[i]);
		}
		UserData.setReferenceMap(this, candyRef);
		
		// Initialisation of a new array of panels that contain all panels that need to be updated
		// and redrawn every time the clock fires.
		JPanel kitPanel = new JPanel();
		kitPanel.setLayout(new BorderLayout());
		kitPanel.add(buffer1, BorderLayout.WEST);
		kitPanel.add(kit, BorderLayout.CENTER);
		kitPanel.add(buffer2, BorderLayout.EAST);
		kitPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
				"CURRENT KIT CONFIGURATION",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				new Font("Dialog", Font.PLAIN, 9)));
		
		// Adding all panels to the KitSelection panel.
		add(kitPanel);
		add(confirmRow);
		add(candyOptions);
		
		// Creating a default configuration where the kit only consists of candy A.
		hashMap = new HashMap <PartType, Integer>();
		
		kitConfig = new PartType[8];
		for (int i = 0; i < 8; i++) {
			kitConfig[i] = PartType.TYPE1;
		}
		hashMap.put(PartType.TYPE1, 8);
			
		UserData.setHashMap(this, hashMap);
	}
	
	// --------------- ACTIONS ---------------
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String userInput = textField.getText();
		try {
			Integer numberOfKits = Integer.parseInt(userInput);
			UserData.setHashMap(this, hashMap);
			UserData.setArrangement(this, new ArrayList<PartType>(Arrays.asList(kitConfig)));
			// for (Map.Entry<PartType, Integer> entry : UserData.returnMap().entrySet()) {
			//	System.out.println("K: " + entry.getKey() +" V: " + entry.getValue());
			// }
			parentFrame.setConfiguration(numberOfKits);
		}
		catch (NumberFormatException nfe) {
			System.out.println("Invalid numerical input.");
		}
	}
	
	@Override
	public String returnPanelID() {
		return KITSELECTIONID;
	}
	
	// --------------- NESTED CLASS ---------------
	
	/**
	 * Manages all the buttons on the panel; if the user selects anything in the kit configuration panel,
	 * this class has its actionPerformed() called and determines which slot was selected based on which
	 * ActionCommand is produced by the button pressed. The hashmap is then updating based on what was
	 * selected, how many of the selected was already in the hashmap, et cetera.
	 * 
	 * @author David Tan
	 */
	private class ButtonControl implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ActionCommand command = ActionCommand.valueOf(e.getActionCommand());
			int flag = -1;
			// Picks which kit slot to alter.
			switch ( command ) {
			case KIT0: flag = 0; break;
			case KIT1: flag = 1; break;
			case KIT2: flag = 2; break;
			case KIT3: flag = 3; break;
			case KIT4: flag = 4; break;
			case KIT5: flag = 5; break;
			case KIT6: flag = 6; break;
			case KIT7: flag = 7; break;
			default: break;
			}
			// Matches current selection with a candy image.
			ImageIcon currentCandy;
			switch ( currentSelection ) {
			case NOTYPE: currentCandy = Images.NoPart; break;
			case TYPE1: currentCandy = Images.CandyA; break;
			case TYPE2: currentCandy = Images.CandyB; break;
			case TYPE3: currentCandy = Images.CandyC; break;
			case TYPE4: currentCandy = Images.CandyD; break;
			case TYPE5: currentCandy = Images.CandyE; break;
			case TYPE6: currentCandy = Images.CandyF; break;
			case TYPE7: currentCandy = Images.CandyG; break;
			case TYPE8: currentCandy = Images.CandyH; break;
			case TYPE9: currentCandy = Images.CandyI; break;
			case TYPE10: currentCandy = Images.CandyJ; break;
			case TYPE11: currentCandy = Images.CandyK; break;
			case TYPE12: currentCandy = Images.CandyL; break;
			case TYPE13: currentCandy = Images.CandyM; break;
			case TYPE14: currentCandy = Images.CandyN; break;
			case TYPE15: currentCandy = Images.CandyO; break;
			case TYPE16: currentCandy = Images.CandyP; break;
			default: currentCandy = Images.CandyA; break;
			}
			// Checking to ensure the user never removes more than four parts from a kit.
			if (candyNumber < 5 && currentSelection == PartType.NOTYPE) {
				System.out.println("Cannot remove more than four parts!");
				return;
			}
			if ((flag >= 0) && (flag <= 7) &&
					(kitConfig[flag] != currentSelection)) {
				// Changes the current number of candies depending on whether the current kit
				// slot does not have a type or the current selection does not have a type.
				if (currentSelection == PartType.NOTYPE) {
					candyNumber--;
				} else if (kitConfig[flag] == PartType.NOTYPE) {
					candyNumber++;
				}
				PartType temp = kitConfig[flag];
				
				// Sets value and value1 if and only if they exist.
				int value = hashMap.containsKey(temp) ? hashMap.get(temp) : 0;
				int value1 = hashMap.containsKey(currentSelection) ? hashMap.get(currentSelection) : 0;
				if (temp != PartType.NOTYPE) {
					if (hashMap.get(temp) == 1) {
						hashMap.remove(temp);
					} else {
						hashMap.put(temp, value - 1);
					}
				}
				if (currentSelection != PartType.NOTYPE) {
					hashMap.put(currentSelection, value1 + 1);
				}
				
				// Sets the configuration settings.
				kitConfig[flag] = currentSelection;
				kitButtons[flag].setIcon(currentCandy);
				// for (Map.Entry<PartType, Integer> entry : hashMap.entrySet()) {
				// 	System.out.println("K: " + entry.getKey() + " V: " + entry.getValue());
				// }
			}
		}
	}
}
