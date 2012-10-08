package gui.ux;

import engine.Agent;
import engine.agents.FeederAgent;
import engine.agents.GantryAgent;
import engine.agents.KitCameraAgent;
import engine.agents.KitRobotAgent;
import engine.agents.KitStandAgent;
import engine.agents.LaneAgent;
import engine.agents.LaneCameraAgent;
import engine.agents.NestAgent;
import engine.agents.PartsRobotAgent;
import engine.interfaces.Nest;
import gui.GuiBoat;
import gui.GuiBoxBelt;
import gui.GuiBubble;
import gui.GuiCamera;
import gui.GuiCheckingStation;
import gui.GuiDiverter;
import gui.GuiFeeder;
import gui.GuiGantryRobot;
import gui.GuiGates;
import gui.GuiKitBoat;
import gui.GuiKitRobot;
import gui.GuiLane;
import gui.GuiNest;
import gui.GuiPartsRobot;
import gui.GuiWave;
import gui.interfaces.Gui;
import gui.locationsimg.Images;
import gui.locationsimg.Locations;
import gui.superclasses.GuiRobot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JPanel;

import structures.Configuration;
import structures.PartType;

/**
 * Contains the primary focus of the Factory simulation, namely all the Factory components.
 * 
 * @author David Tan
 */
public class FactoryPanel extends JPanel implements ActionListener, GuiInterface {
	
	/** Refers to the last update of the FactoryPanel class. */
	private static final long serialVersionUID = 20111127L;
	/** Refers to the internal ID of the FactoryPanel class. */
	private static final String FACTORYPANELID = "TFDZOFAUYCCV";
	
	// --------------- DATA ---------------
	
	/** Represents the parent JFrame in which ControlPanel resides. */
	MainInterface parentFrame;
	/** Represents the class that draws the background. */
	Background currentBackground;
	
	/** Represents whether the ocean has been drained, which disables the robots and boats. */
	private boolean oceanDrained;
	/** Represents whether the ocean is currently draining. */
	private boolean draining;
	/** Represents whether the ocean is currently filling. */
	private boolean filling;
	/** Represents whether the robots are in revolt. */
	private boolean revolt;
	
	/** Represents the wave object that will wash over the factory. */
	private GuiWave guiWave;
	
	/** Represents the GuiGates for the Top */
	private GuiGates guiGates;
	
	/** ArrayList that represents all GUI objects on the FactoryPanel. */
	private ArrayList<Gui> guiList;
	/** ArrayList that represents all agent objects on the FactoryPanel. */
	private ArrayList<Agent> agentList;
	/** ArrayList that represents all GUI object names on the Factory Panel. */
	private ArrayList<String> guiNames;

	// --------------- CONSTRUCTOR ---------------
	
