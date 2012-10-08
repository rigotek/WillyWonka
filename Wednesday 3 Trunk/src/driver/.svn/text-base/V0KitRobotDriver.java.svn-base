//Alberto Marroquin
package driver;
import engine.Agent;
import engine.agents.*;
import engine.test.mock.MockPartsRobot;
import gui.*;
import gui.interfaces.Gui;
import gui.locationsimg.Images;
import gui.locationsimg.Locations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import structures.AgentPart;
import structures.Configuration;
import structures.PartType;
/**
 * This driver will behave as the following: The KitBoxbelt will create a kit. Kit Robot will 
 * pick up kit and place it on the Kit Stand. When the kit is done, Kit Robot picks up finished
 * kit and place it on the Checking Station. Checking Station  takes a picture, and kit is
 * inspected. When inspection is done, Kit Robot picks it up and place it on the boat. All
 * the 'back end' is done by Agents.
 * @author Alberto
 *
 */
public class V0KitRobotDriver extends JPanel implements ActionListener {
	
	public static JFrame myframe;
	public static ArrayList<Gui> guilist = new ArrayList<Gui>();
	public static ArrayList<Agent> agentlist = new ArrayList<Agent>();
	public static JPanel mainpanel;
	public static java.util.Timer timer =new java.util.Timer();
	Rectangle background =  new Rectangle(0, 0,1080,600);
	
	public static void main(String[] args) {
		JFrame driver1 = new JFrame();
		V0KitRobotDriver driver = new V0KitRobotDriver();
		//mainpanel = new JPanel();
		Dimension d = new Dimension(1080,600);
		//driver1.add(mainpanel);
		driver1.setResizable(false);
		driver1.add(driver);
		driver1.setMinimumSize(d);
		driver1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		driver1.setVisible(true);//trick
		driver1.setLayout(null);
		driver1.setBackground(Color.BLUE);
		
		
		Timer factoryTimer = new Timer(25,null);
		factoryTimer.addActionListener(driver);
		
		ArrayList<Gui>componentList = new ArrayList<Gui>();
		
		
		//START HERE
		
		/* Instantiate gui stuff */
		GuiKitRobot guiKitRobot = new GuiKitRobot(Locations.kitRobotX, Locations.kitRobotY);
		GuiCamera guiCamera = new GuiCamera(Locations.kitCradleX, Locations.kitCradleY);
		GuiCheckingStation guiCheckingStation = new GuiCheckingStation(Locations.checkingStationX, Locations.checkingStationY);
		GuiBoxBelt guiBoxBelt = new GuiBoxBelt(Locations.boxBeltX, Locations.boxBeltY);
		
		/* Add all the GUI's to a Gui list */
		guilist.add(guiBoxBelt);
		guilist.add(guiCheckingStation);
		guilist.add(guiKitRobot);
		guilist.add(guiCamera);
		
		
		//myFrame.add(mainPanel);
		
		/* Array of all agents */
		ArrayList<Agent> agent = new ArrayList<Agent>();
		
		/* Creating all the agents */
		
		KitStandAgent kitStandAgent = new KitStandAgent("Kit Stand");
		KitRobotAgent kitRobotAgent = new KitRobotAgent("Kit Robot");
		KitCameraAgent kitCameraAgent = new KitCameraAgent("Kit Camera");
		MockPartsRobot mockPartRobot = new MockPartsRobot("Mock Part Robot");
	
		/* Setting the dependencies of the kitStand */
		kitStandAgent.setKitRobot(kitRobotAgent);
		kitStandAgent.setPartsRobot(mockPartRobot);
		kitStandAgent.setGuiCheckingStation(guiCheckingStation);
		
		/* Setting the dependencies of the kitRobot */
		kitRobotAgent.setKitCamera(kitCameraAgent);
		kitRobotAgent.setKitStand(kitStandAgent);
		kitRobotAgent.setGuiBoxBelt(guiBoxBelt);
		kitRobotAgent.setGuiKitRobot(guiKitRobot);
		
		/* Setting the dependencies of the kitCamera */
		kitCameraAgent.setKitRobot(kitRobotAgent);
		kitCameraAgent.setGuiCheckingStation(guiCamera);
		
		/* Setting the Gui's agents */
		guiKitRobot.setAgent(kitRobotAgent);
		guiCamera.setAgent(kitCameraAgent);
		guiBoxBelt.setAgent(kitRobotAgent);
		guiCheckingStation.SetKitStand(kitStandAgent);
		
		
		/* Adding the agents to the array of agents */
		agent.add(kitStandAgent);
		agent.add(kitRobotAgent);
		agent.add(kitCameraAgent);
		
		
		/* Starting the threads of all the agents */
		for(int i = 0; i < agent.size(); i++) {
			agent.get(i).startThread();
		}
	
		//making a configuration and sending it to kit robot
		HashMap <PartType,Integer> h = new HashMap<PartType,Integer> ();
		ArrayList <AgentPart> parts = new ArrayList<AgentPart> ();
		h.put(PartType.TYPE1, 3);
		AgentPart type1 = new AgentPart(PartType.TYPE1);
		AgentPart type12 = new AgentPart(PartType.TYPE1);
		AgentPart type13 = new AgentPart(PartType.TYPE1);
		parts.add(type1);//Part of Type1 
		parts.add(type12);//Part of Type1 
		parts.add(type13);//Part of Type1 
		h.put(PartType.TYPE2, 2);
		AgentPart type31 = new AgentPart(PartType.TYPE2);
		AgentPart type32 = new AgentPart(PartType.TYPE2);
		parts.add(type31);//First Part of Type3
		parts.add(type32);//Second Part of Type3
		/*h.put(PartType.TYPE4, 1);
		AgentPart type4 = new AgentPart(PartType.TYPE4);
		parts.add(type4);//Part of Type 4
		h.put(PartType.TYPE5, 1);
		AgentPart type5 = new AgentPart(PartType.TYPE5);
		parts.add(type5);//Part of Type 5
		*/
		
		for (Map.Entry<PartType, Integer> entry : h.entrySet()) {
			System.out.println(entry.getValue());
			System.out.println(entry.getKey());
		}
		
		Configuration c1 = new Configuration(h);
		kitRobotAgent.msgHereIsKitConfiguration(c1);
		
		factoryTimer.start();
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.blue);
		g2.fill(background);
		g2.drawImage(Images.Boat.getImage(), null, null); 
		
		for(Gui gui : guilist) {//concurrent modification exception.sometimes
			gui.updateLocation();
			gui.draw(g2);
		}
	}

}
