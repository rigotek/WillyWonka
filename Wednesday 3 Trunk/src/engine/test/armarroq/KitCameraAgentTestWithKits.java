package engine.test.armarroq;
//Alberto Marroquin
//import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
//import org.junit.Test;

import structures.AgentKit;
import structures.AgentPart;
import structures.Configuration;
import structures.KitPosition;
import structures.PartType;

import engine.agents.KitCameraAgent;
import engine.test.mock.MockKitRobot;
import junit.framework.TestCase;

/**
 * This class has two tests. First test will test the KitCamera, handling one kit at a time. The second test 
 * will test the KitCamera, handling multiple kits at a time. These test will take time since a timer is 
 * simulated.
 * @author Alberto Marroquin
 *
 */

public class KitCameraAgentTestWithKits extends TestCase {
	KitCameraAgent kc;
	//@Test
	public void testKitRobotWithOneKitAtATime() {
		kc = new KitCameraAgent("KitCamera");
		MockKitRobot krm = new MockKitRobot("KitRobot");
		kc.setKitRobot(krm);
		HashMap<PartType,Integer>  h = new HashMap<PartType,Integer> ();
		h.put(PartType.TYPE1, 1);
		List<AgentPart> parts = new LinkedList<AgentPart>();
		parts.add(new AgentPart(PartType.TYPE1));
		Configuration c1 = new Configuration(h);
		AgentKit k = new AgentKit(c1);
		k.parts = parts;
		k.setPosition(KitPosition.POSITION3);
		k.quantities = c1.configuration();
		kc.setEnableGUI(false);
		//Since Kit will only get one kit at a time, this scenario runs as the following;
		
		//Inspect Kit
		kc.msgInspectKit(k);
		
		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 0, krm.log.size());
		