	/**
	 * Public constructor for FactoryPanel. Initialises everything required to start the actual
	 * Factory simulation, and waits for a command from the ControlPanel to start the agent
	 * threads. By itself, the FactoryPanel does nothing.
	 * 
	 * @param mainInterface Provides reference to parent JFrame
	 */
	public FactoryPanel(MainInterface mainInterface) {
		parentFrame = mainInterface;
		setPreferredSize(new Dimension(1000, 700));
		currentBackground = new Background();
		
		// Initialisation of some default settings.
		oceanDrained = false;
		draining = false;
		filling = false;
		revolt = false;
		guiWave = null;
		guiGates = new GuiGates();
		//this.addMouseListener(guiGates);
		// Initialisation of the two array lists.
		guiList = new ArrayList<Gui>();
		agentList = new ArrayList<Agent>();
		guiNames = new ArrayList<String>();
		
		// Initialisation of all GUI agents and adding them to a list of GUI agents.
		GuiBoat guiArrivalBoat = new GuiBoat();
		guiList.add(guiArrivalBoat); guiNames.add("Arrival Boat");
		GuiKitBoat guiDepartureBoat = new GuiKitBoat();
		guiList.add(guiDepartureBoat); guiNames.add("Departure Boat");
		
					// ------ GUI FEEDER SECTION ------
		GuiGantryRobot guiGantry = new GuiGantryRobot(Locations.gantryRobotX, Locations.gantryRobotY); 
		
		GuiDiverter guiDiverterAB = new GuiDiverter(Locations.diverterABX, Locations.diverterABY);
		GuiFeeder guiFeederAB = new GuiFeeder(Locations.feederABX, Locations.feederABY);
		GuiLane guiLaneTopA = new GuiLane(Locations.laneAX, Locations.laneAY);
		GuiLane guiLaneBottomB = new GuiLane(Locations.laneBX, Locations.laneBY);

		guiList.add(guiLaneTopA); guiList.add(guiLaneBottomB);
		guiList.add(guiDiverterAB); guiList.add(guiFeederAB);
		guiNames.add("Lane A"); guiNames.add("Lane B");
		guiNames.add("Diverter AB"); guiNames.add("Feeder AB");
		
		GuiDiverter guiDiverterCD = new GuiDiverter(Locations.diverterCDX, Locations.diverterCDY);
		GuiFeeder guiFeederCD = new GuiFeeder(Locations.feederCDX, Locations.feederCDY);
		GuiLane guiLaneTopC = new GuiLane(Locations.laneCX, Locations.laneCY);
		GuiLane guiLaneBottomD = new GuiLane(Locations.laneDX, Locations.laneDY);

		guiList.add(guiLaneTopC); guiList.add(guiLaneBottomD);
		guiList.add(guiDiverterCD); guiList.add(guiFeederCD);
		guiNames.add("Lane C"); guiNames.add("Lane D");
		guiNames.add("Diverter CD"); guiNames.add("Feeder CD");
		
		GuiDiverter guiDiverterEF = new GuiDiverter(Locations.diverterEFX, Locations.diverterEFY);
		GuiFeeder guiFeederEF = new GuiFeeder(Locations.feederEFX, Locations.feederEFY);
		GuiLane guiLaneTopE = new GuiLane(Locations.laneEX, Locations.laneEY);
		GuiLane guiLaneBottomF = new GuiLane(Locations.laneFX, Locations.laneFY);

		guiList.add(guiLaneTopE); guiList.add(guiLaneBottomF);
		guiList.add(guiDiverterEF); guiList.add(guiFeederEF);
		guiNames.add("Lane E"); guiNames.add("Lane F");
		guiNames.add("Diverter EF"); guiNames.add("Feeder EF");
		
		GuiDiverter guiDiverterGH = new GuiDiverter(Locations.diverterGHX, Locations.diverterGHY);
		GuiFeeder guiFeederGH = new GuiFeeder(Locations.feederGHX, Locations.feederGHY);
		GuiLane guiLaneTopG = new GuiLane(Locations.laneGX, Locations.laneGY);
		GuiLane guiLaneBottomH = new GuiLane(Locations.laneHX, Locations.laneHY);

		guiList.add(guiLaneTopG); guiList.add(guiLaneBottomH);
		guiList.add(guiDiverterGH); guiList.add(guiFeederGH);
		guiNames.add("Lane G"); guiNames.add("Lane H");
		guiNames.add("Diverter GH"); guiNames.add("Feeder GH");
		
					// ------ GUI PARTS ROBOT SECTION ------
		GuiPartsRobot guiPartsRobot = new GuiPartsRobot(Locations.partsRobotX,Locations.partsRobotY);
		GuiCheckingStation guiCheckingStation = new GuiCheckingStation(Locations.kitCradleX, Locations.kitCradleY);
		
		GuiNest guiNestA = new GuiNest(Locations.nestAX, Locations.nestAY);
		GuiNest guiNestB = new GuiNest(Locations.nestBX, Locations.nestBY);
		GuiCamera guiPartsCameraAB = new GuiCamera(Locations.cameraNestAX,Locations.cameraNestAY);
		
		GuiNest guiNestC = new GuiNest(Locations.nestCX, Locations.nestCY);
		GuiNest guiNestD = new GuiNest(Locations.nestDX, Locations.nestDY);
		GuiCamera guiPartsCameraCD = new GuiCamera(Locations.cameraNestCDX,Locations.cameraNestCDY);
		
		GuiNest guiNestE = new GuiNest(Locations.nestEX, Locations.nestEY);
		GuiNest guiNestF = new GuiNest(Locations.nestFX, Locations.nestFY);
		GuiCamera guiPartsCameraEF = new GuiCamera(Locations.cameraNestEFX,Locations.cameraNestEFY);
		
		GuiNest guiNestG = new GuiNest(Locations.nestGX, Locations.nestGY);
		GuiNest guiNestH = new GuiNest(Locations.nestHX, Locations.nestHY);
		GuiCamera guiPartsCameraGH = new GuiCamera(Locations.cameraNestGHX,Locations.cameraNestGHY);
		
		guiList.add(guiCheckingStation);
		guiList.add(guiNestA); guiList.add(guiNestB);
		guiList.add(guiNestC); guiList.add(guiNestD);
		guiList.add(guiNestE); guiList.add(guiNestF);
		guiList.add(guiNestG); guiList.add(guiNestH);
		guiNames.add("Checking Station");
		guiNames.add("Nest A"); guiNames.add("Nest B");
		guiNames.add("Nest C"); guiNames.add("Nest D");
		guiNames.add("Nest E"); guiNames.add("Nest F");
		guiNames.add("Nest G"); guiNames.add("Nest H");
		
		guiList.add(guiPartsCameraAB);	guiList.add(guiPartsCameraCD);
		guiList.add(guiPartsCameraEF);  guiList.add(guiPartsCameraGH);
		guiNames.add("Parts Camera AB"); guiNames.add("Parts Camera CD");
		guiNames.add("Parts Camera EF"); guiNames.add("Parts Camera GH");
		
					// ------ GUI KIT ROBOT SECTION ------
		GuiKitRobot guiKitRobot = new GuiKitRobot(Locations.kitRobotX, Locations.kitRobotY);
		GuiCamera guiKitCamera = new GuiCamera(Locations.cameraCheckingStationX, Locations.cameraCheckingStationY);
		GuiBoxBelt guiBoxBelt = new GuiBoxBelt(Locations.boxBeltX, Locations.boxBeltY);
		
		guiList.add(guiBoxBelt); guiList.add(guiKitCamera);
		guiNames.add("Box Belt"); guiNames.add("Kit Camera");
		
					// ------ ROBOTS SECTION ------
		guiList.add(guiGantry); guiList.add(guiPartsRobot); guiList.add(guiKitRobot);
		guiNames.add("Gantry Robot"); guiNames.add("Parts Robot"); guiNames.add("Kit Robot");
		
		// Initialisation of all engine agents and adding them to a list of all engine agents. 
		
					// ------ ENGINE FEEDER SECTION ------
		GantryAgent gantryAgent = new GantryAgent("GantryRobot");
		agentList.add(gantryAgent); // 0
		
		FeederAgent feederAgentAB = new FeederAgent("FeederAgentAB");
		LaneAgent laneAgentA = new LaneAgent("LaneAgentA", 20);
		LaneAgent laneAgentB = new LaneAgent("LaneAgentB", 20);
		
		agentList.add(feederAgentAB); // 1
		agentList.add(laneAgentA); agentList.add(laneAgentB); // 2, 3
		
		FeederAgent feederAgentCD = new FeederAgent("FeederAgentCD");
		LaneAgent laneAgentC = new LaneAgent("LaneAgentC", 20);
		LaneAgent laneAgentD = new LaneAgent("LaneAgentD", 20);
		
		agentList.add(feederAgentCD); // 4
		agentList.add(laneAgentC); agentList.add(laneAgentD); // 5, 6
		
		FeederAgent feederAgentEF = new FeederAgent("FeederAgentEF");
		LaneAgent laneAgentE = new LaneAgent("LaneAgentE", 20);
		LaneAgent laneAgentF = new LaneAgent("LaneAgentF", 20);
		
		agentList.add(feederAgentEF); // 7
		agentList.add(laneAgentE); agentList.add(laneAgentF); // 8, 9
		
		FeederAgent feederAgentGH = new FeederAgent("FeederAgentGH");
		LaneAgent laneAgentG = new LaneAgent("LaneAgentG", 20);
		LaneAgent laneAgentH = new LaneAgent("LaneAgentH", 20);
		
		agentList.add(feederAgentGH); // 10
		agentList.add(laneAgentG); agentList.add(laneAgentH); // 11, 12
		
					// ------ ENGINE PARTS ROBOT SECTION ------
		NestAgent nestAgentA = new NestAgent("NestA", 8);
		NestAgent nestAgentB = new NestAgent("NestB", 8);
		NestAgent nestAgentC = new NestAgent("NestC", 8);
		NestAgent nestAgentD = new NestAgent("NestD", 8);
		NestAgent nestAgentE = new NestAgent("NestE", 8);
		NestAgent nestAgentF = new NestAgent("NestF", 8);
		NestAgent nestAgentG = new NestAgent("NestG", 8);
		NestAgent nestAgentH = new NestAgent("NestH", 8);
			ArrayList<Nest> nestList = new ArrayList<Nest>();
			nestList.add(nestAgentA); nestList.add(nestAgentB);
			nestList.add(nestAgentC); nestList.add(nestAgentD);
			nestList.add(nestAgentE); nestList.add(nestAgentF);
			nestList.add(nestAgentG); nestList.add(nestAgentH);
		LaneCameraAgent partsCameraAgentAB = new LaneCameraAgent("CameraAB");  
		LaneCameraAgent partsCameraAgentCD = new LaneCameraAgent("CameraCD"); 
		LaneCameraAgent partsCameraAgentEF = new LaneCameraAgent("CameraEF");  
		LaneCameraAgent partsCameraAgentGH = new LaneCameraAgent("CameraGH");  
		PartsRobotAgent partsRobotAgent = new PartsRobotAgent("PartsRobot", nestList);
		
		agentList.add(nestAgentA); agentList.add(nestAgentB); // 13, 14
		agentList.add(nestAgentC); agentList.add(nestAgentD); // 15, 16
		agentList.add(nestAgentE); agentList.add(nestAgentF); // 17, 18
		agentList.add(nestAgentG); agentList.add(nestAgentH); // 19, 20
		agentList.add(partsCameraAgentAB); agentList.add(partsCameraAgentCD); // 21, 22 
		agentList.add(partsCameraAgentEF); agentList.add(partsCameraAgentGH); // 23, 24
		agentList.add(partsRobotAgent); // 25
		
					// ------ ENGINE KIT ROBOT SECTION ------
		KitStandAgent kitStandAgent = new KitStandAgent("KitStand");
		KitRobotAgent kitRobotAgent = new KitRobotAgent("KitRobot");
		KitCameraAgent kitCameraAgent = new KitCameraAgent("KitCamera");
		
		agentList.add(kitStandAgent); agentList.add(kitRobotAgent); // 26, 27
		agentList.add(kitCameraAgent); // 28
		
		// Setting all dependencies for all engine agents.
		
					// ------ FEEDER DEPENDENCIES ------
		feederAgentAB.setGantryAgent(gantryAgent);
		feederAgentAB.setTopLaneAgent(laneAgentA);
		feederAgentAB.setBottomLaneAgent(laneAgentB);
		
		feederAgentCD.setGantryAgent(gantryAgent);
		feederAgentCD.setTopLaneAgent(laneAgentC);
		feederAgentCD.setBottomLaneAgent(laneAgentD);
		
		feederAgentEF.setGantryAgent(gantryAgent);
		feederAgentEF.setTopLaneAgent(laneAgentE);
		feederAgentEF.setBottomLaneAgent(laneAgentF);
		
		feederAgentGH.setGantryAgent(gantryAgent);
		feederAgentGH.setTopLaneAgent(laneAgentG);
		feederAgentGH.setBottomLaneAgent(laneAgentH);
		
					// ------ LANE DEPENDENCIES ------
		laneAgentA.setFeeder(feederAgentAB);
		laneAgentA.setNest(nestAgentA);
		laneAgentB.setFeeder(feederAgentAB);
		laneAgentB.setNest(nestAgentB);
		
		laneAgentC.setFeeder(feederAgentCD);
		laneAgentC.setNest(nestAgentC);
		laneAgentD.setFeeder(feederAgentCD);
		laneAgentD.setNest(nestAgentD);
		
		laneAgentE.setFeeder(feederAgentEF);
		laneAgentE.setNest(nestAgentE);
		laneAgentF.setFeeder(feederAgentEF);
		laneAgentF.setNest(nestAgentF);
		
		laneAgentG.setFeeder(feederAgentGH);
		laneAgentG.setNest(nestAgentG);
		laneAgentH.setFeeder(feederAgentGH);
		laneAgentH.setNest(nestAgentH);
		
					// ------ NEST DEPENDENCIES ------
		nestAgentA.setLane(laneAgentA);
		nestAgentA.setLaneCamera(partsCameraAgentAB);
		nestAgentA.setPartsRobot(partsRobotAgent);
		nestAgentB.setLane(laneAgentB);
		nestAgentB.setLaneCamera(partsCameraAgentAB);
		nestAgentB.setPartsRobot(partsRobotAgent);
		
		nestAgentC.setLane(laneAgentC);
		nestAgentC.setLaneCamera(partsCameraAgentCD);
		nestAgentC.setPartsRobot(partsRobotAgent);
		nestAgentD.setLane(laneAgentD);
		nestAgentD.setLaneCamera(partsCameraAgentCD);
		nestAgentD.setPartsRobot(partsRobotAgent);
		
		nestAgentE.setLane(laneAgentE);
		nestAgentE.setLaneCamera(partsCameraAgentEF);
		nestAgentE.setPartsRobot(partsRobotAgent);
		nestAgentF.setLane(laneAgentF);
		nestAgentF.setLaneCamera(partsCameraAgentEF);
		nestAgentF.setPartsRobot(partsRobotAgent);
		
		nestAgentG.setLane(laneAgentG);
		nestAgentG.setLaneCamera(partsCameraAgentGH);
		nestAgentG.setPartsRobot(partsRobotAgent);
		nestAgentH.setLane(laneAgentH);
		nestAgentH.setLaneCamera(partsCameraAgentGH);
		nestAgentH.setPartsRobot(partsRobotAgent);
		
					// ------ PARTS CAMERA DEPENDENCIES ------
		partsCameraAgentAB.setNest1(nestAgentA);
		partsCameraAgentAB.setNest2(nestAgentB);
		partsCameraAgentAB.setPartsRobot(partsRobotAgent);
		
		partsCameraAgentCD.setNest1(nestAgentC);
		partsCameraAgentCD.setNest2(nestAgentD);
		partsCameraAgentCD.setPartsRobot(partsRobotAgent);
		
		partsCameraAgentEF.setNest1(nestAgentE);
		partsCameraAgentEF.setNest2(nestAgentF);
		partsCameraAgentEF.setPartsRobot(partsRobotAgent);
		
		partsCameraAgentGH.setNest1(nestAgentG);
		partsCameraAgentGH.setNest2(nestAgentH);
		partsCameraAgentGH.setPartsRobot(partsRobotAgent);
		
					// ------ PARTS ROBOT DEPENDENCIES ------
		partsRobotAgent.setKitStand(kitStandAgent);
		
					// ------ KIT STAND DEPENDENCIES ------
		kitStandAgent.setKitRobot(kitRobotAgent);
		kitStandAgent.setPartsRobot(partsRobotAgent);
		
					// ------ KIT ROBOT DEPENDENCIES ------
		kitRobotAgent.setKitCamera(kitCameraAgent);
		kitRobotAgent.setKitStand(kitStandAgent);
		
					// ------ KIT CAMERA DEPENDENCIES ------
		kitCameraAgent.setKitRobot(kitRobotAgent);
		
		// Linking all engine agents to their GUI agent components.
		
					// ------ FEEDER SECTION ------
		gantryAgent.setGuiGantryRobot(guiGantry);
		gantryAgent.setGuiBoat(guiArrivalBoat);
		
		feederAgentAB.setGuiFeeder(guiFeederAB);
		feederAgentAB.setGuiDiverter(guiDiverterAB);
		laneAgentA.setGuiLane(guiLaneTopA);
		laneAgentB.setGuiLane(guiLaneBottomB);
		
		feederAgentCD.setGuiFeeder(guiFeederCD);
		feederAgentCD.setGuiDiverter(guiDiverterCD);
		laneAgentC.setGuiLane(guiLaneTopC);
		laneAgentD.setGuiLane(guiLaneBottomD);
		
		feederAgentEF.setGuiFeeder(guiFeederEF);
		feederAgentEF.setGuiDiverter(guiDiverterEF);
		laneAgentE.setGuiLane(guiLaneTopE);
		laneAgentF.setGuiLane(guiLaneBottomF);
		
		feederAgentGH.setGuiFeeder(guiFeederGH);
		feederAgentGH.setGuiDiverter(guiDiverterGH);
		laneAgentG.setGuiLane(guiLaneTopG);
		laneAgentH.setGuiLane(guiLaneBottomH);
		
					// ------ PARTS ROBOT SECTION ------
		nestAgentA.setGuiNest(guiNestA);
		nestAgentB.setGuiNest(guiNestB);
		nestAgentC.setGuiNest(guiNestC);
		nestAgentD.setGuiNest(guiNestD);
		nestAgentE.setGuiNest(guiNestE);
		nestAgentF.setGuiNest(guiNestF);
		nestAgentG.setGuiNest(guiNestG);
		nestAgentH.setGuiNest(guiNestH);
		
		partsCameraAgentAB.setGuiCamera(guiPartsCameraAB);
		partsCameraAgentCD.setGuiCamera(guiPartsCameraCD);
		partsCameraAgentEF.setGuiCamera(guiPartsCameraEF);
		partsCameraAgentGH.setGuiCamera(guiPartsCameraGH);
		partsRobotAgent.setGuiPartsRobot(guiPartsRobot);
		
					// ------ KIT ROBOT SECTION ------
		kitStandAgent.setGuiCheckingStation(guiCheckingStation);
		kitRobotAgent.setGuiBoxBelt(guiBoxBelt);
		kitRobotAgent.setGuiKitBoat(guiDepartureBoat);
		kitRobotAgent.setGuiKitRobot(guiKitRobot);
		kitCameraAgent.setGuiCheckingStation(guiKitCamera);
		kitCameraAgent.setGuiCheckStation(guiCheckingStation);
		
		// Linking all GUI agents to their parent engine agents.
		guiArrivalBoat.setAgent(gantryAgent);
		guiDepartureBoat.setAgent(kitRobotAgent);
		
					// ------ FEEDER SECTION ------
		guiGantry.setAgent(gantryAgent);
		
		guiFeederAB.setAgent(feederAgentAB);
		guiDiverterAB.setAgent(feederAgentAB);
		guiLaneTopA.setAgent(laneAgentA);
		guiLaneBottomB.setAgent(laneAgentB);
		
		guiFeederCD.setAgent(feederAgentCD);
		guiDiverterCD.setAgent(feederAgentCD);
		guiLaneTopC.setAgent(laneAgentC);
		guiLaneBottomD.setAgent(laneAgentD);
		
		guiFeederEF.setAgent(feederAgentEF);
		guiDiverterEF.setAgent(feederAgentEF);
		guiLaneTopE.setAgent(laneAgentE);
		guiLaneBottomF.setAgent(laneAgentF);
		
		guiFeederGH.setAgent(feederAgentGH);
		guiDiverterGH.setAgent(feederAgentGH);
		guiLaneTopG.setAgent(laneAgentG);
		guiLaneBottomH.setAgent(laneAgentH);
		
					// ------ PARTS ROBOT SECTION ------
		guiNestA.setAgent(nestAgentA);
		guiNestB.setAgent(nestAgentB);
		guiPartsCameraAB.setAgent(partsCameraAgentAB);
		
		guiNestC.setAgent(nestAgentC);
		guiNestD.setAgent(nestAgentD);
		guiPartsCameraCD.setAgent(partsCameraAgentCD);
		
		guiNestE.setAgent(nestAgentE);
		guiNestF.setAgent(nestAgentF);
		guiPartsCameraEF.setAgent(partsCameraAgentEF);
		
		guiNestG.setAgent(nestAgentG);
		guiNestH.setAgent(nestAgentH);
		guiPartsCameraGH.setAgent(partsCameraAgentGH);
		
		guiPartsRobot.setAgent(partsRobotAgent);
		
					// ------ KIT ROBOT SECTION ------
		guiCheckingStation.SetKitStand(kitStandAgent);
		guiBoxBelt.setAgent(kitRobotAgent);
		guiKitRobot.setAgent(kitRobotAgent);
		guiKitCamera.setAgent(kitCameraAgent);
		
		stopThreads();
	}

