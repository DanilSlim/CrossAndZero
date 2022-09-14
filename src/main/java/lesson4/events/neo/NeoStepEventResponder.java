package lesson4.events.neo;

import javax.swing.JOptionPane;

import lesson4.events.DeadHeatEventProducer;
import lesson4.events.StepEvent;
import lesson4.events.StepEventListener;
import lesson4.events.WinEventProducer;
import lesson4.graphic.CellGameField;
import lesson4.logic.NeoLogicGame;

public class NeoStepEventResponder implements StepEventListener {
	
	
	private CellGameField [][] cellArray;
	
	private int numberSymbolsForWin;
	
	private WinEventProducer winEventProducer;
	
	private DeadHeatEventProducer deadHeatEventProducer;
	
	
	public NeoStepEventResponder(CellGameField [][] cellArray, WinEventProducer winEventProducer,DeadHeatEventProducer deadHeatEventProducer, int numberSymbolsForWin) {
		
		this.cellArray=cellArray;	
		
		this.numberSymbolsForWin=numberSymbolsForWin;
		
		this.winEventProducer=winEventProducer;
		
		this.deadHeatEventProducer=deadHeatEventProducer;
	}

	@Override
	public void stepEventHappend(StepEvent stepEvent) {
		
		
		if(stepEvent.gethumanIdentifer()) { //если ход сделал человек
			
			CellGameField field=(CellGameField) stepEvent.getSource();
			
			field.setEnabledForChoice(false);
			
			field.setText("X");
			
			field.setCellType('X');
			
			if(NeoLogicGame.checkWin(field, numberSymbolsForWin, cellArray)) {
				
				winEventProducer.fireWinEvent();
				
				field.setGameOverFlag(true);
				
				JOptionPane.showMessageDialog(null, "Human Win!!!");
			}
			
			if (NeoLogicGame.checkDeadHeat(cellArray)) {
				
				deadHeatEventProducer.fireDeadHeatEvent();
				
				JOptionPane.showMessageDialog(null, "Dead heat!!!");
			}
			
		}
		
		else { //ход сделал AI
			
			CellGameField field=(CellGameField) stepEvent.getSource();
			
			if(!field.isGameOverFlag()) {
				
				field.setEnabledForChoice(false);
				
				field.setText("O");
				
				field.setCellType('O');
				
				if(NeoLogicGame.checkWin(field, numberSymbolsForWin, cellArray)) {
					
					winEventProducer.fireWinEvent();
					
					JOptionPane.showMessageDialog(null, "AI Win!!!");
				}
				
				if (NeoLogicGame.checkDeadHeat(cellArray)) {
					
					deadHeatEventProducer.fireDeadHeatEvent();
					
					JOptionPane.showMessageDialog(null, "Dead heat!!!");
				}
				
			}
				
			
			
		}//end else
		
			

	}

}
