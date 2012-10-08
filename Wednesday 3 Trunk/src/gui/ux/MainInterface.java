package gui.ux;

import gui.locationsimg.Images;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.Timer;

import structures.PartType;

/**
 * Provides the JFrame in which all other GUI panels will reside and gives the user the ability
 * to interact with said panels and view the board.
 * 
 * @author David Tan
 */
public class MainInterface extends JFrame implements GuiInterface {
	
	/** Refers to the last update of MainInterface. */
	private static final long serialVersionUID = 20111127L;
	/** Refers to the internal ID of the MainInterface class. */
	private static final String MAININTERFACEID = "DFCLXGFTERQTR";

	// --------------- DATA ---------------
	
	/** Refers to whether the Factory simulation has been started before. */
	private boolean started;
	/** Refers to the number of kits that must be created of the current type. */
	private int kitNumber;
	/** Holds the current state of the MainInterface class. */
	private enum Status {
		/** States that the MainInterface class is currently running. */
		START,
		/** States that the MainInterface class is currently stopped. */
		STOP
	}
	/** Refers to the current status of the MainInterface class. */
	private Status currentStatus;
	
	/** TabsPanel panel, contains all side panels. */
	private JTabbedPane tabsPanel;
	/** FactoryPanel panel, displays the actual Factory simulation. */
	private FactoryPanel factoryPanel;
	/** ControlPanel panel, allows the user to provide input to the Factory simulation. */
	private ControlPanel controlPanel;
	/** NonNormPanel panel, allows the user to provide other input to the Factory simulation. */
	private NonNormPanel nonNormPanel;
	/** PowerPanel panel, allows the user to control enable/disable status of every GUI agent. */
	private PowerPanel powerPanel;
	/** GuiGates class, will be drawn into a JPanel. */
	// private GuiGates guiGates;
	
	/** JMenuItem that effectively resets the Factory simulation when pressed. */
	private JMenuItem jmiNew;
	/** JMenuItem that exits the Factory simulation when pressed. */
	private JMenuItem jmiQuit;
	
	/** Timer that runs the entire Factory simulation. */
	private Timer timer;
	/** Refers to the current period of the timer. */
	private int delay;
	
	// --------------- CONSTRUCTOR ---------------
	
