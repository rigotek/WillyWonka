package driver;

import engine.Agent;
import engine.agents.*;
import engine.interfaces.Nest;
import gui.*;
import gui.interfaces.Gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class V0BlankDriver implements ActionListener {
	
	ArrayList<Gui> componentList = new ArrayList<Gui>();
	JFrame myFrame = new JFrame();
	JPanel mainPanel = new JPanel();
	
	public static void main(String[] args) {
		V0BlankDriver driver = new V0BlankDriver();
		Dimension d = new Dimension(1080,600);
		JFrame myFrame = new JFrame();
		JPanel mainPanel = new JPanel();
		ArrayList<Component> windowComponents = new ArrayList<Component>();
		windowComponents.add(myFrame);
		windowComponents.add(mainPanel);
		
		for(int t = 0;t<windowComponents.size();t++){
			windowComponents.get(t).setSize(d);
			windowComponents.get(t).setPreferredSize(d);
			windowComponents.get(t).setMinimumSize(d);
			windowComponents.get(t).setMaximumSize(d);
		}
		
		myFrame.setResizable(false);
		myFrame.setVisible(true);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.BLUE);
		
		Timer factoryTimer = new Timer(50,null);
		
		ArrayList<Gui>componentList = new ArrayList<Gui>();
		
		/* Instantiate gui parts robot */
		GuiPartsRobot guipartsrobot = new GuiPartsRobot(150,150);
		
		/* Instantiate guinest */
		GuiNest guinest = new GuiNest(300,150);
		
		/* Instantiate guikitstand */
		
		
		componentList.add(guipartsrobot);
		componentList.add(guinest);
		
		myFrame.add(mainPanel);
		
		ArrayList<Agent> agent = new ArrayList<Agent>();
		
		GantryAgent gantry = new GantryAgent("Gantry");
		List<FeederAgent> feederagent = new ArrayList<FeederAgent>();
		for(int i = 0; i < 4; i++) {
			feederagent.add(new FeederAgent("Feeder Agent"+i));
		}
		List<LaneAgent> laneagent = new ArrayList<LaneAgent>();
		for(int i = 0; i < 8; i++) {
			laneagent.add(new LaneAgent("Lane Agent"+i, 20));
		}
		List<Nest> nestlist = new ArrayList<Nest>();
		for(int i = 0; i < 8; i++) {
			nestlist.add(new NestAgent("Nest Agent"+i, 20));
		}
		List<LaneCameraAgent> cameraagent = new ArrayList<LaneCameraAgent>();
		for(int i = 0; i < 4; i++) {
			cameraagent.add(new LaneCameraAgent("Lane Camera"+i));
			cameraagent.get(i).setNest1(nestlist.get(i*2));
			cameraagent.get(i).setNest2(nestlist.get(i*2+1));
		}
		PartsRobotAgent partsrobotagent = new PartsRobotAgent("PartsRobot",nestlist);
		KitStandAgent kitstand = new KitStandAgent("KitStand");
		KitRobotAgent kitrobotagent = new KitRobotAgent("Kit Robot");
		KitCameraAgent kitcamera = new KitCameraAgent("Kit Camera");
		
		for(int i = 0; i < 8; i++) {
			laneagent.get(i).setFeeder(feederagent.get(i/2));
			laneagent.get(i).setNest(nestlist.get(i));
			((NestAgent) nestlist.get(i)).setLane(laneagent.get(i));
			((NestAgent) nestlist.get(i)).setLaneCamera(cameraagent.get(i/2));
			((NestAgent) nestlist.get(i)).setPartsRobot(partsrobotagent);
			cameraagent.get(i/2).setPartsRobot(partsrobotagent);
		}
		partsrobotagent.setKitStand(kitstand);
		kitstand.setKitRobot(kitrobotagent);
		kitstand.setPartsRobot(partsrobotagent);
		kitrobotagent.setKitCamera(kitcamera);
		kitrobotagent.setKitStand(kitstand);
		kitcamera.setKitRobot(kitrobotagent);
		
		agent.add(gantry);
		agent.addAll(feederagent);
		agent.addAll(laneagent);
		agent.addAll((Collection<? extends Agent>) nestlist);
		agent.addAll(cameraagent);
		agent.add(partsrobotagent);
		agent.add(kitstand);
		agent.add(kitrobotagent);
		agent.add(kitcamera);
		
		factoryTimer.addActionListener(driver);
		
		for(int i = 0; i < agent.size(); i++) {
			agent.get(i).startThread();
		}
		
		factoryTimer.start();
	}

	public void actionPerformed(ActionEvent e) {
		myFrame.repaint();
	}

	private void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		// TODO Auto-generated method stub
		for(int i = 0; i < componentList.size(); i++) {
			componentList.get(i).draw(g2);
		}
	}
}
