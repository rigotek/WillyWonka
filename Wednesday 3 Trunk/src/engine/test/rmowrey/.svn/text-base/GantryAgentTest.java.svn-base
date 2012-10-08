package engine.test.rmowrey;

import engine.agents.GantryAgent;
import engine.test.mock.MockFeeder;
import junit.framework.TestCase;

public class GantryAgentTest extends TestCase {

	GantryAgent gantry;
	myBin mb;
	public class myBin{
		MockFeeder feeder;
		
	}
	
	public void testBinCreation(){
		gantry = new GantryAgent("Gantry");
		mb.feeder = new MockFeeder("Feeder");
		
		boolean bincreate = false;
		
		//bincreate = gantry.createBin(mb);
		assertTrue("Bin has been created, filled, loaded in Feeder",bincreate);
	}
	
	public void testBinLoading(){
		gantry = new GantryAgent("Gantry");
		mb.feeder = new MockFeeder("Feeder");
				
		assertTrue("Bin is not null.",mb != null);
		
		gantry.loadBin(mb.feeder);
	}
}
