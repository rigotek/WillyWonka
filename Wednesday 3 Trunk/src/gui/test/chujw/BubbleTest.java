package gui.test.chujw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.holdable.GuiCandy;
import gui.ux.Background;
import gui.GuiBubble;
import gui.GuiGates;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import structures.PartType;

public class BubbleTest extends JPanel implements ActionListener
{
	/**
	 * Author: Jonathan Chu
	 */
	private static final long serialVersionUID = 1L;
	private GuiCandy testCandy;
	private GuiBubble myBubble;
	private int waitCount;
	//GuiGates myGates;
	Background myBackground;
	Timer t;
	
	public BubbleTest()
	{
		//myGates = new GuiGates();
		myBackground = new Background();
		testCandy = new GuiCandy(50,50,PartType.TYPE1);
		myBubble = new GuiBubble(testCandy);
		waitCount = 0;
		t = new Timer(25, this);
		t.start();
		//this.addMouseListener(myGates);	
	}
	
	public static void main(String[] args)
	{
		BubbleTest myTest = new BubbleTest();
		JFrame frame = new JFrame();
		frame.add(myTest);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.gray);
		frame.setSize(1000, 700);
		frame.setVisible(true);
		//System.out.println(frame.getHeight());
		//System.out.println(frame.getWidth());
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		updateLocation();
		myBackground.draw(g2d);
		myBubble.draw(g2d);
		//myGates.draw(g2d);
	}

	public void updateLocation()
	{
		waitCount++;
		//if(waitCount == 50)
		//{
		//	myGates.openGates();
		//}
		if(waitCount == 500)
		{
			myBackground.drainOcean();
		}
		else
		{
			if(waitCount == 700)
			{
				myBackground.fillOcean();
			}
		}
		myBackground.updateLocation();
		myBubble.updateLocation();
		//myGates.updateLocation();
	}
	public void actionPerformed(ActionEvent e) 
	{
		repaint();
	}
}
