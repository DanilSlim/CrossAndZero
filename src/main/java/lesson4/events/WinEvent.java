package lesson4.events;

import java.util.EventObject;

public class WinEvent extends EventObject {

	private static final long serialVersionUID = -3612147979800994102L;

	public WinEvent(Object source) {
		super(source);
		
	}

}
