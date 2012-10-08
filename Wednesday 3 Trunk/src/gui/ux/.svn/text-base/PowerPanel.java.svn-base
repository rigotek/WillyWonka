package gui.ux;

import gui.interfaces.Gui;
import gui.locationsimg.Images;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Provides the user with the ability to enable or disable any GUI agent in the Factory simulation.
 * 
 * @author David
 */
public class PowerPanel extends JPanel implements GuiInterface {

	/** Refers to the last update of the PowerPanel class. */
	private static final long serialVersionUID = 20111127L;
	/** Refers to the internal ID of the PowerPanel class. */
	private static final String POWERPANELID = "KEUAUYEOCH";
	
	// --------------- DATA ---------------
	
	/** Represents the parent JFrame in which PowerPanel resides. */
	// private MainInterface parentFrame;
	/** Represents the parent FactoryPanel which PowerPanel will change. */
	private FactoryPanel parentFactory;
	
	/** Represents the current number of agents in existence.*/
	private int numGuiAgents;
	
	/** Represents the array that contains all sets of buttons, names, and statuses. */
	private JPanel[] setArray;
	/** Represents the array that contains all buttons. */
	private JButton[] buttonsArray;
	/** Represents the array that contains all the names. */
	private JLabel[] labelsArray;
	/** Represents the array that contains all the statuses. */
	private JLabel[] powerArray;
	/** Represents the array that contains all the GUI references. */
	private Gui[] guiArray;
	
	/**
	 * 
	 * @param mainInterface Provides reference to parent JFrame
	 * @param factoryPanel Provides reference to parent FactoryPanel
	 */
	public PowerPanel(MainInterface mainInterface, FactoryPanel factoryPanel) {
		// parentFrame = mainInterface;
		this.parentFactory = factoryPanel;
		setPreferredSize(new Dimension(280, 669));
		
		// Initialisation of default settings.
		numGuiAgents = parentFactory.returnGuiNamesList().size();
		setLayout(new GridLayout(numGuiAgents, 1, 0, 0));

		// Initialisation of each array.
		setArray = new JPanel[numGuiAgents];
		buttonsArray = new JButton[numGuiAgents];
		labelsArray = new JLabel[numGuiAgents];
		powerArray = new JLabel[numGuiAgents];
		guiArray = new Gui[numGuiAgents];
		
		// Initialisation of settings for each GUI agent.
		for (int i = 0; i < numGuiAgents; i++) {
			// Initialise a button.
			buttonsArray[i] = new JButton(Images.CheckMark);
			buttonsArray[i].setBorderPainted(true);
			buttonsArray[i].setBorder(null);
			buttonsArray[i].setContentAreaFilled(false);
			buttonsArray[i].setFocusPainted(true);
			buttonsArray[i].setOpaque(false);
			
			// Initialise a label with the name and power status.
			labelsArray[i] = new JLabel(parentFactory.returnGuiNamesList().get(i).toString()
					+ " Power Status: ");
			labelsArray[i].setFont(new Font("Dialog", Font.PLAIN, 9));
			labelsArray[i].setBorder(null);
			powerArray[i] = parentFactory.returnGuiList().get(i).checkPower() != null
					?	new JLabel(parentFactory.returnGuiList().get(i).checkPower().toString())
					:	new JLabel(Gui.Power.ON.toString());
			powerArray[i].setFont(new Font("Dialog", Font.PLAIN, 9));
			powerArray[i].setBorder(null);
			
			// Initialise a small panel with the button and labels.
			setArray[i] = new JPanel();
			setArray[i].setLayout(new FlowLayout(10));
			setArray[i].add(buttonsArray[i]);
			setArray[i].add(labelsArray[i]);
			setArray[i].add(powerArray[i]);
			
			// Initialise an action listener for each button.
			buttonsArray[i].addActionListener(new CustomActionListener(i));
			guiArray[i] = parentFactory.returnGuiList().get(i);
			add(setArray[i]);
		}
	}
	
	@Override
	public String returnPanelID() {
		return POWERPANELID;
	}
	
	// --------------- NESTED CLASS ---------------
	
	/**
	 * Designed to give a list of buttons the ability to affect GUI agents.
	 * 
	 * @author David Tan
	 */
	private class CustomActionListener implements ActionListener {
		
		/** Represents the current index of the GUI agent the CustomActionListener represents. */
		private int index;
		
		/**
		 * CustomActionListener only sets one value, index.
		 * 
		 * @param param index of current CustomActionListener
		 */
		protected CustomActionListener(int param) {
			index = param;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (parentFactory.returnGuiList().get(index).checkPower() == Gui.Power.ON) {
				parentFactory.returnGuiList().get(index).disable();
				buttonsArray[index].setIcon(Images.CircleX);
				powerArray[index].setText(parentFactory.returnGuiList().get(index).checkPower() != null
						?	parentFactory.returnGuiList().get(index).checkPower().toString()
						:	Gui.Power.ON.toString());
			} else {
				parentFactory.returnGuiList().get(index).enable();
				buttonsArray[index].setIcon(Images.CheckMark);
				powerArray[index].setText(parentFactory.returnGuiList().get(index).checkPower() != null
						?	parentFactory.returnGuiList().get(index).checkPower().toString()
						:	Gui.Power.ON.toString());
			}
		}
	}
}
