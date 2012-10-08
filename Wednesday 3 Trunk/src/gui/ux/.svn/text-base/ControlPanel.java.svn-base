package gui.ux;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Provides the user with the ability to interact with the Factory simulation. Primary functions
 * are "START" and "STOP", which start and stop all agent threads respectively.
 * 
 * @author David Tan
 */
public class ControlPanel extends JPanel implements ActionListener, GuiInterface {
	
	/** Refers to the last update of ControlPanel. */
	private static final long serialVersionUID = 20111127L;
	/** Refers to the internal ID of the ControlPanel class. */
	private static final String CONTROLPANELID = "QCXQFQFTEHNI";
	/** Refers to itself. */
	private final ControlPanel outer = this;
	
	// --------------- DATA ---------------
	
	/** Represents the parent JFrame in which ControlPanel resides. */
	private MainInterface parentFrame;
	/** KitSelection panel, selects user configuration for kits for use in the Factory simulation. */
	private KitSelection kitSelection;
	/** ConsolePanel panel, displays all printed statements from the Factory simulation. */
	private ConsolePanel consolePanel;
	
	/** Represents the JButton that will start the simulation. */
	private JButton jbtnStart;
	/** Represents the JButton that will stop the simulation. */
	private JButton jbtnStop;
	/** Represents the JSlider that will control vibration speed. */
	private JSlider jslVibration;
	/** Represents the JSlider that will control simulation speed. */
	private JSlider jslFrameRate;
	
	/** Represents the minimum global vibration rate. */
	private final int vibrMIN = 0;
	/** Represents the initial global vibration rate. */
	private final int vibrINI = 2;
	/** Represents the maximum global vibration rate. */
	private final int vibrMAX = 10;
	
	/** Represents the minimum frame rate. */
	private final int frameMIN = 0;
	/** Represents the initial frame rate. */
	private final int frameINI = 40;
	/** Represents the maximum frame rate. */
	private final int frameMAX = 200;
	
	// private AudioPlayer audioPlayer = null;
	
	// --------------- CONSTRUCTOR ---------------
	
	/**
	 * The ControlPanel creates all buttons that interact with the main factory simulation.
	 * 
	 * @param mainInterface Provides reference to parent JFrame
	 */
	public ControlPanel(MainInterface mainInterface) {
		parentFrame = mainInterface;
		setPreferredSize(new Dimension(280, 669));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		/* Initialize audio
		try {
			audioPlayer = new AudioPlayer(new File("assets/sounds/pureimagination.wav"));
			audio.start();
		}
		catch (Exception e){
			System.out.println("ERROR: Audio cannot be played");
		} */
		
		// Initialisation of top two buttons, namely jbtnStart and jbtnStop
		jbtnStart = new JButton("Start");
		jbtnStart.setVerticalTextPosition(AbstractButton.CENTER);
	    jbtnStart.setHorizontalTextPosition(AbstractButton.LEADING);
	    jbtnStart.setActionCommand("startsim");
	    jbtnStart.addActionListener(this);
		jbtnStart.setFocusPainted(false);
    	jbtnStart.setEnabled(true);
	    
		jbtnStop = new JButton("Stop");
		jbtnStop.setVerticalTextPosition(AbstractButton.CENTER);
	    jbtnStop.setHorizontalTextPosition(AbstractButton.TRAILING);
	    jbtnStop.setActionCommand("pausesim");
	    jbtnStop.addActionListener(this);
		jbtnStop.setFocusPainted(false);
    	jbtnStop.setEnabled(false);
    	
    	// Initialisation of the vibration speed slider.
    	jslVibration = new JSlider(JSlider.HORIZONTAL, vibrMIN, vibrMAX, vibrINI);
    	jslVibration.addChangeListener(new VibrationListener());
    	jslVibration.setMajorTickSpacing(5);
    	jslVibration.setMinorTickSpacing(1);
    	jslVibration.setPaintTicks(true);
    	jslVibration.setPaintLabels(true);
    	jslVibration.setFont(new Font("Dialog", Font.PLAIN, 10));
    	jslVibration.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
				"CONVEYOR VIBRATION SPEED",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				new Font("Dialog", Font.PLAIN, 9)));
    	
    	// Initialisation of the frame rate slider.
    	jslFrameRate = new JSlider(JSlider.HORIZONTAL, frameMIN, frameMAX, frameINI);
    	jslFrameRate.addChangeListener(new FrameRateListener());
    	jslFrameRate.setMajorTickSpacing(20);
    	jslFrameRate.setMinorTickSpacing(4);
    	jslFrameRate.setPaintTicks(true);
    	jslFrameRate.setPaintLabels(true);
    	jslFrameRate.setFont(new Font("Dialog", Font.PLAIN, 10));
    	jslFrameRate.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
				"FRAME RATE",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				new Font("Dialog", Font.PLAIN, 9)));
    	
    	// Initialisation of the KitSelection and ConsolePanel sub-panels.
    	kitSelection = new KitSelection(parentFrame);
    	consolePanel = new ConsolePanel(parentFrame);
    	
    	// Initialisation of the layout for the start and stop buttons.
    	JPanel startStop = new JPanel();
    	startStop.setLayout(new FlowLayout(FlowLayout.CENTER));
    	startStop.setPreferredSize(new Dimension(200, 30));
    	startStop.add(jbtnStart);
    	startStop.add(jbtnStop);
    	
    	// Initialisation of the combination layout for the buttons and sliders.
    	JPanel options = new JPanel();
    	options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
    	options.setPreferredSize(new Dimension(200, 178));
    	options.add(startStop);
    	options.add(jslVibration);
    	options.add(jslFrameRate);
    	
    	// Aesthetic settings and adding all sub-panels to the main control panel.
		options.setBorder(BorderFactory.createTitledBorder("Options"));
    	add(options);
    	add(kitSelection);
    	add(consolePanel);
    	
    	// Set a default vibration speed.
    	UserData.setVibrationSpeed(this, 2);
	}

	// --------------- ACTIONS ---------------
	
	/**
	 * Called when any button is pressed. The individual commands are distinguished by the
	 * unique action commands specified per button.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("startsim")) {
			jbtnStart.setEnabled(false);
			jbtnStop.setEnabled(true);
			parentFrame.start();
			parentFrame.toggleStatus(true);
	    } else if (e.getActionCommand().equals("pausesim")) {
	    	jbtnStart.setEnabled(true);
	    	jbtnStop.setEnabled(false);
	    	parentFrame.stop();
	    	parentFrame.toggleStatus(false);
	    } else {
	    	jbtnStart.setEnabled(true);
	    	jbtnStop.setEnabled(false);
	    	parentFrame.toggleStatus(false);
	    }
	}
	
	@Override
	public String returnPanelID() {
		return CONTROLPANELID;
	}

	// --------------- NESTED CLASS ---------------
	
	private class VibrationListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			UserData.setVibrationSpeed(outer, (int) source.getValue());
		}
	}
	
	// --------------- NESTED CLASS ---------------

	private class FrameRateListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
			parentFrame.adjustTimer((int) source.getValue());
		}
	}
}
