package gui.ux;

import gui.audio.AudioPlayer;
import gui.locationsimg.Images;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Provides the user with the ability to start non-normative scenarios within the Factory
 * simulation, such as damaging or disabling various parts of the factory.
 * 
 * @author David Tan
 */
public class NonNormPanel extends JPanel implements ActionListener, GuiInterface {

	/** Refers to the last update of the NonNormPanel class. */
	private static final long serialVersionUID = 20111127L;
	/** Refers to the internal ID of the NonNormPanel class. */
	private static final String NONNORMPANELID = "QMSVGYPYCLIB";
	
	// --------------- DATA ---------------
	
	/** Represents the parent JFrame in which NonNormPanel resides. */
	private MainInterface parentFrame;
	
	/** Represents the current state of each JButton: break/fix. */
	private boolean[] buttonStates;
	/** Represents the JButton that will drain all chocolate and stop all agents. */
	private JButton jbtnDrain;
	/** Represents the JButton that will activate a chocolate wave that will stop all boats. */
	private JButton jbtnWave;
	/** Represents the JButton that will force all free-standing candies to bubble away. */
	private JButton jbtnBubble;
	/** Represents the JButton that will send all robots in the Factory simulation into revolts. */
	private JButton jbtnRevolt;
	
	/** Represents the AudioPlayer that will play music during the Factory simulation. */
	private AudioPlayer audioPlayer;
	
	// --------------- CONSTRUCTOR ---------------
	
	/**
	 * The NonNormPanel creates all buttons that interact create non-normative scenarios.
	 * 
	 * @param mainInterface Provides reference to parent JFrame
	 */
	public NonNormPanel(MainInterface mainInterface) {
		parentFrame = mainInterface;
		setPreferredSize(new Dimension(280, 669));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Initialisation of the AudioPlayer.
		audioPlayer = null;
		try {
			audioPlayer = new AudioPlayer(new File("assets/sounds/pureimagination.wav"));
			audioPlayer.start();
		}
		catch (Exception ex){
			System.out.println("ERROR: Audio cannot be played.");
		}
		
		// Initialisation of the JButtons.
		buttonStates = new boolean[4];
		for (int i = 0; i < 4; i++) {
			buttonStates[i] = false;
		}
		
		jbtnDrain = new JButton(Images.ButtonB);
	    jbtnDrain.setActionCommand("drainocean");
	    jbtnDrain.addActionListener(this);
    	jbtnDrain.setEnabled(true);
    	
		jbtnWave = new JButton(Images.ButtonE);
		jbtnWave.setActionCommand("wave");
		jbtnWave.addActionListener(this);
		jbtnWave.setEnabled(true);
		
		jbtnBubble = new JButton(Images.ButtonC);
		jbtnBubble.setActionCommand("bubble");
		jbtnBubble.addActionListener(this);
		jbtnBubble.setEnabled(true);
		
		jbtnRevolt = new JButton(Images.ButtonD);
	    jbtnRevolt.setActionCommand("revolt");
	    jbtnRevolt.addActionListener(this);
    	jbtnRevolt.setEnabled(true);
		
		// Initialisation of the layout for the non-normative buttons.
		JPanel normLayout = new JPanel();
		normLayout.setLayout(new GridLayout(4, 1, 0, 0));
		normLayout.setPreferredSize(new Dimension(280, 539));
		normLayout.setBorder(BorderFactory.createTitledBorder("Non-Normative Scenarios"));
		normLayout.add(jbtnDrain);
		normLayout.add(jbtnWave);
		normLayout.add(jbtnBubble);
		normLayout.add(jbtnRevolt);
		
		add(normLayout);
	}
	
	// --------------- ACTIONS ---------------
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("drainocean")) {
			parentFrame.drain();
			if (!buttonStates[0]) {
				jbtnDrain.setIcon(Images.ButtonA);
				buttonStates[0] = true;
			} else {
				jbtnDrain.setIcon(Images.ButtonB);
				buttonStates[0] = false;
			}
	    } else if (e.getActionCommand().equals("wave")) {
	    	parentFrame.wave();
			if (!buttonStates[1]) {
				jbtnWave.setIcon(Images.ButtonA);
				buttonStates[1] = true;
			} else {
				jbtnWave.setIcon(Images.ButtonE);
				buttonStates[1] = false;
			}
	    } else if (e.getActionCommand().equals("bubble")) {
	    	parentFrame.bubble();
		} else if (e.getActionCommand().equals("revolt")) {
			parentFrame.revolt();
			if (!buttonStates[3]) {
				jbtnRevolt.setIcon(Images.ButtonA);
				buttonStates[3] = true;
				if (audioPlayer != null) { audioPlayer.stop(); }
				audioPlayer = null;
				try {
					audioPlayer = new AudioPlayer(new File("assets/sounds/oompastep.wav"));
					audioPlayer.start();
				}
				catch (Exception ex) {
					System.out.println("ERROR: Audio cannot be played.");
				}
			} else {
				jbtnRevolt.setIcon(Images.ButtonD);
				buttonStates[3] = false;
				if (audioPlayer != null) { audioPlayer.stop(); }
				audioPlayer = null;
				try {
					audioPlayer = new AudioPlayer(new File("assets/sounds/pureimagination.wav"));
					audioPlayer.start();
				}
				catch (Exception ex) {
					System.out.println("ERROR: Audio cannot be played.");
				}
			}
	    }
	}
	
	@Override
	public String returnPanelID() {
		return NONNORMPANELID;
	}
}
