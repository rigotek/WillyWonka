package gui.test.davidtan;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import structures.PartType;

import engine.agents.FeederAgent;

import gui.GuiDiverter;
import gui.holdable.GuiCandy;
import gui.locationsimg.Locations;

/**
 * GuiDiverterTest serves as the unit testbench for the GuiDiverter and only tests the GuiDiverter;
 * a known issue is the NullPointerException thrown at the end of any and every successful move
 * of GuiCandy from start to finish, and this exception is thrown due to the partially initialised
 * FeederAgent. In the actual implementation, the FeederAgent does not have this problem.
 * 
 * @author David Tan
 */
public class GuiDiverterTest extends JFrame {

	/** Refers to last update of the GuiDiverterTest class. */
	private static final long serialVersionUID = 20111106L;

	// --------------- DATA ---------------
	
	/** GuiDiverter reference that represents current unit under test, or UUT. */
	public static GuiDiverter guiDiverter = new GuiDiverter(Locations.diverterABX, Locations.diverterABY);
	
	/** GuiCandy reference that represents a sample GuiCandy that will be passed to the GuiDiverter. */
	public static GuiCandy guiCandy = new GuiCandy(Locations.diverterABX + 40, Locations.diverterABY + 50, PartType.TYPE1);

	// --------------- CONSTRUCTOR ---------------
	
	/**
	 * Public constructor for the GuiDiverterTest class. Does nothing more than serve as a container
	 * for the internal member class MainPanel.
	 */
	public GuiDiverterTest() {
		add(new MainPanel());
	}

	// --------------- MAIN ---------------
	
	/**
	 * Executes the main test, which is done in the GUI created. The user may input one of five
	 * commands to the GUI, namely 'd', 'r', 's', 't', and 'u'. 'u' and 'd' shift the direction of
	 * the diverter up and down respectively, 'r' and 't' reset the GuiCandy to be either of type
	 * A or type B respectively, and 's' sends a new GuiCandy through the diverter.
	 * 
	 * @param args Possible command-line inputs, unused
	 */
	public static void main(String[] args) {
		
		// Create a new GuiDiverterTest to set up test.
		GuiDiverterTest frame = new GuiDiverterTest();
		
		// Repaint once to paint the JFrame with the JPanel.
		frame.repaint();
		
		// miscellaneous settings
		frame.setTitle("GuiDiverterTest");
		frame.setSize(1080, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		// start tests
		FeederAgent agent = new FeederAgent("FeederAgent");
		guiDiverter.setAgent(agent);
    }
	
	// --------------- ACTIONS ---------------
	
	/**
	 * Customised Action Listener class that does nothing more than call repaint every time it detects
	 * an ActionEvent passed by the timer.
	 * 
	 * @author David Tan
	 */
	class CustomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			repaint();
		}
	}

	// --------------- NESTED CLASS ---------------
	
	/**
	 * Serves as the JPanel in which the GuiDiverter resides, contains a timer and a KeyListener
	 * in addition to a paintComponent method that would draw the actual GuiDiverter onto the
	 * JPanel.
	 * 
	 * @author David Tan
	 */
	protected class MainPanel extends JPanel {

		/** Refers to last update of the MainPanel class. */
		private static final long serialVersionUID = 20111106L;

		// ------ CONSTRUCTOR ------

		/**
		 * Public constructor for MainPanel, only called by GuiDiverterTest. Creates a timer and
		 * sets up a KeyListener for the MainPanel.
		 */
		protected MainPanel() {
			
			// Create a new timer and start it.
			Timer timer = new Timer(20, new CustomActionListener());
			setFocusable(true);
			timer.start();
			
			// Add a KeyListener to the panel so that the user may provide input.
			addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
					switch ( e.getKeyChar() ) {
					// Switches diverter direction up.
					case 'u': guiDiverter.doMoveDiverterToPosition('u'); break;
					// Switches diverter direction down.
					case 'd': guiDiverter.doMoveDiverterToPosition('d'); break;
					// Sends the currently existing GuiCandy down the diverter.
					case 's': guiDiverter.doUnloadPartIntoLane(guiCandy); break;
					// Resets the GuiDiverter with a new GuiCandy of type A.
					case 'r': guiCandy = new GuiCandy(Locations.diverterABX + 40, Locations.diverterABY + 50, PartType.TYPE1); break;
					// Resets the GuiDiverter with a new GuiCandy of type B.
					case 't': guiCandy = new GuiCandy(Locations.diverterABX + 40, Locations.diverterABY + 50, PartType.TYPE2); break;
					// Does nothing.
					default : break;
					}
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// Does nothing.
				}
				
				@Override
				public void keyTyped(KeyEvent e) {
					// Does nothing.
				}
			});
		}

		// ------ GRAPHICS ------
		
		/**
		 * Draws the GuiDiverter and a black box to the right that would represent the GuiFeeder,
		 * which is smaller than drawn.
		 */
		public void paintComponent(Graphics g){
			guiDiverter.draw(g);
			g.setColor(Color.BLACK);
			g.fillRect(680, 40, 200, 180);
		}
	}
}