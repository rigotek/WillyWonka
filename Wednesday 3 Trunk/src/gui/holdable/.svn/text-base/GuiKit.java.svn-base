
package gui.holdable;



import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.geom.AffineTransform;
import java.util.ArrayList;


//import javax.swing.ImageIcon;

import gui.interfaces.Gui;
//import gui.locationsimg.Locations;
import gui.locationsimg.Images;

public class GuiKit extends Holdable implements Gui {
	private ArrayList<GuiCandy> candies;
	private boolean isOn;
	
	//kit can contain objects
	int CandyPos=20;
	int Width=40;
	int Length=60;
	int Width2=Width/2;
	int Length1=Length/4;
	int Length2=Length/2;
	int Length3=Length*(3/4);
	int[] PositionsX = new int[8];
	int[] PositionsY = new int[8];
	int[] TranslationX = new int[8];
	int[] TranslationY = new int[8];
	int TransDif=21;
	public GuiKit(int x, int y) {
		super(x, y);
		image =Images.Kit;//change
		isOn = true;
		candies=new ArrayList<GuiCandy>();
	}

	public GuiKit(int x, int y, Gui o) {
		super(x, y, o);
		image =Images.Kit;//change
		isOn = true;
		candies=new ArrayList<GuiCandy>();
	}
	
	public void clearKit(){
		candies=new ArrayList<GuiCandy>();
	}

	public void draw(Graphics g){
		
		PositionsX[0]=(currX);
		PositionsY[0]=(currY);
		PositionsX[1]=(currX+CandyPos);
		PositionsY[1]=(currY);
		PositionsX[2]=(currX);
		PositionsY[2]=(currY+CandyPos);
		PositionsX[3]=(currX+CandyPos);
		PositionsY[3]=(currY+CandyPos);
		PositionsX[4]=(currX);
		PositionsY[4]=(currY+(2*CandyPos));
		PositionsX[5]=(currX+CandyPos);
		PositionsY[5]=(currY+(2*CandyPos));
		PositionsX[6]=(currX);
		PositionsY[6]=(currY +(3*CandyPos));
		PositionsX[7]=(currX+CandyPos);
		PositionsY[7]=(currY +(3*CandyPos));
		
		
		TranslationX[0]=0;
		TranslationY[0]=0;
		TranslationX[1]=TransDif;
		TranslationY[1]=0;
		TranslationX[2]=0;
		TranslationY[2]=TransDif;
		TranslationX[3]=TransDif;
		TranslationY[3]=TransDif;
		
		TranslationX[4]=0;
		TranslationY[4]=2*TransDif;
		
		TranslationX[5]=TransDif;
		TranslationY[5]=2*TransDif;
		TranslationX[6]=0;
		TranslationY[6]=3*TransDif;
		TranslationX[7]=TransDif;
		TranslationY[7]=3*TransDif;
		
		/*
		TranslationX[4]=0;
		TranslationY[4]=0;
		TranslationX[5]=0;
		TranslationY[5]=0;
		TranslationX[6]=0;
		TranslationY[6]=0;
		TranslationX[7]=0;
		TranslationY[7]=0;
		*/
		
		super.draw(g);
		/*Graphics2D g2d = (Graphics2D) g;
		AffineTransform affineTransform;
		affineTransform = new AffineTransform(); 
		affineTransform.setToTranslation(currX, currY); 
		affineTransform.rotate(Math.toRadians(currAngle), 0, 0); 
		affineTransform.translate(transX, transY);
		g2d.drawImage(image.getImage(), affineTransform, null);*/

		//g.drawImage(image.getImage(), currX, currY, null);//change myImage to image

		//System.out.println(candies.size());


		for(int j=0; j<candies.size() && j<8; j++){

			if (transX == 0 && transY == 0){
				candies.get(j).setCurrRotation(0);
				candies.get(j).setLocation(PositionsX[j], PositionsY[j]);
				candies.get(j).resetImage();
				candies.get(j).draw(g);
			}
			else {
				
				candies.get(j).setLocation(currX, currY);
				candies.get(j).setCurrRotation(currAngle);
				candies.get(j).setTranslation(transX + TranslationX[j], transY + TranslationY[j]);
				candies.get(j).draw(g);
			}
			
		}	

	}

	public void updateLocation(){
		if(isOn){
			super.updateLocation();
		}
	}

	public void fillKit(ArrayList<GuiCandy> candy){
		//System.out.println("My kit is being filled with candy");
		candies.clear();
		//fill the kit array with objects
		candies.addAll(candy);
		for(int j=0; j<candies.size(); j++){
			candies.get(j).resetImage();
			candies.get(j).setCurrRotation(0);
		}

	}
	
	@Override
	public void resetImage() {
		super.resetImage();
		for(int j=0; j<candies.size(); j++){
			candies.get(j).resetImage();
		}
	}


	public Holdable getPart(int i){
		if (i<candies.size() && i>=0)
			return candies.get(i);
		else
			return null;
	}

	public boolean kitIsFull(){
		if(candies.size() == 8){
			return true;
		}
		return false;
	}
	
	public void MakeHorizontal(){
		image=Images.EmptyKit;
	}

	public void MakeVertical(){
		image=Images.Kit;
	}
	public void drain(){
		disable();
	}
	public void enable(){
		isOn = true;
	}
	public void disable(){
		isOn = false;
	}
	public Power checkPower(){
		if(isOn){
			return Power.ON;
		}
		else{
			return Power.OFF;
		}
	}
}
