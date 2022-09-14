package lesson4.events;

import java.util.EventObject;

public class StepEvent extends EventObject {

	private static final long serialVersionUID = 5546144355859490981L;
	
	private boolean humanIdentifer; //идентификатор того, что ход сделал человек

	public StepEvent(Object source, boolean humanIdentifer) {
		
		 super(source);
		
		 this.humanIdentifer=humanIdentifer;
	}
	
	@Override
	  public String toString()
	  {
	    return getClass().getName() + "[source = " + getSource() + ", message = " + humanIdentifer+ "]";
	  }

	public boolean gethumanIdentifer() {
		return humanIdentifer;
	}
	
	

}
