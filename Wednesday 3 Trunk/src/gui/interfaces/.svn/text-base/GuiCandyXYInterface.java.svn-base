/**
 * @author Jungho Lee
 * Here we have a ripple interface.
 * This interface holds angle and radius of each candies
 * which needed to calculate movement of thrown parts
 */
// Only for Ripples in the ocean
package gui.interfaces;

public class GuiCandyXYInterface {

	private int candyAngle;
	private int candyRadius;

	public GuiCandyXYInterface(int candyX, int candyY, int rippleX, int rippleY)
	{
		candyRadius = (int) Math.sqrt(((rippleX-candyX)*(rippleX-candyX)) + ((rippleY-candyY)*(rippleY-candyY))); 
		candyAngle = (int) Math.toDegrees(Math.asin((Math.abs(candyY - rippleY)) / candyRadius));
		
		if(candyX < rippleX)
		{
			if(candyY < rippleY)
			{
				candyAngle = 180 - candyAngle;
			}
			else
			{
				candyAngle = 180 + candyAngle;
			}
		}
		else
		{
			if(candyX > rippleX)
			{
				if(candyY < rippleY)
				{
					//Stays the same
				}
				else
				{
					candyAngle = 360 - candyAngle;
				}
			}
		}
	}
	
	public int getCandyRadius() {
		return candyRadius;
	}
	
	public int getCandyX() {
		return (int) (candyRadius * Math.cos(Math.toRadians(candyAngle)));
	}
	
	public int getCandyY() {
		return (int) (candyRadius * Math.sin(Math.toRadians(candyAngle)));
	}
	
	public void advCandyAngle() {
		candyAngle--;
	}
	
	public void advCandyRadius() {
		candyRadius--;
	}
}