	// --------------- GRAPHICS ---------------
	
	/**
	 * First paints the background with a white rectangle so as to mark the current region being
	 * drawn, and then iterates through the guiList ArrayList and calls the updatelocation and
	 * draw functions of each element in the ArrayList.
	 * 
	 * @param g Graphics component
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		currentBackground.updateLocation();
		currentBackground.draw(g2);
		g2.drawImage(Images.OompaLand.getImage(), Locations.oompaLandX, Locations.oompaLandY, null);
		g2.drawImage(Images.FlashIdle.getImage(), Locations.cameraNestAX, Locations.cameraNestAY, null);
		g2.drawImage(Images.FlashIdle.getImage(), Locations.cameraNestCDX, Locations.cameraNestCDY, null);
		g2.drawImage(Images.FlashIdle.getImage(), Locations.cameraNestEFX, Locations.cameraNestEFY, null);
		g2.drawImage(Images.FlashIdle.getImage(), Locations.cameraNestGHX, Locations.cameraNestGHY, null);
		g2.drawImage(Images.FlashIdle.getImage(), Locations.cameraCheckingStationX, Locations.cameraCheckingStationY, null);
		if (draining) {
			if (currentBackground.getDoneDraining()) {
				oceanDrained = true;
				draining = false;
				for (Gui current : guiList) {
					if (oceanDrained && (current.checkPower() == Gui.Power.ON)) {
						current.disable();
					}
				}
			}
		}
		if (filling) {
			if (currentBackground.getDoneFilling()) {
				oceanDrained = false;
				filling = false;
				for (Gui current : guiList) {
					current.enable();
				}
			}
		}
		for (Gui current : guiList) {
			current.updateLocation();
			current.draw(g2);
		}
		g2.drawImage(Images.TunnelBottom.getImage(), Locations.tunnelBottomLeftX, Locations.tunnelBottomLeftY, null);
		g2.drawImage(Images.TunnelBottom.getImage(), Locations.tunnelBottomRightX, Locations.tunnelBottomRightY, null);
		g2.drawImage(Images.TunnelUp.getImage(), Locations.tunnelTopLeftX, Locations.tunnelTopLeftY, null);
		g2.drawImage(Images.TunnelUp.getImage(), Locations.tunnelTopRightX, Locations.tunnelTopRightY, null);
		g2.drawImage(Images.NextToTunnel.getImage(), Locations.nextToTunnelX, Locations.nextToTunnelY, null);
		for (Gui current : guiList) {
			try {
				LinkedList<GuiBubble> tempList = ((GuiLane) current).getBubbles();
				for (GuiBubble currentBubble : tempList) {
					currentBubble.draw(g2);
				}
			}
			catch (ClassCastException e) { }
			try {
				LinkedList<GuiBubble> tempList = ((GuiNest) current).getBubbles();
				for (GuiBubble currentBubble : tempList) {
					currentBubble.draw(g2);
				}
			}
			catch (ClassCastException e) { }
		}
		if (guiWave != null) {
			guiWave.updateLocation();
			guiWave.draw(g2);
			for (Gui current : guiList) {
				current.disable();
			}
		}
		g2.drawImage(Images.Background2.getImage(), null, null);
		guiGates.updateLocation();
		guiGates.draw(g2);
	}

	// --------------- ACTIONS ---------------
	
	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}
	
	/** Starts all agents in the FactoryPanel and begins the simulation. Should only be called once. */
	public void startThreads() {
		guiGates.openGates();
		for (int i = 0; i < agentList.size(); i++) {
			agentList.get(i).startThread();
		}
	}
	
