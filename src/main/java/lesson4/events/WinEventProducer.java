package lesson4.events;

import java.util.ArrayList;
import java.util.List;

public class WinEventProducer {
	
	private List<WinEventListener> winEventListeners=new ArrayList<>();
	
	public void addWinEventListener(WinEventListener  listener) {
		
		winEventListeners.add(listener);
	}
	
	public void removeWinEventListener(WinEventListener listener) {
		
		winEventListeners.remove(listener);
	}
	
	
	public void fireWinEvent() {
		
		WinEvent winEvent=new WinEvent("WinEvent");
		
		for(WinEventListener listener: winEventListeners) {
			
			listener.winEventHappend(winEvent);
		}
	}
	
	

}
