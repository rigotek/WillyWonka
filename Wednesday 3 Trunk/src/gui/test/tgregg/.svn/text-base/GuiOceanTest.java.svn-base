/**
 * @author Tyler Gregg
 * The GuiBoatTest sets up a unit-testing
 * environment for the GuiOcean.
 * This test is meant to test the components
 * necessary for v2.
 */
package gui.test.tgregg;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

import structures.PartType;
import gui.GuiOcean;
import gui.holdable.GuiCandy;
//import gui.locationsimg.Locations;
//import gui.locationsimg.Locations;

//The Main Frame
public class GuiOceanTest extends JFrame {
	private static final long serialVersionUID = 1L;
	public static GuiOcean ocean = new GuiOcean(0,0);
	public static GuiCandy candy = new GuiCandy(100, 100, PartType.TYPE1);
	GuiOceanTest(){
		add(new MainPanel());
	}
	public static void main(String[] args) throws Exception{
		GuiOceanTest app = new GuiOceanTest(); //create new instance to call the constructor
		app.repaint();
		app.setTitle("GuiOceanTest");
		app.setSize(1080, 600);
		app.setLocationRelativeTo(null);
		app.setVisible(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		//start hardcoded tests
		ocean.DoMoveToRipple(candy);
		/*
		while(true){
			if(boat.getHasBin() == false){
				GuiBin bin = new GuiBin(Locations.binOnArrivalX, Locations.binOnArrivalY);
				boat.doReceiveBinFromGantry(bin);
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
			ocean.draw(g);
		}
	}
	class CustomActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			repaint();
		}
	}
}