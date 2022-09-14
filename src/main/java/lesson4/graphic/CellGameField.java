package lesson4.graphic;



import javax.swing.JButton;



/**
 * @author danil
 * Класс реализует ячейку игрового поля
 */
public class CellGameField extends JButton {

	private static final long serialVersionUID = 4711242539852002749L;
	
	
	private boolean enabledForChoice=true; //поле свободно для хода
	
	private int xCoordinate; //j-индекс массива
	
	private int yCoordinate;//i- индекс массива
	
	private char cellType='-';//тип ячейки "-" пустая ячейка
	
	private boolean gameOverFlag=false;
	
	
	
	public CellGameField(int yCoordinate, int xCoordinate) {
		
		this.xCoordinate=xCoordinate;
		
		this.yCoordinate=yCoordinate;
		
	}
	
	

	public boolean isEnabledForChoice() {
		return enabledForChoice;
	}

	public void setEnabledForChoice(boolean enabledForChoice) {
		this.enabledForChoice = enabledForChoice;
	}



	public int getxCoordinate() {
		return xCoordinate;
	}



	public int getyCoordinate() {
		return yCoordinate;
	}
	
	
	
	
	
	public char getCellType() {
		return cellType;
	}



	public void setCellType(char cellType) {
		this.cellType = cellType;
	}



	


	public boolean isGameOverFlag() {
		return gameOverFlag;
	}



	public void setGameOverFlag(boolean deadHeatFlag) {
		this.gameOverFlag = deadHeatFlag;
	}



	@Override
	public String toString() {
		return "CellGameField [enabledForChoice=" + enabledForChoice + ", xCoordinate=" + xCoordinate + ", yCoordinate="
				+ yCoordinate + ", cellType=" + cellType + "]";
	}
	
	
	

}
