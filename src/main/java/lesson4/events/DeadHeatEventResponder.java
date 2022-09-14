package lesson4.events;

import lesson4.graphic.CellGameField;

public class DeadHeatEventResponder implements DeadHeatEventListener {
	
	private CellGameField [][] cellArray;
	
	public DeadHeatEventResponder(CellGameField [][] cellArray) {
		
		this.cellArray=cellArray;
	}

	@Override
	public void deadHeatEventHappend(DeadHeatEvent event) {
		
		
			for(int i=0;i<cellArray.length;i++) {
			
				for (int j=0;j<cellArray.length;j++) {
				
				cellArray[i][j].setEnabledForChoice(false);
			}
		}
	}

}
