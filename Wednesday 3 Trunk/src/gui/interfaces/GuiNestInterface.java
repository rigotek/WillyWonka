package gui.interfaces;

import gui.holdable.Holdable;

// GuiNestInterface

// Author: Christian Vanderwall

public interface GuiNestInterface {
  
 // Agent call from backend
 // Takes a candy from the lane and places it in the last position of the CandyQueue
 public void DoPutInNest(Holdable p);

 // Agent call from backend
 // Moves the candy at the front of the queue
 public void DoRemoveFromNest(Holdable p);
 
}