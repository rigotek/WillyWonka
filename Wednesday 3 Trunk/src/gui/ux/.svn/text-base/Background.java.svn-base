package gui.ux;

import java.awt.Graphics;

import gui.locationsimg.Images;


/**
 * 
 * @author Jonathan Chu
 *
 */
public class Background 
{
	int x1;
	int x2;
	int count;
	int opacityCount;
	boolean doneDraining;
	boolean doneFilling;

	private enum backgroundState{NORMAL, DRAINING, DRAINED, FILLING, FILLED};
	backgroundState myState;
	public Background()
	{
		myState = backgroundState.NORMAL;
		x1 = -2000;
		x2 = -5000;
		opacityCount = 0;
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(Images.DryBackground.getImage(), 0, 0, null);
		
		switch(myState)
		{
			case NORMAL:
			{
				g.drawImage(Images.Background.getImage(), x1, 0, null);
				g.drawImage(Images.Background.getImage(), x2, 0, null);
				break;
			}
			case DRAINING:
			{
				if(opacityCount < 170)
				{
					if(opacityCount < 10)
					{
						g.drawImage(Images.BackgroundDrain1.getImage(), x1, 0, null);
						g.drawImage(Images.BackgroundDrain1.getImage(), x2, 0, null);
						g.drawImage(Images.DrainSwirl1.getImage(), 0, 0, null);
					}
					else
					{
						if(opacityCount < 20)
						{
							g.drawImage(Images.BackgroundDrain2.getImage(), x1, 0, null);
							g.drawImage(Images.BackgroundDrain2.getImage(), x2, 0, null);
							g.drawImage(Images.DrainSwirl2.getImage(), 0, 0, null);
						}
						else
						{
							if(opacityCount < 30)
							{
								g.drawImage(Images.BackgroundDrain3.getImage(), x1, 0, null);
								g.drawImage(Images.BackgroundDrain3.getImage(), x2, 0, null);
								g.drawImage(Images.DrainSwirl3.getImage(), 0, 0, null);
							}
							else
							{
								if(opacityCount < 40)
								{
									g.drawImage(Images.BackgroundDrain4.getImage(), x1, 0, null);
									g.drawImage(Images.BackgroundDrain4.getImage(), x2, 0, null);
									g.drawImage(Images.DrainSwirl4.getImage(), 0, 0, null);
								}
								else
								{
									if(opacityCount < 50)
									{
										g.drawImage(Images.BackgroundDrain5.getImage(), x1, 0, null);
										g.drawImage(Images.BackgroundDrain5.getImage(), x2, 0, null);
										g.drawImage(Images.DrainSwirl5.getImage(), 0, 0, null);
									}
									else
									{
										if(opacityCount < 60)
										{
											g.drawImage(Images.BackgroundDrain6.getImage(), x1, 0, null);
											g.drawImage(Images.BackgroundDrain6.getImage(), x2, 0, null);
											g.drawImage(Images.DrainSwirl6.getImage(), 0, 0, null);
										}
										else
										{
											if(opacityCount < 70)
											{
												g.drawImage(Images.BackgroundDrain7.getImage(), x1, 0, null);
												g.drawImage(Images.BackgroundDrain7.getImage(), x2, 0, null);
												g.drawImage(Images.DrainSwirl7.getImage(), 0, 0, null);
											}
											else
											{
												if(opacityCount < 80)
												{
													g.drawImage(Images.BackgroundDrain8.getImage(), x1, 0, null);
													g.drawImage(Images.BackgroundDrain8.getImage(), x2, 0, null);
													g.drawImage(Images.DrainSwirl8.getImage(), 0, 0, null);
													doneDraining = true;
												}
												else
												{
													if(opacityCount < 90)
													{
														g.drawImage(Images.BackgroundDrain9.getImage(), x1, 0, null);
														g.drawImage(Images.BackgroundDrain9.getImage(), x2, 0, null);
														g.drawImage(Images.DrainSwirl9.getImage(), 0, 0, null);
													}
													else
													{
														if(opacityCount < 100)
														{
															g.drawImage(Images.BackgroundDrain10.getImage(), x1, 0, null);
															g.drawImage(Images.BackgroundDrain10.getImage(), x2, 0, null);
															g.drawImage(Images.DrainSwirl10.getImage(), 0, 0, null);
														}
														else
														{
															if(opacityCount < 110)
															{
																g.drawImage(Images.BackgroundDrain11.getImage(), x1, 0, null);
																g.drawImage(Images.BackgroundDrain11.getImage(), x2, 0, null);
																g.drawImage(Images.DrainSwirl11.getImage(), 0, 0, null);
															}
															else
															{
																if(opacityCount < 120)
																{
																	g.drawImage(Images.BackgroundDrain12.getImage(), x1, 0, null);
																	g.drawImage(Images.BackgroundDrain12.getImage(), x2, 0, null);
																	g.drawImage(Images.DrainSwirl11.getImage(), 0, 0, null);
																}
																else
																{
																	if(opacityCount < 130)
																	{
																		g.drawImage(Images.BackgroundDrain13.getImage(), x1, 0, null);
																		g.drawImage(Images.BackgroundDrain13.getImage(), x2, 0, null);
																		g.drawImage(Images.DrainSwirl13.getImage(), 0, 0, null);
																	}
																	else
																	{
																		if(opacityCount < 140)
																		{
																			g.drawImage(Images.BackgroundDrain14.getImage(), x1, 0, null);
																			g.drawImage(Images.BackgroundDrain14.getImage(), x2, 0, null);
																			g.drawImage(Images.DrainSwirl14.getImage(), 0, 0, null);
																		}
																		else
																		{
																			if(opacityCount < 150)
																			{
																				g.drawImage(Images.BackgroundDrain15.getImage(), x1, 0, null);
																				g.drawImage(Images.BackgroundDrain15.getImage(), x2, 0, null);
																				g.drawImage(Images.DrainSwirl15.getImage(), 0, 0, null);
																			}
																			else
																			{
																				if(opacityCount < 160)
																				{
																					g.drawImage(Images.BackgroundDrain16.getImage(), x1, 0, null);
																					g.drawImage(Images.BackgroundDrain16.getImage(), x2, 0, null);
																					g.drawImage(Images.DrainSwirl16.getImage(), 0, 0, null);
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				else
				{
					myState = backgroundState.DRAINED;
				}
			}//End of opaque background drawing
			break;
			case DRAINED:
			{
				//Nothing Really
				break;
			}
			case FILLING:
			{
				if(opacityCount >= 0)
				{
					if(opacityCount > 160)
					{
						g.drawImage(Images.BackgroundDrain16.getImage(), x1, 0, null);
						g.drawImage(Images.BackgroundDrain16.getImage(), x2, 0, null);
					}
					else
					{
						if(opacityCount > 150)
						{
							g.drawImage(Images.BackgroundDrain15.getImage(), x1, 0, null);
							g.drawImage(Images.BackgroundDrain15.getImage(), x2, 0, null);
						}
						else
						{
							if(opacityCount > 140)
							{
								g.drawImage(Images.BackgroundDrain14.getImage(), x1, 0, null);
								g.drawImage(Images.BackgroundDrain14.getImage(), x2, 0, null);
							}
							else
							{
								if(opacityCount > 130)
								{
									g.drawImage(Images.BackgroundDrain13.getImage(), x1, 0, null);
									g.drawImage(Images.BackgroundDrain13.getImage(), x2, 0, null);
								}
								else
								{
									if(opacityCount > 120)
									{
										g.drawImage(Images.BackgroundDrain12.getImage(), x1, 0, null);
										g.drawImage(Images.BackgroundDrain12.getImage(), x2, 0, null);
									}
									else
									{
										if(opacityCount > 110)
										{
											g.drawImage(Images.BackgroundDrain11.getImage(), x1, 0, null);
											g.drawImage(Images.BackgroundDrain11.getImage(), x2, 0, null);
										}
										else
										{
											if(opacityCount > 100)
											{
												g.drawImage(Images.BackgroundDrain10.getImage(), x1, 0, null);
												g.drawImage(Images.BackgroundDrain10.getImage(), x2, 0, null);
											}
											else
											{
												if(opacityCount > 90)
												{
													g.drawImage(Images.BackgroundDrain9.getImage(), x1, 0, null);
													g.drawImage(Images.BackgroundDrain9.getImage(), x2, 0, null);
												}
												else
												{
													if(opacityCount > 80)
													{
														g.drawImage(Images.BackgroundDrain8.getImage(), x1, 0, null);
														g.drawImage(Images.BackgroundDrain8.getImage(), x2, 0, null);
														doneFilling = true;
													}
													else
													{
														if(opacityCount > 70)
														{
															g.drawImage(Images.BackgroundDrain7.getImage(), x1, 0, null);
															g.drawImage(Images.BackgroundDrain7.getImage(), x2, 0, null);
														}
														else
														{
															if(opacityCount > 60)
															{
																g.drawImage(Images.BackgroundDrain6.getImage(), x1, 0, null);
																g.drawImage(Images.BackgroundDrain6.getImage(), x2, 0, null);
															}
															else
															{
																if(opacityCount > 50)
																{
																	g.drawImage(Images.BackgroundDrain5.getImage(), x1, 0, null);
																	g.drawImage(Images.BackgroundDrain5.getImage(), x2, 0, null);
																}
																else
																{
																	if(opacityCount > 40)
																	{
																		g.drawImage(Images.BackgroundDrain4.getImage(), x1, 0, null);
																		g.drawImage(Images.BackgroundDrain4.getImage(), x2, 0, null);
																	}
																	else
																	{
																		if(opacityCount > 30)
																		{
																			g.drawImage(Images.BackgroundDrain3.getImage(), x1, 0, null);
																			g.drawImage(Images.BackgroundDrain3.getImage(), x2, 0, null);
																		}
																		else
																		{
																			if(opacityCount > 20)
																			{
																				g.drawImage(Images.BackgroundDrain2.getImage(), x1, 0, null);
																				g.drawImage(Images.BackgroundDrain2.getImage(), x2, 0, null);
																			}
																			else
																			{
																				if(opacityCount > 10)
																				{
																					g.drawImage(Images.BackgroundDrain1.getImage(), x1, 0, null);
																					g.drawImage(Images.BackgroundDrain1.getImage(), x2, 0, null);
																				}
																				else
																				{
																					g.drawImage(Images.Background.getImage(), x1, 0, null);
																					g.drawImage(Images.Background.getImage(), x2, 0, null);
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
				else
				{
					myState = backgroundState.FILLED;
					g.drawImage(Images.Background.getImage(), x1, 0, null);
					g.drawImage(Images.Background.getImage(), x2, 0, null);
				}
				break;
			}
			case FILLED:
			{
				g.drawImage(Images.Background.getImage(), x1, 0, null);
				g.drawImage(Images.Background.getImage(), x2, 0, null);
				myState = backgroundState.NORMAL;
			}
		}
	}
	
	public void updateLocation()
	{
		x1++;
		x2++;
		if(x1 == 1000)
		{
			x1 = -5000;
		}
		if(x2 == 1000)
		{
			x2 = -5000;
		}
		
		switch(myState)
		{
			case NORMAL:
			{
				doneDraining = false;
				doneFilling = false;
				opacityCount = 0;
				break;
			}
			case DRAINING:
			{
				opacityCount++;
				break;
			}
			case DRAINED:
			{
				opacityCount = 170;
				break;
			}
			case FILLING:
			{
				opacityCount--;
				break;
			}
		};
	}
	
	public void drainOcean()
	{
		myState = backgroundState.DRAINING;
	}
	
	public void fillOcean()
	{
		myState = backgroundState.FILLING;
	}
	public void reset()
	{
		doneDraining = false;
		doneFilling = false;
		myState = backgroundState.NORMAL;
		x1 = -2000;
		x2 = -5000;
	}
	
	public boolean getDoneDraining()
	{
		return doneDraining;
	}
	public boolean getDoneFilling()
	{
		return doneFilling;
	}
}
