package driver;

import engine.Agent;
import engine.agents.*;
import engine.test.mock.MockNest;
import gui.*;
import gui.interfaces.Gui;
import gui.locationsimg.Locations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import structures.PartType;
public class V0FeederDriver extends JPanel implements ActionListener {
	
	public static JFrame myframe;
	public static LinkedList<Gui> guilist = new LinkedList<Gui>();
	public static ArrayList<Agent> agentlist = new ArrayList<Agent>();
	public static JPanel mainpanel;

	
	public static void main(String[] args) {
		JFrame driver1 = new JFrame();
		V0FeederDriver driver = new V0FeederDriver();
		mainpanel = new JPanel();
		Dimension d = new Dimension(1080,600);
		//driver.add(mainpanel);
		driver1.setResizable(false);
		driver1.setVisible(true);
		driver1.setMinimumSize(d);
		driver1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		driver1.add(driver);
		driver.setLayout(null);
		driver.setBackground(Color.BLUE);
		
		Timer factoryTimer = new Timer(5,null);
		factoryTimer.addActionListener(driver);
		
		
		
		//START HEREHEREHEREHEREHERE
		/*
		 * need to make two mockNests,
		 * one feederAgent, 
		 * two laneagents
		 */
		
		/* Instantiate gui lanes */
		GuiBoat guiBoat = new GuiBoat();
		GuiLane topGuiLane = new GuiLane(Locations.laneAX, Locations.laneAY);
		GuiLane bottomGuiLane = new GuiLane(Locations.laneBX, Locations.laneBY);
		GuiFeeder guiFeeder = new GuiFeeder(Locations.feederABX, Locations.feederABY);
		GuiDiverter guiDiverter = new GuiDiverter(Locations.diverterABX, Locations.diverterABY);
		GuiGantryRobot guiGantry = new GuiGantryRobot(Locations.gantryRobotX, Locations.gantryRobotY);

		/* Add all the GUI's to a Gui list */
		guilist.addLast(guiBoat);
		guilist.addLast(topGuiLane);
		guilist.addLast(bottomGuiLane);
		guilist.addLast(guiDiverter);
		guilist.addLast(guiFeeder);
		guilist.addLast(guiGantry);

		
		//myFrame.add(mainPanel);
		
		/* Array of all agents */
		ArrayList<Agent> agent = new ArrayList<Agent>();
		
		/* Creating all the agents */
		GantryAgent gantryAgent = new GantryAgent("Gantry");
		FeederAgent feederAgent = new FeederAgent("Feeder Agent");
		LaneAgent laneAgent1 = new LaneAgent("Lane Agent1", 20);
		LaneAgent laneAgent2 = new LaneAgent("Lane Agent2", 20);
		MockNest mockNest1 = new MockNest("MockNest1");
		MockNest mockNest2 = new MockNest("MockNest2");
		
		/* Setting Feeder to testing mode... will only make itself spit out 10 parts. */
		feederAgent.setTestV0(true);
		
		/* Setting the dependencies of the Lanes */
		laneAgent1.setFeeder(feederAgent);
		laneAgent1.setNest(mockNest1);
		laneAgent2.setFeeder(feederAgent);
		laneAgent2.setNest(mockNest2);
		
		/* Setting the dependencies of the feeder */
		feederAgent.setGantryAgent(gantryAgent);
		feederAgent.setTopLaneAgent(laneAgent1);
		feederAgent.setBottomLaneAgent(laneAgent2);

		
		/* Adding the agents to the array of agents */
		agent.add(gantryAgent);
		agent.add(feederAgent);
		agent.add(laneAgent1);
		agent.add(laneAgent2);
		
		/* Setting the Gui's to the agents */
		laneAgent1.setGuiLane(topGuiLane);
		laneAgent2.setGuiLane(bottomGuiLane);
		feederAgent.setGuiFeeder(guiFeeder);
		feederAgent.setGuiDiverter(guiDiverter);
		gantryAgent.setGuiGantryRobot(guiGantry);
		
		/* Setting the Gui's agents */
		topGuiLane.setAgent(laneAgent1);
		bottomGuiLane.setAgent(laneAgent2);
		guiFeeder.setAgent(feederAgent);
		guiDiverter.setAgent(feederAgent);
		guiGantry.setAgent(gantryAgent);
		
		gantryAgent.setGuiBoat(guiBoat);
		guiBoat.setAgent(gantryAgent);
		
		/* Starting the threads of all the agents */
		for(int i = 0; i < agent.size(); i++) {
			agent.get(i).startThread();
		}
				
		PartType partType1 = PartType.TYPE1;
		laneAgent1.msgChangeToThisPart(partType1);
		// PartType partType2 = PartType.TYPE2;
		// laneAgent2.msgChangeToThisPart(partType2);

		
		factoryTimer.start();

		
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 1080, 600);
		for(Gui gui : guilist) {
			gui.updateLocation();
			gui.draw(g2);
		}
	}

}
