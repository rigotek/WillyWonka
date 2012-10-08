/**
 * @author Jungho Lee
 * Here we have a ripple test on the ocean.
 * The ripple itself is spinning
 * It takes candies, bins, and whatever
 * implements holdable.
 */
package gui.test.junghole;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.holdable.GuiBin;
import gui.holdable.GuiCandy;
import gui.ux.Background;
import gui.GuiBubble;
import gui.GuiOcean;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import structures.PartType;

public class RippleTest extends JPanel implements ActionListener
{
	private GuiCandy testCandy;
	private GuiOcean testOcean;
	private GuiBin testBin;
	
	Background myBackground;
	Timer t;

public RippleTest() {
	testCandy = new GuiCandy(200, 200, PartType.TYPE1);
	testOcean = new GuiOcean(100, 100);
	testBin = new GuiBin(250,250);
	testOcean.DoMoveToRipple(testCandy);
	testOcean.DoMoveToRipple(testBin);
	myBackground = new Background();
	t = new Timer(20, this);
	t.start();
}

public static void main(String[] args) {
	RippleTest myTest = new RippleTest();
	JFrame frame = new JFrame();
	frame.add(myTest);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setBackground(Color.gray);
	frame.setSize(1000, 700);
	frame.setVisible(true);
}

public void paint(Graphics g) {
	Graphics2D g2d = (Graphics2D)g;
	myBackground.updateLocation();
	myBackground.draw(g);
	//g2d.setBackground(Color.gray);
	updateLocation();
	testOcean.draw(g2d);
	//testCandy.draw(g2d);
	
}

public void updateLocation() {
	//testOcean.DoMoveToRipple(testCandy);
	testOcean.updateLocation();
}

public void actionPerformed(ActionEvent e) {
	repaint();
}


}