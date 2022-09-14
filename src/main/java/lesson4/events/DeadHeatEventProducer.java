package lesson4.events;

import java.util.ArrayList;
import java.util.List;

public class DeadHeatEventProducer {
	
	
	private List<DeadHeatEventListener> deadHeatEventListeners=new ArrayList<>();
	
	public void addDeadHeatEventListener(DeadHeatEventListener listener) {
		
		deadHeatEventListeners.add(listener);
	}
	
	public void removeDeadHeatEventListener(DeadHeatEventListener listener) {
		
		deadHeatEventListeners.remove(listener);
	}
	
	public void fireDeadHeatEvent() {
		
		DeadHeatEvent event=new DeadHeatEvent("DeadHeatEvent");
		
		for(DeadHeatEventListener listener:deadHeatEventListeners) {
			
			listener.deadHeatEventHappend(event);
		}
		
	}

}
