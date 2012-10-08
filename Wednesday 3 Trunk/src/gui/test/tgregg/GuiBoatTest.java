/**
 * @author Tyler Gregg
 * The GuiBoatTest sets up a unit-testing
 * environment for the GuiBoat and GuiBin.
 * This test is meant to test the components
 * necessary for v1.
 */
package gui.test.tgregg;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import engine.agents.GantryAgent;
//import gui.holdable.GuiBin;
import gui.GuiBoat;
import gui.holdable.GuiBin;
import gui.locationsimg.Locations;
//import gui.locationsimg.Locations;

//The Main Frame
public class GuiBoatTest extends JFrame {
	private static final long serialVersionUID = 1L;
	public static GuiBoat boat = new GuiBoat();
	GuiBoatTest(){
		add(new MainPanel());
	}
	public static void main(String[] args) throws Exception{
		GuiBoatTest app = new GuiBoatTest(); //create new instance to call the constructor
		app.repaint();
		app.setTitle("GuiBoatTest");
		app.setSize(1080, 600);
		app.setLocationRelativeTo(null);
		app.setVisible(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		//start hardcoded tests
		GantryAgent agent = new GantryAgent("name");
		boat.setAgent(agent);
		boat.doBringBinToGantry();
		while(true){
			if(boat.getHasBin() == false){
				GuiBin bin = new GuiBin(Locations.binOnArrivalX, Locations.binOnArrivalY);
				boat.doReceiveBinFromGantry(bin);
				break;
			}
		}
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
			boat.draw(g);
		}
	}
	class CustomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			repaint();
		}
	}
}