	/** Pause all agents in the FactoryPanel, called by stop() in the MainInterface class. */
	public void pauseThreads() {
		for (int i = 0; i < agentList.size(); i++) {
			agentList.get(i).pauseThread();
		}
	}
	
	/** Stop all agents in the FactoryPanel, should only be used internally. */
	public void stopThreads() {
		for (int i = 0; i < agentList.size(); i++) {
			agentList.get(i).stopThread();
		}
	}
	
	/**
	 * Drains the chocolate ocean in which all GUI agents operate. Doing so shuts down every GUI
	 * agent and removes the ability to enable them.
	 */
	public void drain() {
		if (draining ^ filling) {
			return;
		}
		if (!oceanDrained) {
			currentBackground.drainOcean();
			draining = true;
		} else {
			currentBackground.fillOcean();
			filling = true;
		}
	}
	
	/** Draws a wave that completely cripples the factory and forces a reset. */
	public void wave() {
		if(guiWave != null)
		{
			parentFrame.reset();
		}
		else
		{
			guiWave = new GuiWave();
		}
	}
	
	/** Sends all candies in the nests and lanes into bubbles and floats them away. */
	public void bubble() {
		for (Gui current : guiList) {
			try { ((GuiLane) current).bubbleCandies(); }
			catch (ClassCastException e) { }
		}
	}
	
