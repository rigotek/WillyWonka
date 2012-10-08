package gui.test.chujw;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import gui.*;
import gui.interfaces.Gui;
import engine.agents.*;
import structures.*;

public class KitRobotTest extends JPanel implements ActionListener
{
	private static int PANELWIDTH = 1000;
	private static int PANELHEIGHT = 1000;
	
	ArrayList<Gui> myGuis = new ArrayList<Gui>();
	
	GuiKitRobot myGuiKitRobot;
	KitRobotAgent myKitRobotAgent;
	
	GuiCheckingStation myCheckingStand; 
	KitStandAgent myKitStandAgent;
	
	GuiBoxBelt myBoxBelt;
	
	Graphics g;
	
	Timer t;
	
	public KitRobotTest()
	{
		t = new Timer(50,this);
		
		myKitRobotAgent = new KitRobotAgent("PEWPEWPEW");
		myGuiKitRobot = new GuiKitRobot();
		
		myKitRobotAgent.setGuiKitRobot(myGuiKitRobot);
		myGuiKitRobot.setAgent(myKitRobotAgent);
		
		
		
		t.start();
	}
	
	public static void main(String[] args)
	{
		KitRobotTest myPanel = new KitRobotTest();
		
		JFrame frame = new JFrame();
		frame.add(myPanel);
		frame.setSize(PANELWIDTH,PANELHEIGHT);
		frame.setName("Kit Robot Test (v.0-a)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		for(Gui gui: myGuis)
		{
			gui.updateLocation();
		}
		
		for(Gui gui: myGuis)
		{
			gui.draw(g);
		}
	}

}
