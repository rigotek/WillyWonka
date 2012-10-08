package engine.test.mock;

import java.util.List;

import structures.*;
import engine.interfaces.*;
import engine.test.*;

public class MockNest extends MockAgent implements Nest {

	public MockNest(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public EventLog log = new EventLog();

	@Override
	public void msgNextPart(AgentPart p) {
		log.add(new LoggedEvent(
				"Received message msgNextPart from lane that gives the nest part " + p.toString()));
		
	}

	@Override
	public void msgTakingPicture() {
		log.add(new LoggedEvent(
				"Received message msgTakingPicture from lane camera that it wants snapshot of parts in nest"));
		
	}

	@Override
	public void msgINeedTheseParts(List<AgentPart> pts) {
		log.add(new LoggedEvent(
				"Received message msgINeedTheseParts from parts robot that it has decided to take parts "
				+ pts.toString() + " from the nest"));
		
	}
	
	public void msgChangeToThisPart(PartType type) {
		log.add(new LoggedEvent(
				"Received message msgChangeToThisPart from parts robot because it needs part type " +
				type.toString()));
	}

	@Override
	public void msgThesePartsAreBad(List<AgentPart> pts) {
		log.add(new LoggedEvent(
				"Received message msgThesePartsAreBad from lane camera with bad parts " + pts.toString()));
		
	}
	
	public void msgPurgeTheNest() {
		log.add(new LoggedEvent(
				"Received message msgPurgeTheNest from lane to purge the nest"));
	}
	
	
}