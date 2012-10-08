/**
 * @author Tyler Gregg
 * The GuiFeederTest sets up a unit-testing
 * environment for the GuiFeeder and GuiBin.
 * This test is meant to test the components
 * necessary for v0, namely the method
 * doMoveBinDownFeederLane.
 */
package gui.test.tgregg;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import engine.agents.FeederAgent;

import gui.GuiFeeder;
import gui.holdable.GuiBin;
import gui.locationsimg.Locations;

//The Main Frame
public class GuiFeederTest extends JFrame {
	private static final long serialVersionUID = 1L;
	public static GuiFeeder feeder = new GuiFeeder(Locations.feederABX, Locations.feederABY);
	public static GuiBin bin = new GuiBin(Locations.binOnArrivalX, Locations.binOnArrivalY);//TODO": fix
	GuiFeederTest(){
		add(new MainPanel());
	}
	public static void main(String[] args) throws Exception{
		GuiFeederTest app = new GuiFeederTest(); //create new instance to call the constructor
		app.repaint();
		app.setTitle("GuiFeederTest");
		app.setSize(1080, 600);
		app.setLocationRelativeTo(null);
		app.setVisible(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		//start hardcoded tests
		FeederAgent agent = new FeederAgent("name");
		feeder.setAgent(agent);
		feeder.doMoveBinDownFeederLane(bin);
		//may be used to test function beyond the
		//scope of v0
		/*
		while(true){
			if(feeder.presentState == GuiFeeder.State.UNLOAD){
				feeder.doPurgeBin();
				break;
			}
		}
		*/
    }
	//The main panel
	class MainPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public MainPanel(){
			Timer timer = new Timer(20, new CustomActionListener());
			setFocusable(true);
			timer.start();
		}
		public void paintComponent(Graphics g){
			feeder.draw(g);
		}
	}
	class CustomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			repaint();
		}
	}
}