package lesson4.graphic;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.Toolkit;

import javax.swing.JFrame;


public class MyFrame extends JFrame {

	private static final long serialVersionUID = 956484441443431013L;
	
	private int dimensionField;
	
	private int numberSimbolForWin;
	
	private GameFieldPanel gameFieldPanel;
	
	private boolean startGameFlag=false;
	
	private boolean aiGameFlag=true;
	
	
	
	
	
	
	
	
	public MyFrame (int dimensionField) {
		
		this.dimensionField=dimensionField;
		
		setTitle("Крестики нолики");
		
		Toolkit kit=Toolkit.getDefaultToolkit();
		
		Dimension screenSize=kit.getScreenSize();
		
		int screenSizeHeight=screenSize.height;
		
		int screenSizeWidth=screenSize.width;
		
		setSize(screenSizeWidth/2,screenSizeHeight-100);
		
		setLocationByPlatform(true);
		
		
		
		
		ButtonPanel buttonPanel=new ButtonPanel(this);
		
		this.add(buttonPanel,BorderLayout.SOUTH);
		
		this.setResizable(false);
		
	}
	
	
	
	public void addGameField() {
		
		this.gameFieldPanel=new GameFieldPanel(this.dimensionField, this.numberSimbolForWin, this.aiGameFlag);//game field initialization
		
		this.add(gameFieldPanel,BorderLayout.CENTER);
		
	    this.repaint();
		
		this.setVisible(true);
		
	}






	public int getDimensionField() {
		return dimensionField;
	}






	public void setDimensionField(int dimensionField) {
		this.dimensionField = dimensionField;
	}



	public boolean isStartGameFlag() {
		return startGameFlag;
	}



	public void setStartGameFlag(boolean startGameFlag) {
		this.startGameFlag = startGameFlag;
	}



	public int getNumberSimbolForWin() {
		return numberSimbolForWin;
	}



	public void setNumberSimbolForWin(int numberSimbolForWin) {
		this.numberSimbolForWin = numberSimbolForWin;
	}



	public boolean isAiGameFlag() {
		return aiGameFlag;
	}



	public void setAiGameFlag(boolean aiGameFlag) {
		this.aiGameFlag = aiGameFlag;
	}
	
	
	
	
	

}
