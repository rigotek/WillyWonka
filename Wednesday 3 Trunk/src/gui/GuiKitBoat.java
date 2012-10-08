	package gui;
import engine.agents.KitRobotAgent;
	import gui.interfaces.Gui;
import gui.interfaces.Gui.Power;
	import gui.locationsimg.Images;
	import gui.locationsimg.Locations;
	import java.awt.Graphics;
	import java.awt.Image;
	import javax.swing.ImageIcon;
import gui.holdable.GuiKit;
	
	
	public class GuiKitBoat implements Gui {
		private Power Power;
		private GuiKit kit = null;
		private int currX, currY, destX, destY;
		private boolean hasKit;
		private boolean moved;
		private KitRobotAgent agent = null;
		private final int speed = 5;
	    public GuiKitBoat(){
	    	Power = Power.OFF;
	    	hasKit = false;
	    	moved = false;
	    	currX = Locations.boatDepartOffScreenX;
	    	currY = Locations.boatDepartOffScreenY;
	    	destX = currX;
	    	destY = currY;
	    }
	    public void setAgent(KitRobotAgent newagent){
	    	agent = newagent;
	    }
		@Override
		public void draw(Graphics g) {
			g.drawImage(Images.Boat2.getImage(), currX, currY, null);
			if(hasKit){
				kit.draw(g);
			}
		}

		@Override
		public void updateLocation() {
	       if(moved == false && hasKit == false && (currX == destX) && (currY == destY) && currY != Locations.boatDepartOffScreenY){
	        		agent.msgAnimationDone();
	        		moved = true;
	        		return;
	        } else {
	        	if (currX < destX) {
	        		currX = currX + speed;
	        	}
	        	else if (currX > destX) {
	        		currX = currX - speed;
	        	}
	        	if (currY < destY) {
	        		currY = currY + speed;
	        	} 
	        	else if (currY > destY) {
	        		currY = currY - speed;
	        	}
	        }
	       if (hasKit) {
	    	   if(currX == Locations.boatDepartDoneOffScreenX && currY == Locations.boatDepartDoneOffScreenY){
	    		   currX = Locations.boatDepartOffScreenX;
	    		   currY = Locations.boatDepartOffScreenY;
	    		   kit = null;
	    		   hasKit = false;
	    		   destX = currX;
	    		   destY = currY;
	    		   moved = false;
	    	   }
	    	   else{
	    		   for(int i=0; i < speed; i++){
	    			   kit.updateLocation();
	    		   }
	    	   }
	       }
		}
		
		public void msgDoGetKit(){
			destX = Locations.boatDepartureX;
			destY = Locations.boatDepartureY;
		}
		
		public void msgDoReceiveKit(GuiKit newkit){
			kit = newkit;
			hasKit = true;
			destX = Locations.boatDepartDoneOffScreenX;
			destY = Locations.boatDepartDoneOffScreenY;
			kit.setDestination(Locations.binDepartDoneOffScreenX, Locations.binDepartDoneOffScreenY);
		}
		
		@Override
		public void partCallback(Gui part) {
			//do nothing
		}

		@Override
		public int getCurrentX() {
			return currX;
		}

		@Override
		public int getCurrentY() {
			return currY;
		}

		@Override
		public int getDestinationX() {
			return destX;
		}

		@Override
		public int getDestinationY() {
			return destY;
		}

		@Override
		public void setDestination(int x, int y) {
			destX = x;
			destY = y;
		}
		
		public boolean hasKit() {
			return hasKit;
		}
		
		@Override
		public void enable() {
			Power = Power.ON;
		}

		@Override
		public void disable() {
			// TODO Auto-generated method stub
			Power = Power.OFF;
		}

		@Override
		public Power checkPower() {
			// TODO Auto-generated method stub
			return Power;
		}

		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			Power = Power.OFF;
		}

		@Override
		public void repair() {
			// TODO Auto-generated method stub
			Power = Power.ON;
		}

		@Override
		public boolean checkRepair() {
			// TODO Auto-generated method stub
			return false;
		}
		
}
