package driver;

import engine.*;
import engine.agents.*;
import engine.interfaces.Nest;
import engine.test.mock.*;
import gui.*;
import gui.holdable.GuiCandy;
import gui.holdable.GuiKit;
import gui.interfaces.*;
import gui.locationsimg.Locations;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import structures.*;
import java.awt.*;
import java.util.*;

public class V0PartsRobotDriver extends JPanel implements ActionListener {
	public static JFrame myframe;
	public static ArrayList<Gui> guilist = new ArrayList<Gui>();
	public static ArrayList<Agent> agentlist = new ArrayList<Agent>();
	public static JPanel mainpanel;
	public static ArrayList<GuiCandy> candylist = new ArrayList<GuiCandy>();
	
	public static void main(String args[]) {
		JFrame driver1 = new JFrame();
		V0PartsRobotDriver driver = new V0PartsRobotDriver();
		//create the frame and everything
		Dimension d = new Dimension(1080,600);
		//mainpanel = new JPanel();
		//driver.add(mainpanel);
		driver1.setSize(d);
		driver1.setResizable(false);
		driver1.add(driver);
		driver1.setVisible(true);
		//driver.setMinimumSize(d);
		driver1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		driver1.setLayout(null);
		driver1.setBackground(Color.BLUE);
		
		Timer factoryTimer = new Timer(25,null);
		factoryTimer.addActionListener(driver);
		
		//create the necessary guis: 2 nests, kitstand, partsrobot, camera
		GuiCheckingStation kitcheck = new GuiCheckingStation(Locations.kitCradleX, Locations.kitCradleY);
		GuiNest n1 = new GuiNest(Locations.nestAX, Locations.nestAY);
		GuiNest n2 = new GuiNest(Locations.nestBX, Locations.nestBY);
		GuiPartsRobot guirobot = new GuiPartsRobot(Locations.partsRobotX,Locations.partsRobotY);
		GuiCamera camera = new GuiCamera(Locations.cameraNestAX,Locations.cameraNestAY);
		//add them to the guilist
		guilist.add(kitcheck);
		guilist.add(n1);
		guilist.add(n2);
		//guilist.add(guirobot);
		guilist.add(camera);
		
		//create the agents: 2 nests, 1 camera, 1 parts robot, 1 kitstand
		NestAgent nest1 = new NestAgent("nest1", 20);
		NestAgent nest2 = new NestAgent("nest2", 20);
		ArrayList<Nest> nestlist = new ArrayList<Nest>();
		nestlist.add(nest1);
		nestlist.add(nest2);
		LaneCameraAgent cm = new LaneCameraAgent("camera");
		PartsRobotAgent partsrobot = new PartsRobotAgent("Parts Robot", nestlist);
		KitStandAgent kitstand = new KitStandAgent("Kit Stand");
		//add them all to the agentlist
		agentlist.add(nest1);
		agentlist.add(nest2);
		agentlist.add(cm);
		agentlist.add(partsrobot);
		agentlist.add(kitstand);
		
		//Link all of the guis to the agents and the agents to each other
		nest1.setGuiNest(n1);
		nest2.setGuiNest(n2);
		cm.setGuiCamera(camera);
		cm.setNest1(nest1);
		cm.setNest2(nest2);
		cm.setPartsRobot(partsrobot);
		camera.setAgent(cm);
		partsrobot.setGuiPartsRobot(guirobot);
		guirobot.setAgent(partsrobot);
		kitstand.setKitRobot(new MockKitRobot("Kit Robot"));
		kitstand.setPartsRobot(partsrobot);
		nest1.setLane(new MockLane("Mock Lane 1"));
		nest2.setLane(new MockLane("Mock Lane 2"));
		nest1.setLaneCamera(cm);
		nest1.setPartsRobot(partsrobot);
		nest2.setLaneCamera(cm);
		nest2.setPartsRobot(partsrobot);
		partsrobot.setKitStand(kitstand);
		
		
		for(Agent agent : agentlist) {
			agent.startThread();
		}
		
		//New Kit
		HashMap<PartType,Integer> config = new HashMap<PartType,Integer>();
		config.put(PartType.TYPE1, 1);
		config.put(PartType.TYPE2, 1);
		Configuration configuration = new Configuration(config);
		AgentKit kit = new AgentKit(configuration,1);
		GuiKit kitg = new GuiKit(Locations.kitAX, Locations.kitAY);
		guilist.add(kitg);
		kit.setGuiKit(kitg);
		partsrobot.msgINeedThese(kit, kitstand);
		
		HashMap<PartType,Integer> config2 = new HashMap<PartType,Integer>();
		config2.put(PartType.TYPE1, 1);
		config2.put(PartType.TYPE2, 1);
		Configuration configuration2 = new Configuration(config2);
		AgentKit kit2 = new AgentKit(configuration2,1);
		GuiKit kitg2 = new GuiKit(Locations.kitAX, Locations.kitAY);
		guilist.add(kitg2);
		kit.setGuiKit(kitg2);
		partsrobot.msgINeedThese(kit2, kitstand);
		
		guilist.add(guirobot);
		
		//New Candy
		AgentPart part1 = new AgentPart(PartType.TYPE1);
		GuiCandy candy1 = new GuiCandy(Locations.nestAX, Locations.nestAY, PartType.TYPE1);
		//candylist.add(candy1);
		//guilist.add(candy1);
		AgentPart part2 = new AgentPart(PartType.TYPE2);
		GuiCandy candy2	= new GuiCandy(Locations.nestBX, Locations.nestBY, PartType.TYPE2);
		//candylist.add(candy2);
		//guilist.add(candy2);
		part1.setGuiCandy(candy1);
		candy1.agent = part1;
		part2.setGuiCandy(candy2);
		candy2.agent = part2;
		nest1.msgNextPart(part1);
		nest2.msgNextPart(part2);
		
		//start the timer
		factoryTimer.start();
	}
	
	public void actionPerformed(ActionEvent ae) {
		repaint();
	}

	public void paint(Graphics g) {
		//System.out.print("Calling paint");
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.BLUE);
		g2.fillRect(0, 0, 1080, 600);
		for(Gui gui : guilist) {
			gui.updateLocation();
			gui.draw(g2);
		}
	}
}