	/**
	 * Public constructor for the MainInterface class. Sets up the MainInterface class such that
	 * the static method main method needs only to create an instance of the MainInterface class
	 * and alter a few settings to successfully create the entire interface.
	 */
	public MainInterface() {
		setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		
		// Initialisation of default settings.
		started = false;
		kitNumber = 0;
		delay = 25;
		
		// Initialisation of all internal panels.
		controlPanel = new ControlPanel(this);
		controlPanel.setName("Control Panel");
		nonNormPanel = new NonNormPanel(this);
		nonNormPanel.setName("NonNorm Panel");
		factoryPanel = new FactoryPanel(this);
		factoryPanel.setName("Factory Panel");
		powerPanel = new PowerPanel(this, factoryPanel);
		powerPanel.setName("Power Panel");
		// guiGates = new GuiGates(nonNormPanel.audioPlayer);
		
		// Initialisation of the card layout panel that would contain the factory panel, the kit
		// selection panel, and the zoomed versions of factory sections.
		tabsPanel = new JTabbedPane();
		tabsPanel.addTab("Control", Images.CandyB, controlPanel, "Main control panel");
		tabsPanel.addTab("Non-norm", Images.CandyF, nonNormPanel, "Non-normatives control panel");
		tabsPanel.addTab("Power", Images.CandyJ, powerPanel, "Power control panel");
		
		// Initialisation of the factory panel and the control panel on the right.
		add(factoryPanel, BorderLayout.CENTER);
		add(tabsPanel, BorderLayout.EAST);
		
		// Initialisation of the menu bar at the top of the screen.
		JMenuBar menuBar = new JMenuBar();
		JMenu menu1 = new JMenu("File");
		menu1.add(jmiNew = new JMenuItem("New Simulation"));
		menu1.add(jmiQuit = new JMenuItem("Quit"));
		menuBar.add(menu1);
		setJMenuBar(menuBar);
		
		// Initialisation of action listeners for each of the JMenuItems in the JMenuBar.
		jmiNew.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					reset();
				}
			});
        jmiQuit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
		
        // Pack the frame to only be as large as necessary.
        pack();
        
        // Initialise a timer that fires every 25 milliseconds (40 Hz).
		timer = new Timer(delay, null);
		timer.addActionListener(new CustomActionListener());
	}
	
	// --------------- MAIN ---------------
	
	/**
	 * Only creates a new frame and sets some miscellaneous settings. Does almost nothing.
	 * 
	 * @param args Possible command-line inputs, unused
	 */
	public static void main(String[] args) {
		MainInterface frame = new MainInterface();
		
		frame.setTitle("Willy Wonka and the Chocolate Factory");
		frame.repaint();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	// --------------- ACTIONS ---------------
	
	/** Resets the entire Factory simulation. */
	public void reset() {
		
		// Stopping the simulation.
		timer.stop();
		factoryPanel.stopThreads();
		
		// Removal of existing panels and panes.
		remove(controlPanel);
		remove(nonNormPanel);
		remove(powerPanel);
		remove(factoryPanel);
		remove(tabsPanel);
		
		// Initialisation of default settings.
		started = false;
		kitNumber = 0;
		delay = 25;
		timer.setDelay(delay);
		
		// Initialisation of all internal panels.
		controlPanel = new ControlPanel(this);
		controlPanel.setName("Control Panel");
		nonNormPanel = new NonNormPanel(this);
		nonNormPanel.setName("NonNorm Panel");
		factoryPanel = new FactoryPanel(this);
		factoryPanel.setName("Factory Panel");
		powerPanel = new PowerPanel(this, factoryPanel);
		powerPanel.setName("Power Panel");
		
		// Initialisation of the card layout panel that would contain the factory panel, the kit
		// selection panel, and the zoomed versions of factory sections.
		tabsPanel = new JTabbedPane();
		tabsPanel.addTab("Control", Images.CandyB, controlPanel, "Main control panel");
		tabsPanel.addTab("Non-norm", Images.CandyF, nonNormPanel, "Non-normatives control panel");
		tabsPanel.addTab("Power", Images.CandyJ, powerPanel, "Power control panel");
		
		// Initialisation of the factory panel and the control panel on the right.
		add(factoryPanel, BorderLayout.CENTER);
		add(tabsPanel, BorderLayout.EAST);
		
		validate();
		repaint();
		System.out.println("Factory simulation reset.");
		System.gc();
	}
	
	/** Starts the timer. */
	public void start() {
		timer.start();
		if (!started) {
			factoryPanel.startThreads();
			started = true;
		}
	}
	
	/** Stops the timer. */
	public void stop() {
		timer.stop();
		factoryPanel.pauseThreads();
	}
	
	/** Called by the ControlPanel class to turn the Factory simulation on or off. */
	public void toggleStatus(boolean param) {
		if (param) {
			currentStatus = Status.START;
		} else {
			currentStatus = Status.STOP;
		}
	}
	
	/** Called by the ControlPanel class to alter the speed of the MainInterface. */
	public void adjustTimer(int frameRate) {
        if (frameRate == 0) {
            if (currentStatus == Status.START) {
            	stop();
            }
            return;
        }
        delay = 1000 / frameRate;
        timer.setDelay(delay);
        if ((currentStatus == Status.START) && !timer.isRunning()) {
            start();
        }
	}
	
	/** Called by the KitSelection class to set the number of kits to be created. */
	public void setConfiguration(int param) {
		if (currentStatus != Status.START) {
			System.out.println("Cannot create kits when simulation is stopped.");
			return;
		}
		kitNumber = param;
		System.out.println("Created " + kitNumber + " kits of following configuration:");
		for (Map.Entry<PartType, Integer> entry : UserData.returnMap().entrySet()) {
			System.out.println("\t" + entry.getValue() + " pieces of " + UserData.returnRef().get(entry.getKey()) + " candy");
		}
	}
	
	/** Called by the NonNormPanel class to call drain() in the FactoryPanel class. */
	public void drain() {
		factoryPanel.drain();
	}
	
	/** Called by the NonNormPanel class to call wave() in the FactoryPanel class. */
	public void wave() {
		factoryPanel.wave();
	}
	
	/** Called by the NonNormPanel class to call bubble() in the FactoryPanel class. */
	public void bubble() {
		factoryPanel.bubble();
	}
	
	/** Called by the NonNormPanel class to call revolt() in the FactoryPanel class. */
	public void revolt() {
		factoryPanel.revolt();
	}
	
	@Override
	public String returnPanelID() {
		return MAININTERFACEID;
	}
	
	// --------------- NESTED CLASS ---------------
	
	/**
	 * Simply calls the repaint method of each JPanel to be drawn and sends the current kit
	 * configuration to the KitRobot if necessary.
	 * 
	 * @author David Tan
	 */
	class CustomActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			factoryPanel.repaint();
			if (kitNumber > 0) {
				factoryPanel.setConfiguration(UserData.returnMap());
				kitNumber--;
			}
		}
	}
}
