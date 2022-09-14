package lesson4.events;

import java.util.EventObject;

public class DeadHeatEvent extends EventObject {

	private static final long serialVersionUID = -5068164825947233185L;

	public DeadHeatEvent(Object source) {
		
		super(source);
		
	}

}
