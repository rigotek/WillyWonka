package engine.test.tbaetz;

import java.util.ArrayList;
import java.util.TimerTask;

import structures.AgentPart;
import engine.agents.LaneCameraAgent;
import engine.test.mock.MockNest;
import engine.test.mock.MockPartsRobot;
import junit.framework.TestCase;

public class LaneCameraTests extends TestCase{
	public LaneCameraAgent laneCamera;
	
	public void testMsgAnotherPart(){
		laneCamera = new LaneCameraAgent("lane camera");
		MockNest nest1 = new MockNest("nest1");
		MockNest nest2 = new MockNest("nest2");
		laneCamera.setNest1(nest1);
		laneCamera.setNest2(nest2);
		laneCamera.msgIHaveAnotherPart(2, nest1);
		laneCamera.pickAndExecuteAnAction();
		laneCamera.msgIHaveAnotherPart(4, nest2);
		laneCamera.pickAndExecuteAnAction();
		assertEquals("The camera should have received updated parts from both nests and have two messages in its log",2,laneCamera.log.size());
	}
	public void testMsgTimerFired(){
		laneCamera = new LaneCameraAgent("lane camera");
		laneCamera.msgTimerFired();
		assertEquals("The camera should receive the message from the timer and update the log",1,laneCamera.log.size());
		}
		
	public void testMsgDoneTakingPicture(){
		laneCamera = new LaneCameraAgent("lane camera");
		MockNest nest1 = new MockNest("nest1");
		MockNest nest2 = new MockNest("nest2");
		laneCamera.setNest1(nest1);
		laneCamera.setNest2(nest2);
		laneCamera.msgDoneTakingPicture();
		assertEquals("The camera should receive the GUI callback and update the log", 1,laneCamera.log.size());	
		
		laneCamera.pickAndExecuteAnAction();
		assertEquals("Nest 1 should have received a message from the camera",1,nest1.log.size());
		assertEquals("Nest 2 should have received a message from the camera",1,nest2.log.size());
		
		}
	public void testMsgHereAreParts(){
		laneCamera = new LaneCameraAgent("lane camera");
		MockNest nest1 = new MockNest("nest1");
		MockNest nest2 = new MockNest("nest2");
		MockPartsRobot robot = new MockPartsRobot("robot");
		AgentPart part1 = new AgentPart(null);
		AgentPart part2 = new AgentPart(null);
		AgentPart part3 = new AgentPart(null);
		AgentPart part4 = new AgentPart(null);
		AgentPart part5 = new AgentPart(null);
		AgentPart part6 = new AgentPart(null);
		ArrayList<AgentPart> parts1 = new ArrayList();
		ArrayList<AgentPart> parts2 = new ArrayList();
		parts1.add(part1);
		parts1.add(part2);
		parts2.add(part3);
		parts2.add(part4);
		parts2.add(part5);
		parts2.add(part6);
		laneCamera.setNest1(nest1);
		laneCamera.setNest2(nest2);
		laneCamera.setPartsRobot(robot);
		laneCamera.msgHereAreParts(parts1, nest1);
		laneCamera.msgHereAreParts(parts2, nest2);
		
		assertEquals("The camera should receive both nest lists and have updated the log", 2, laneCamera.log.size());
		assertEquals("These lists should be equal", parts1, laneCamera.getParts1());
		assertEquals("These lists should be equal", parts2, laneCamera.getParts2());
		
		laneCamera.pickAndExecuteAnAction();
		
		assertEquals("The parts robot should receive two messages containing both lists", 2, robot.log.size());
	}
	public void testResettingCamera(){
		laneCamera = new LaneCameraAgent("lane camera");
		MockNest nest1 = new MockNest("nest1");
		assertEquals("The timer task should be null",null,laneCamera.getTask());
		laneCamera.msgIHaveAnotherPart(2, nest1);
		laneCamera.pickAndExecuteAnAction();
		TimerTask temp = laneCamera.getTask();
		assertNotNull("The timer should have been set",temp);
		laneCamera.msgIHaveAnotherPart(3, nest1);
		laneCamera.pickAndExecuteAnAction();
		assertNotSame("The timer tasks should not be equal because the timer has been reset",temp,laneCamera.getTask());
	}
	
}
