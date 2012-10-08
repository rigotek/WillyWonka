/**
 * @author Jungho Lee
 */
package gui.holdable;

import gui.interfaces.Gui;
import gui.locationsimg.Images;

import java.awt.Graphics;

import structures.AgentPart;
import structures.PartType;

public class GuiCandy extends Holdable implements Gui {

	PartType partType;
	public AgentPart agent;

	public GuiCandy(int x, int y, PartType type) {
		super(x, y);
		this.partType = type;
		// only two different candy types for v0
		if (partType.ordinal() == 0) {
			image = Images.CandyA;}
		else if (partType.ordinal() == 1) {
			image = Images.CandyB;}
		else if (partType.ordinal() == 2) {
			image = Images.CandyC;}
		else if (partType.ordinal() == 3) {
			image = Images.CandyD;}
		else if (partType.ordinal() == 4) {
			image = Images.CandyE;}
		else if (partType.ordinal() == 5) {
			image = Images.CandyF;}
		else if (partType.ordinal() == 6) {
			image = Images.CandyG;}
		else if (partType.ordinal() == 7) {
			image = Images.CandyH;}
		else if (partType.ordinal() == 8) {
			image = Images.CandyI;}
		else if (partType.ordinal() == 9) {
			image = Images.CandyJ;}
		else if (partType.ordinal() == 10) {
			image = Images.CandyK;}
		else if (partType.ordinal() == 11) {
			image = Images.CandyL;}
		else if (partType.ordinal() == 12) {
			image = Images.CandyM;}
		else if (partType.ordinal() == 13) {
			image = Images.CandyN;}
		else if (partType.ordinal() == 14) {
			image = Images.CandyO;}
		else if (partType.ordinal() == 15) {
			image = Images.CandyP;}
	}

	/**
	 * Sets the agent part
	 * @param ap
	 */
	public void setAgentPart(AgentPart ap) {
		agent = ap;
	}

	public GuiCandy(int x, int y, Gui o, PartType type) {
		super(x, y, o);
		// only two different candy types for v0
		if (type.ordinal() == 0) {
			image = Images.CandyA;}
		else if (type.ordinal() == 1) {
			image = Images.CandyB;}
		else if (partType.ordinal() == 2) {
			image = Images.CandyC;}
		else if (partType.ordinal() == 3) {
			image = Images.CandyD;}
		else if (partType.ordinal() == 4) {
			image = Images.CandyE;}
		else if (partType.ordinal() == 5) {
			image = Images.CandyF;}
		else if (partType.ordinal() == 6) {
			image = Images.CandyG;}
		else if (partType.ordinal() == 7) {
			image = Images.CandyH;}
		else if (partType.ordinal() == 8) {
			image = Images.CandyI;}
		else if (partType.ordinal() == 9) {
			image = Images.CandyJ;}
		else if (partType.ordinal() == 10) {
			image = Images.CandyK;}
		else if (partType.ordinal() == 11) {
			image = Images.CandyL;}
		else if (partType.ordinal() == 12) {
			image = Images.CandyM;}
		else if (partType.ordinal() == 13) {
			image = Images.CandyN;}
		else if (partType.ordinal() == 14) {
			image = Images.CandyO;}
		else if (partType.ordinal() == 15) {
			image = Images.CandyP;}
	}


	@Override
	public void draw(Graphics g) {
		if(image != null){
			
			super.draw(g);
			
			//g.drawImage(image.getImage(), currX, currY, null);
			/*Graphics2D g2d = (Graphics2D) g;
			AffineTransform affineTransform;
			affineTransform = new AffineTransform(); 
			affineTransform.setToTranslation(currX, currY); 
			affineTransform.rotate(Math.toRadians(currAngle), 0, 0); 
			affineTransform.translate(transX, transY);
			g2d.drawImage(image.getImage(), affineTransform, null);*/
		}
		else 
			System.out.println("image is null");
	}
}	