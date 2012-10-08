package engine.test.armarroq;
//Alberto Marroquin
//import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.LinkedList;

//import org.junit.Test;

import structures.AgentKit;
import structures.Configuration;
import structures.KitPosition;
import structures.PartType;

import engine.agents.KitRobotAgent;
import engine.test.mock.MockKitCamera;
import engine.test.mock.MockKitStand;
import junit.framework.TestCase;

/**
 * This test will test KitRobot, handling multiple kits on its slots.
 * @author Alberto
 *
 */

public class KitRobotAgentTestWithMultipleKits extends TestCase {
	KitRobotAgent kr;
	//@Test
	public void testKitRobotWithMultipleKits() {
		kr = new KitRobotAgent("KitRobot");
		MockKitStand ksm = new MockKitStand("KitStand");
		MockKitCamera kcm = new MockKitCamera("KitCamera");
		kr.setKitStand(ksm);
		kr.setKitCamera(kcm);
		kr.setEnableGUI(false);
		HashMap <PartType,Integer> h = new HashMap<PartType,Integer> ();
		h.put(PartType.TYPE1, 1);
		h.put(PartType.TYPE3, 2);
		h.put(PartType.TYPE4, 1);
		h.put(PartType.TYPE5, 1);
		Configuration c1 = new Configuration(h);
		Configuration c2 = new Configuration(h);
		Configuration c3 = new Configuration(h);
		Configuration c4= new Configuration(h);
		Configuration c5= new Configuration(h);
		//Placing kit 1 on stand
		System.out.println("Kit 1 is placed on stand");
		this.kr.msgHereIsKitConfiguration(c1);
		this.kr.pickAndExecuteAnAction();
		
		//Placing kit 2 on stand. Kit 3 is not placed.
		System.out.println("Kit 2 is placed on stand");
		this.kr.msgHereIsKitConfiguration(c2);
		this.kr.msgHereIsKitConfiguration(c3);
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 1, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 0, kcm.log.size());
		
