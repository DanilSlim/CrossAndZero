package lesson4.events;

import lesson4.graphic.CellGameField;

public class WinEventResponder implements WinEventListener {
	
	private CellGameField [][] cellArray;
	
	public WinEventResponder(CellGameField [][] cellArray) {
		
		this.cellArray=cellArray;
	}

	@Override
	public void winEventHappend(WinEvent winEvent) {
		
		for(int i=0;i<cellArray.length;i++) {
			
			for (int j=0;j<cellArray.length;j++) {
				
				cellArray[i][j].setEnabledForChoice(false);
			}
		}
		

	}

}