		kc.pickAndExecuteAnAction();
		//This shouldn't do anything.
		kc.pickAndExecuteAnAction();
		

		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 0, krm.log.size());
		
		int timer = 0;
		int timeout = 1000 * 6;
		while (timer < timeout) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			timer += 50;
		}
		//The Kit has been inspected and results are out.
		System.out.println("Kit is now inspected.");
		//Kit Passed
		kc.pickAndExecuteAnAction();

		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 1, krm.log.size());
		
		assertTrue(
				"Mock KitRobot should have received message about Kit passing inspection. Event log: "
						+ krm.log.toString(), krm.log.containsString("KitRobot received message: msgKitIsGood. Kit is on slot"+
								KitPosition.POSITION3));
		//This shouldn't do anything.
		kc.pickAndExecuteAnAction();
		
		h = new HashMap<PartType,Integer> ();
		h.put(PartType.TYPE1, 1);
		Configuration c2 = new Configuration(h);
		AgentKit k2 = new AgentKit(c2);
		k2.setPosition(KitPosition.POSITION3);
		k2.quantities = c2.configuration();
		
		//Inspect Kit
		kc.msgInspectKit(k2);
		kc.pickAndExecuteAnAction();
		//This shouldn't do anything.
		kc.pickAndExecuteAnAction();

		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 1, krm.log.size());
		
		timer = 0;
		timeout = 1000 * 6;
		while (timer < timeout) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			timer += 50;
		}
		//The Kit has been inspected and results are out.
		System.out.println("Kit is now inspected.");
		//Kit Passed
		kc.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 2, krm.log.size());
		
		assertTrue(
				"Mock KitRobot should have received message about Kit passing inspection. Event log: "
						+ krm.log.toString(), krm.log.containsString("KitRobot received message: msgKitIsGood. Kit is on slot"+
								KitPosition.POSITION3));
		
		//This shouldn't do anything.
		kc.pickAndExecuteAnAction();

		HashMap<PartType,Integer>  h1 = new HashMap<PartType,Integer> ();
		h1.put(PartType.TYPE1, 1);
		Configuration c3 = new Configuration(h1);
		//Temp will be used as a kit with a different config from above.
		HashMap<PartType,Integer>  h2 = new HashMap<PartType,Integer> ();
		h2.put(PartType.TYPE3, 2);
		h2.put(PartType.TYPE4, 1);
		Configuration temp = new Configuration(h2);
		AgentKit k3 = new AgentKit(c3);
		k3.setPosition(KitPosition.POSITION3);
		k3.quantities = temp.configuration();
		
		//Inspect Kit
		kc.msgInspectKit(k3);
		
		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 2, krm.log.size());
		
		kc.pickAndExecuteAnAction();
		//This shouldn't do anything.
		kc.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 2, krm.log.size());
		
		timer = 0;
		timeout = 1000 * 6;
		while (timer < timeout) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			timer += 50;
		}
		//The Kit has been inspected and results are out.
		System.out.println("Kit is now inspected.");
		//Kit didn't passed
		kc.pickAndExecuteAnAction();
		//This shouldn't do anything.
		kc.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 3, krm.log.size());
		
		assertTrue(
				"Mock KitRobot should have received message about Kit passing inspection. Event log: "
						+ krm.log.toString(), krm.log.containsString("KitRobot received message: msgKitIsGood. Kit is on slot"+
								KitPosition.POSITION3));
	}
	
	//@Test
	public void testKitRobotWithMultipleKitsAtATime() {
		//Lets try having multiple kits requesting inspection.
		kc = new KitCameraAgent("kitCamera");
		MockKitRobot krm = new MockKitRobot("KitRobot");
		kc.setKitRobot(krm);
		kc.setEnableGUI(false);
		HashMap<PartType,Integer> p1 = new HashMap<PartType,Integer> ();
		p1.put(PartType.TYPE1, 1);
		List<AgentPart> parts = new LinkedList<AgentPart>();
		parts.add(new AgentPart(PartType.TYPE1));
		Configuration c4 = new Configuration(p1);
		AgentKit k4 = new AgentKit(c4);
		k4.parts =parts;
		k4.setPosition(KitPosition.POSITION3);
		k4.quantities = c4.configuration();
		HashMap<PartType,Integer>  p2 = new HashMap<PartType,Integer> ();
		p2.put(PartType.TYPE3, 2);
		p2.put(PartType.TYPE4, 1);
		HashMap<PartType,Integer>  p3 = new HashMap<PartType,Integer> ();
		p3.put(PartType.TYPE2, 2);
		p3.put(PartType.TYPE4, 5);
		Configuration c5 = new Configuration(p2);
		Configuration temp2 = new Configuration(p3);
		AgentKit k5 = new AgentKit(c5);
		k5.setPosition(KitPosition.POSITION3);
		k5.quantities = temp2.configuration();
		
		//Inspect Kit4. Kit5 will be next.
		kc.msgInspectKit(k4);
		kc.msgInspectKit(k5);
		
		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 0, krm.log.size());
		
		kc.pickAndExecuteAnAction();
		kc.pickAndExecuteAnAction();
		//This shouldn't do anything.
		kc.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 0, krm.log.size());
		
		int timer = 0;
		int timeout = 1000 * 6;
		while (timer < timeout) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			timer += 50;
		}
		//The Kit has been inspected and results are out.
		System.out.println("Kit is now inspected.");
		kc.pickAndExecuteAnAction();

		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 1, krm.log.size());
		
		assertTrue(
				"Mock KitRobot should have received message about Kit passing inspection. Event log: "
						+ krm.log.toString(), krm.log.containsString("KitRobot received message: msgKitIsGood. Kit is on slot"+
								KitPosition.POSITION3));
		
		System.out.println("Kit is now inspected.");
		kc.pickAndExecuteAnAction();
		

		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 2, krm.log.size());
		
		assertTrue(
				"Mock KitRobot should have received message about Kit failing inspection. Event log: "
						+ krm.log.toString(), krm.log.containsString("KitRobot received message: msgKitIsBad. Kit is on slot"+
								KitPosition.POSITION3));
		//This shouldn't do anything.
		kc.pickAndExecuteAnAction();
		

		assertEquals(
				"Mock KitRobot should have an empty event log before the KitRobot's scheduler is called. Instead, " +
				"the mock KitRobot's event log reads: "+ krm.log.toString(), 2, krm.log.size());
	
	}
}