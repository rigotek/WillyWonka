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
 * This test will test the KitRobot, handling one Kit on its slots..
 * @author Alberto
 *
 */

public class KitRobotAgentTestWithOneKit extends TestCase {
	KitRobotAgent kr;
	//@Test
	public void testKitRobotWithOneKits() {
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
		//Placing kit 1 on stand
		System.out.println("Kit 1 is placed on stand");
		this.kr.msgHereIsKitConfiguration(c1);
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 0, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 0, kcm.log.size());
		
		this.kr.pickAndExecuteAnAction();

		//This should do anything
		this.kr.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 1, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 0, kcm.log.size());
		
		
		LinkedList<AgentKit> kits = ksm.getKits();
		System.out.println("Kit Stand has "+ kits.size() + " kits on stand");
		
		assertTrue(
				"Mock KitStand should have received message about new configuration for kit. Event log: "
						+ ksm.log.toString(), ksm.log.containsString("KitStand received message: msgHereIsNextAgentKit. Kit" +
								" slot is " + KitPosition.POSITION1));
		
		//Kit one is done and will be inspected
		System.out.println("Kit "+ (1) + " is done");
		this.kr.msgKitIsDone(kits.get(0));
		this.kr.pickAndExecuteAnAction();
		
		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 1, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have one event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 1, kcm.log.size());
		
		assertTrue(
				"Mock KitCamera should have received message about a kit to inspect. Event log: "
						+ kcm.log.toString(), kcm.log.containsString("KitCamera received message: msgInspectKit. Kit is on slot "+
				KitPosition.POSITION3));
		
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
		

		assertEquals(
				"Mock KitStand should have an empty event log before the KitStand's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ ksm.log.toString(), 1, ksm.log.size());
		
		assertEquals(
				"Mock KitCamera should have an empty event log before the KitCamera's scheduler is called. Instead, " +
				"the mock KitStand's event log reads: "+ kcm.log.toString(), 1, kcm.log.size());
		
		kits = kcm.getKits();
		System.out.println("Kit Camera has "+ kits.size() + " kits on Inspection");
		kits = ksm.getKits();
		System.out.println("Kit Stand has "+ kits.size() + " kits on stand");
	}
}