		this.kr.pickAndExecuteAnAction();
		this.kr.pickAndExecuteAnAction();
		//This should do anything
		this.kr.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 2, ksm.log.size());
		
		LinkedList<AgentKit> kits = ksm.getKits();
		System.out.println("Kit Stand has "+ kits.size() + " kits on stand");
		
		assertTrue(
				"Mock KitStand should have received message about new configuration for kit. Event log: "
						+ ksm.log.toString(), ksm.log.containsString("KitStand received message: msgHereIsNextAgentKit. Kit" +
								" slot is " + KitPosition.POSITION1));
		

		assertTrue(
				"Mock KitStand should have received message about new configuration for kit. Event log: "
						+ ksm.log.toString(), ksm.log.containsString("KitStand received message: msgHereIsNextAgentKit. Kit" +
								" slot is " + KitPosition.POSITION2));

		//Kit one is done and will be inspected
		System.out.println("Kit "+ (1) + " is done");
		this.kr.msgKitIsDone(kits.get(0));
		this.kr.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 2, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have one event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 1, kcm.log.size());
		
		assertTrue(
				"Mock KitCamera should have received message about a kit to inspect. Event log: "
						+ kcm.log.toString(), kcm.log.containsString("KitCamera received message: msgInspectKit. Kit is on slot "+
				KitPosition.POSITION3));
		
		//Placing kit 3 on stand
		System.out.println("Kit 3 is placed on stand");
		this.kr.pickAndExecuteAnAction();

		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 3, ksm.log.size());

		assertTrue(
				"Mock KitStand should have received message about new configuration for kit. Event log: "
						+ ksm.log.toString(), ksm.log.containsString("KitStand received message: msgHereIsNextAgentKit. Kit" +
								" slot is " + KitPosition.POSITION1));
		
		kits = kcm.getKits();
		System.out.println("Kit Camera has "+ kits.size() + " kits on Inspection");
		
		//Kit one passes Inspection. 
		System.out.println("Kit passed Inspection");
		this.kr.msgKitIsGood(kits.get(0));
		//Placing next kit on Boat.
		this.kr.pickAndExecuteAnAction();
		
		//This shouldn't do anything.
		this.kr.pickAndExecuteAnAction();
		ksm.kits.remove(kits.get(0));
		kcm.kits.clear();
		
		
		kits = ksm.getKits();
		System.out.println("Kit Stand has "+ kits.size() + " kits on stand");
		//Kit Two is done and will be inspected
		System.out.println("Kit "+ (2) + " is done");
		this.kr.msgKitIsDone(kits.get(0));
		

		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 3, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 1, kcm.log.size());
		
		
		this.kr.pickAndExecuteAnAction();
		//This shouldn't do anything
		this.kr.pickAndExecuteAnAction();
		

		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 2, kcm.log.size());
		
		assertTrue(
				"Mock KitCamera should have received message about a kit to inspect. Event log: "
						+ kcm.log.toString(), kcm.log.containsString("KitCamera received message: msgInspectKit. Kit is on slot "+
				KitPosition.POSITION3));
		//Placing kit 4 on slot 2. Kit 5 is not placed.
		System.out.println("Kit 4 is placed on stand");
		this.kr.msgHereIsKitConfiguration(c4);
		this.kr.msgHereIsKitConfiguration(c5);
		this.kr.pickAndExecuteAnAction();
		this.kr.pickAndExecuteAnAction();
		//This shouldn't do anything
		this.kr.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 4, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 2, kcm.log.size());
		
		assertTrue(
				"Mock KitStand should have received message about new configuration for kit. Event log: "
						+ ksm.log.toString(), ksm.log.containsString("KitStand received message: msgHereIsNextAgentKit. Kit" +
								" slot is " + KitPosition.POSITION2));
		
		kits = kcm.getKits();
		System.out.println("Kit Camera has "+ kits.size() + " kits on Inspection");
		
		//Kit two passes Inspection. 
		System.out.println("Kit passed Inspection");
		this.kr.msgKitIsGood(kits.get(0));
		//Placing next kit on Boat.
		this.kr.pickAndExecuteAnAction();
		
		//This shouldn't do anything.
		this.kr.pickAndExecuteAnAction();
		ksm.kits.remove(kits.get(0));
		kcm.kits.clear();
		
		kits = ksm.getKits();
		System.out.println("Kit Stand has "+ kits.size() + " kits on stand");
		//Kit 3 is done and will be inspected
		System.out.println("Kit "+ (3) + " is done");
		this.kr.msgKitIsDone(kits.get(0));
		this.kr.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 4, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 3, kcm.log.size());
		
		//Kit 5 is placed on Stand
		System.out.println("Kit 5 is placed on stand");
		this.kr.pickAndExecuteAnAction();
		//This shouldn't do anything
		this.kr.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 5, ksm.log.size());

		assertTrue(
				"Mock KitStand should have received message about new configuration for kit. Event log: "
						+ ksm.log.toString(), ksm.log.containsString("KitStand received message: msgHereIsNextAgentKit. Kit" +
								" slot is " + KitPosition.POSITION1));
		
		//Kit 4 is done and will not be inspected yet.
		System.out.println("Kit "+ (4) + " is done");
		this.kr.msgKitIsDone(kits.get(1));
		//This shouldn't do anything since slot three is taken by kit 3.
		this.kr.pickAndExecuteAnAction();
		
		//Kit 5 is done and will not be inspected yet.
		System.out.println("Kit "+ (5) + " is done");
		this.kr.msgKitIsDone(kits.get(2));
		//This shouldn't do anything since slot three is taken by kit 3.
		this.kr.pickAndExecuteAnAction();
		
		kits = kcm.getKits();
		System.out.println("Kit Camera has "+ kits.size() + " kits on Inspection");
		
		//Kit three passes Inspection. 
		System.out.println("Kit passed Inspection");
		this.kr.msgKitIsGood(kits.get(0));
		//Placing next kit on Boat.
		this.kr.pickAndExecuteAnAction();
		
		ksm.kits.remove(kits.get(0));
		kcm.kits.clear();
		
		//Kit 4 will be inspected since its been waiting first.
		this.kr.pickAndExecuteAnAction();
	
		//This shouldn't do anything.
		this.kr.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 5, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 4, kcm.log.size());
		
		kits = kcm.getKits();
		System.out.println("Kit Camera has "+ kits.size() + " kits on Inspection");
		
		//Kit four passes Inspection. 
		System.out.println("Kit passed Inspection");
		this.kr.msgKitIsGood(kits.get(0));
		//Placing next kit on Boat.
		this.kr.pickAndExecuteAnAction();
		
		ksm.kits.remove(kits.get(0));
		kcm.kits.clear();
		
		//Kit 5 will be inspected since its been waiting first.
		this.kr.pickAndExecuteAnAction();

		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 5, kcm.log.size());
		
		//This shouldn't do anything.
		this.kr.pickAndExecuteAnAction();

		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 5, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 5, kcm.log.size());
		
		kits = kcm.getKits();
		System.out.println("Kit Camera has "+ kits.size() + " kits on Inspection");
		
		//Kit five passes Inspection. 
		System.out.println("Kit passed Inspection");
		this.kr.msgKitIsGood(kits.get(0));
		//Placing next kit on Boat.
		this.kr.pickAndExecuteAnAction();
		
		//This shouldn't do anything.
		this.kr.pickAndExecuteAnAction();
		
		ksm.kits.remove(kits.get(0));
		kcm.kits.clear();
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 5, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 5, kcm.log.size());
		
		kits = kcm.getKits();
		System.out.println("Kit Camera has "+ kits.size() + " kits on Inspection");
		kits = ksm.getKits();
		System.out.println("Kit Stand has "+ kits.size() + " kits on stand");
	}
}