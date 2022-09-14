package lesson4.graphic;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import lesson4.events.DeadHeatEventProducer;
import lesson4.events.DeadHeatEventResponder;
import lesson4.events.StepEventProducer;
import lesson4.events.StepEventResponder;
import lesson4.events.WinEventProducer;
import lesson4.events.WinEventResponder;
import lesson4.events.neo.HumanStepEventResponder;
import lesson4.events.neo.NeoStepEventResponder;
import lesson4.logic.LogicGame;
import lesson4.logic.NeoLogicGame;

public class GameFieldPanel extends JPanel {
	
	private static final long serialVersionUID = -2100959666245611372L;
	
	private boolean humanStep =true; //флаг определяет чья очередь ходить AI или человека
	
	private CellGameField [][] cellArray;
	
	private StepEventProducer stepEventProducer=new StepEventProducer();
	
	private WinEventProducer winEventProducer = new WinEventProducer();
	
	private DeadHeatEventProducer deadHeatEventProducer=new DeadHeatEventProducer();
	
	private CellGameField lastHumanStepCell;//ячейка последнего сделанного человеком хода
	
	private int numberSimbolForWin=3;
	
	private boolean aiGameFlag;
	
	private boolean xStepHuman=true;//when game hu
	

	public GameFieldPanel(int dimensionSize, int numberSimbolForWin, boolean aiGameFlag) { //Creation game field
		
		this.numberSimbolForWin=numberSimbolForWin;
		
		this.aiGameFlag=aiGameFlag;
		
		setLayout(new GridLayout(dimensionSize, dimensionSize));
		
		this.cellArray=new CellGameField [dimensionSize][dimensionSize];
		
		
		for(int i=0;i<dimensionSize;i++) {//initialization field's cells
			
			for(int j=0;j<dimensionSize;j++) {
				
				cellArray[i][j]=new CellGameField(i, j);
				
				cellArray[i][j].addActionListener(new MoveInGame());
						
				this.add(cellArray[i][j]);
			}
		}
		
		
		WinEventResponder winEventResponder=new WinEventResponder(cellArray);
		
		winEventProducer.addWinEventListener(winEventResponder);
		
		DeadHeatEventResponder deadHeatEventResponder=new DeadHeatEventResponder(cellArray);
		
		deadHeatEventProducer.addDeadHeatEventListener(deadHeatEventResponder);
		
		//StepEventResponder stepEventResponder=new StepEventResponder(cellArray, winEventProducer, deadHeatEventProducer);
		
		if(this.aiGameFlag) {
			
			NeoStepEventResponder stepEventResponder= new NeoStepEventResponder(cellArray, winEventProducer, deadHeatEventProducer, this.numberSimbolForWin);
			
			stepEventProducer.addStepEventListener(stepEventResponder);
		}
		else {
			
			HumanStepEventResponder stepEventResponder=new HumanStepEventResponder(cellArray, winEventProducer, deadHeatEventProducer, this.numberSimbolForWin);
			
			stepEventProducer.addStepEventListener(stepEventResponder);
		}
		
		
		
		
	}
	
	
	//Слушатель нажатия на ячейку поля (кнопку), при выполнении хода игроком 
	private class MoveInGame implements ActionListener{ //ход в игре 
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(aiGameFlag) {//Game human vs AI
				
				if(humanStep) {//если очередь хода у человека обрабатываем нажатие кнопки
					
					CellGameField cellGameField=(CellGameField) e.getSource();
					
					if(cellGameField.isEnabledForChoice()) {
						
					stepEventProducer.fireStepEvent(cellGameField,true);//human step
					
					lastHumanStepCell=cellGameField;
					
					humanStep=false;
					
					
					//AI press button imitation
					ActionEvent e1 = new ActionEvent(this, 1001,"");
					
					ActionListener[] listeners;
					
					listeners = lastHumanStepCell.getActionListeners();
					
					listeners[0].actionPerformed(e1);
					///////////////////////////////////////////////////
					
					System.out.println(cellGameField);
					
					}
					
				} else { //ход AI
					
					//CellGameField cellForAIStep =  LogicGame.cellForAIStep(lastHumanStepCell, cellArray);
					
					//CellGameField cellForAIStep = NeoLogicGame.cellForAIStep(lastHumanStepCell, cellArray, NUMBER_SYMBOL_FOR_WIN);
					
					CellGameField cellForAIStep = NeoLogicGame.cellForAIStepNeo(lastHumanStepCell, cellArray, numberSimbolForWin);
					
					if(cellForAIStep.isEnabledForChoice()) {
						
					stepEventProducer.fireStepEvent(cellForAIStep,false);//AI step
					
					humanStep=true;
					
					System.out.println("AI "+cellForAIStep);
					
					}
					
				}
			}//end if game
			
			else {//game human vs human
				
				if(xStepHuman) {//ход за Х
					
					CellGameField cellGameField=(CellGameField) e.getSource();
					
					if(cellGameField.isEnabledForChoice()) {
						
					stepEventProducer.fireStepEvent(cellGameField,true);//human step
					
					lastHumanStepCell=cellGameField;
					
					xStepHuman=false;
					}
					
				}
					
				else {
						
						CellGameField cellGameField=(CellGameField) e.getSource();
						
						if(cellGameField.isEnabledForChoice()) {
							
						stepEventProducer.fireStepEvent(cellGameField,false);//human step
						
						lastHumanStepCell=cellGameField;
						
						xStepHuman=true;
						}
						
						
					}
				
				
				
				
				
				
			}
			
			
		
	  }//end action performed
		
	}

}
