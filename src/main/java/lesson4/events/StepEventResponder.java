package lesson4.events;



import javax.swing.JOptionPane;

import lesson4.graphic.CellGameField;
import lesson4.logic.LogicGame;

@Deprecated
public class StepEventResponder implements StepEventListener {
	
	private CellGameField [][] cellArray;
	
	private WinEventProducer winEventProducer;
	
	private DeadHeatEventProducer deadHeatEventProducer;
	
	
	public StepEventResponder(CellGameField [][] cellArray, WinEventProducer winEventProducer,DeadHeatEventProducer deadHeatEventProducer) {
		
		this.cellArray=cellArray;
		
		this.winEventProducer=winEventProducer;
		
		this.deadHeatEventProducer=deadHeatEventProducer;
	}

	@Override
	public void stepEventHappend(StepEvent stepEvent) {
		
		
		//вызов методов логики игры
		
		
		
		if(stepEvent.gethumanIdentifer()) { //если ход сделал человек
		
		CellGameField field=(CellGameField) stepEvent.getSource();
		
		field.setEnabledForChoice(false);
		
		field.setText("X");
		
		field.setCellType('X');
		
		
		String str=LogicGame.checkGameState(cellArray);
		
		//if dead heat - stop game
		//if win - stop game
		
		if(str.equals("Human win!!")) {winEventProducer.fireWinEvent();
		
			JOptionPane.showMessageDialog(null, str);
		}
		
		if(str.equals("Dead heat")) {deadHeatEventProducer.fireDeadHeatEvent();
		
			JOptionPane.showMessageDialog(null, str);
		}
		
		System.out.println("Human :"+str);
		}
		
		else {
			
			CellGameField field=(CellGameField) stepEvent.getSource();
			
			field.setEnabledForChoice(false);
			
			field.setText("O");
			
			field.setCellType('O');
			
			String str=LogicGame.checkGameState(cellArray);
			
			
			
			//if dead heat - stop game
			//if win - stop game
			
			if(str.equals("AI win!!")) {winEventProducer.fireWinEvent();
			
				JOptionPane.showMessageDialog(null, str);
			}
			
			if(str.equals("Dead heat")) {deadHeatEventProducer.fireDeadHeatEvent();
			
				JOptionPane.showMessageDialog(null, str);
			}
			
			System.out.println("AI :"+str);
			
			
		}
		
	}

}