	/** 
	 * Causes all the Oompa Loompa robots to revolt and stop working; all of the robots will then move
	 * randomly around on the screen and will continue to do so until the Factory simulation is completely
	 * reset.
	 */
	public void revolt() {
		if (!revolt) {
			for (Gui current : guiList) {
				try { ((GuiRobot) current).revolt(); }
				catch (ClassCastException e) { }
			}
			revolt = true;
		} else {
			for (Gui current : guiList) {
				try { ((GuiRobot) current).endRevolt(); }
				catch (ClassCastException e) { }
			}
			revolt = false;
		}
	}
	
	/**
	 * Sets a new kit configuration for use by the kitRobotAgent.
	 * 
	 * @param param Kit configuration
	 */
	public void setConfiguration(HashMap <PartType, Integer> param) {
		HashMap <PartType, Integer> tempMap = new HashMap <PartType, Integer>();
		for (Map.Entry<PartType, Integer> entry : param.entrySet()) {
			tempMap.put(entry.getKey(), entry.getValue());
		}
		ArrayList <PartType> tempArray = new ArrayList <PartType>();
		for (PartType current : UserData.returnArrangement()) {
			if (current != PartType.NOTYPE) {
				tempArray.add(current);
			}
		}
		((KitRobotAgent) agentList.get(27)).msgHereIsKitConfiguration(new Configuration(tempMap, tempArray));
	}
	
	/** Returns the list containing all GUI agents. */
	public ArrayList<Gui> returnGuiList() {
		return guiList;
	}
	
	/** Returns the list containing all GUI agents. */
	public ArrayList<Agent> returnAgentList() {
		return agentList;
	}
	
	/** Returns the list containing all GUI agent names. */
	public ArrayList<String> returnGuiNamesList() {
		return guiNames;
	}
	
	@Override
	public String returnPanelID() {
		return FACTORYPANELID;
	}
}
