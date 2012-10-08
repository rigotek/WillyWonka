package engine.agents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import structures.AgentPart;

import engine.Agent;
import engine.interfaces.LaneCamera;
import engine.interfaces.Nest;
import engine.interfaces.PartsRobot;
import gui.interfaces.GuiCameraInterface;

public class LaneCameraAgent extends Agent implements LaneCamera {

	private List<AgentPart> parts1; //List of parts received by nest1
	private List<AgentPart> parts2; //List of parts received by nest2
	private Nest nest1; //Nest 1
	private Nest nest2; //Nest 2
	private Timer timer = new Timer(); //Controls camera firing
	private TimerTask currentTask; 

	private enum CameraState { //State enum to control camera agent
		RESET, FIRED, DONE, PAUSE, PLAY
	};

	private CameraState state = CameraState.PAUSE;
	private PartsRobot robot; //Parts robot
	private String name; //Robot name
	public ArrayList<String> log = new ArrayList<String>(); //debug log for unit tests
	private GuiCameraInterface camera; //GUI camera
	
	/*The constructor for the camera agent takes in a name parameter and then initialized the agent lists*/
	public LaneCameraAgent(String name) {
		this.name = name;
		parts1 = Collections.synchronizedList(new ArrayList<AgentPart>());
		parts2 = Collections.synchronizedList(new ArrayList<AgentPart>());
	}

	// Message sent from Nest agent after it receives another part
	/*
	 * (non-Javadoc)
	 * 
	 * @see engine.agents.LaneCamera#msgIHaveAnotherPart(int, java.lang.Object)
	 */
	public void msgIHaveAnotherPart(int numParts, Nest nest) {
		print("Received another part message from " + nest.toString());
		log.add("Received another part message from " + nest.toString());
		state = CameraState.RESET;
		stateChanged();
	}

	// Take picture after timer has fired
	/*
	 * (non-Javadoc)
	 * 
	 * @see engine.agents.LaneCamera#msgTimerFired()
	 */
	@Override
	public void msgTimerFired() {
		print("Received message from timer to take picture");
		log.add("Received message from timer to take picture");
		state = CameraState.FIRED;
		stateChanged();
	}

	// Message sent from Nest agent after picture taken
	/*
	 * (non-Javadoc)
	 * 
	 * @see engine.agents.LaneCamera#msgHereAreParts(java.util.ArrayList,
	 * java.lang.Object)
	 */
	public void msgHereAreParts(ArrayList<AgentPart> parts, Nest nest) {
		if (nest == nest1) {
			parts1.addAll(parts);
		} else if (nest == nest2) {
			parts2.addAll(parts);
		}
		print("Received message from " + nest.toString() + " with parts");
		log.add("Received message from " + nest.toString() + " with parts");
		stateChanged();
	}

	// Gui callback
	@Override
	public void msgDoneTakingPicture() {
		print("Received GUI callback");
		log.add("Received GUI callback");
		state = CameraState.PLAY;
		stateChanged();

	}
	/*Scheduler Rules
	1. If either parts list from the nest contains parts, alert the parts robot
	2. If the camera receives a message from the nest that it has received another part, it resets the timer
	3. If the camera receives the GUI callback that the animation has finished, it alerts the nests that a picture has been taken
	4. If the timer fires, the camera alerts the GUI to take a picture
	 */
	public boolean pickAndExecuteAnAction() {
		if (!parts1.isEmpty() || !parts2.isEmpty()){
			callRobot();
			return true;
		}
		else if (state == CameraState.RESET) {
			resetTimer();
			return true;
		} else if (state == CameraState.PLAY) {
			tellNest();
			return true;
		} else if (state == CameraState.FIRED) {
			takePicture();
			return true;
		}
		return false;
	}

	// Messages the Parts Agent robot to pick up parts
	private void callRobot() {
		ArrayList<AgentPart> toremove1 = new ArrayList<AgentPart>();
		for (AgentPart ap : parts1) { //removing any faulty or missing parts from list 1
			if (ap.getFaulty() || ap.partgui == null) {
				print("removing bad part of type 1");
				toremove1.add(ap);
			}
		}
		for(AgentPart ap : toremove1) {
			parts1.remove(ap);
		}
		ArrayList<AgentPart> toremove2 = new ArrayList<AgentPart>();
		for (AgentPart ap : parts2) { //removing any faulty or missing parts from list 2
			if (ap.getFaulty() || ap.partgui == null) {
				print("Removing bad part of type 2");
				toremove2.add(ap);
			}
		}
		for(AgentPart ap : toremove2) {
			parts2.remove(ap);
		}
		print("Giving parts robot lists");
		state = CameraState.PAUSE;
		//Message nest bad parts and robot good parts
		if (!toremove1.isEmpty())
			nest1.msgThesePartsAreBad(toremove1);
		if (!toremove2.isEmpty())
			nest2.msgThesePartsAreBad(toremove2);
		if (!parts1.isEmpty())
			robot.msgIHaveGoodParts(parts1, nest1, this);
		if (!parts2.isEmpty())
			robot.msgIHaveGoodParts(parts2, nest2, this);
		//Clear lists
		parts1.clear();
		parts2.clear();
		toremove1.clear();
		toremove2.clear();
		stateChanged();
	}

	// Messages GUI camera to perform picture animation
	private void takePicture() {
		print("Telling GUI to take picture");
		state = CameraState.PAUSE;
		camera.doTakePicture();
		stateChanged();
	}

	// Resets the timer if the nest receives another part
	private void resetTimer() {
		if (currentTask != null)
			currentTask.cancel();
		currentTask = getTimerTask();
		timer.schedule(currentTask, 3000);
		print("Reset the timer");
		state = CameraState.PAUSE;
		stateChanged();
	}

	// Message nest that a picture has been taken
	private void tellNest() {
		print("Told nests to take picture");
		state = CameraState.PAUSE;
		nest1.msgTakingPicture();
		nest2.msgTakingPicture();
		stateChanged();
	}
	//Helper function that returns a new timer task
	private TimerTask getTimerTask() {
		TimerTask reset = new TimerTask() {
			public void run() {
				msgTimerFired();
			}
		};
		return reset;
	}

	public void setNest1(Nest nest) {
		nest1 = nest;
	}

	public void setNest2(Nest nest) {
		nest2 = nest;
	}

	public void setPartsRobot(PartsRobot robot) {
		this.robot = robot;
	}

	public List<AgentPart> getParts1() {
		return parts1;
	}

	public List<AgentPart> getParts2() {
		return parts2;
	}

	public TimerTask getTask() {
		return currentTask;
	}

	public void setGuiCamera(GuiCameraInterface cam) {
		camera = cam;
	}
	public String toString(){
		return name;
	}
}
