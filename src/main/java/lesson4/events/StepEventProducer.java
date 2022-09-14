package lesson4.events;

import java.util.ArrayList;
import java.util.List;

import lesson4.graphic.CellGameField;

public class StepEventProducer {
	
	
	private List<StepEventListener> stepEventListeners=new ArrayList<>();//list
	
	public void addStepEventListener(StepEventListener listener) {
		
		stepEventListeners.add(listener);
	}
	
	public void removeStepEventListener(StepEventListener listener) {
		
		stepEventListeners.remove(listener);
	}
	
	public void fireStepEvent(CellGameField cell,boolean humanIdentifer) {
		
		StepEvent stepEvent=new StepEvent(cell, humanIdentifer);
		
		
		for (StepEventListener stepEventListener : stepEventListeners ) {
			
			stepEventListener.stepEventHappend(stepEvent);	
			
		}
	}
	
	
	

}
