package gui.interfaces;

import gui.holdable.Holdable;

public interface GuiLaneInterface {
  
 // Agent call from backend
 // Takes a candy from the deflector and places it in the last position of the CandyQueue
 public void DoMoveToLane(Holdable p);

 // Agent call from backend
 // Moves the candy at the front of the queue into the Nest
 public void DoRemoveFromLane(Holdable p);

 
}