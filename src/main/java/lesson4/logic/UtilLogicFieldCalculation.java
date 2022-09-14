package lesson4.logic;

import lesson4.graphic.CellGameField;



public class UtilLogicFieldCalculation {
	
	

	protected static CellGameField calculateFieldForStringNeo(UtilField targetField, CellGameField[][] cellArray) {
		
		CellGameField cellForAIStep=null;
		
		int stringIndex=targetField.getTargetField().getyCoordinate();
		
		int columnIndex=targetField.getTargetField().getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		String directMove=targetField.getDirect();
		
		
		if(directMove.equals("right")) {//двигаемся вправо до первой свободной ячейки в строке
			
			for(int j=columnIndex+1;j<=maxArrayIndex;j++) {
				
				if(cellArray[stringIndex][j].getCellType()=='-') {
					
					cellForAIStep=cellArray[stringIndex][j];
					
					break;
				}
			}
			
			
		}
		
		else if(directMove.equals("left")) {//двигаемся влево до первой свободной ячейки в строке
			
			for (int j=columnIndex-1;j>=0;j--) {
				
				if(cellArray[stringIndex][j].getCellType()=='-') {
					
					cellForAIStep=cellArray[stringIndex][j];
					
					break;
				}
			}
			
		}
		
		
		
		return cellForAIStep;
		
		
	}

	protected static CellGameField calculateFieldForColumnNeo(UtilField targetField, CellGameField[][] cellArray) {
		
		CellGameField cellForAIStep=null;
		
		int stringIndex=targetField.getTargetField().getyCoordinate();
		
		int columnIndex=targetField.getTargetField().getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		if(targetField.getDirect().equals("up")) {// двигаемся вверх до первой свободной ячейки
			
			for(int i=stringIndex-1;i>=0;i--) {
				
				if(cellArray[i][columnIndex].getCellType()=='-') {
					
					cellForAIStep=cellArray[i][columnIndex];
					
					break;
				}
			}
		}
		
		else if(targetField.getDirect().equals("down")) {// двигаемся вниз до первой свободной ячейки
			
			for(int i=stringIndex+1;i<=maxArrayIndex;i++) {
				
				if(cellArray[i][columnIndex].getCellType()=='-') {
					
					cellForAIStep=cellArray[i][columnIndex];
					
					break;
				}
			}
			
		}
		
		
		
		return cellForAIStep;
	}

	protected static CellGameField calculateFieldForFirstDiagonalNeo(UtilField targetField,CellGameField[][] cellArray) {
		
		CellGameField cellForAIStep=null;
		
		int stringIndex=targetField.getTargetField().getyCoordinate();
		
		int columnIndex=targetField.getTargetField().getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		if(targetField.getDirect().equals("up")) {// двигаемся вверх до первой свободной ячейки
			
			int i=stringIndex-1;
			
			int j=columnIndex-1;
			
			boolean circleFlag=true;
			
			while (circleFlag) {
				
				if (cellArray[i][j].getCellType()=='-') {
					
					cellForAIStep=cellArray[i][j];
					
					break;
				}
				
				i--;
				j--;
				
				if(i<0 || j<0) circleFlag=false;
			}
			
			
		}
		
		else if(targetField.getDirect().equals("down")) {// двигаемся вниз до первой свободной ячейки
			
			int i=stringIndex+1;
			
			int j=columnIndex+1;
			
			boolean circleFlag=true;
			
			while(circleFlag) {
				
				if (cellArray[i][j].getCellType()=='-') {
					
					cellForAIStep=cellArray[i][j];
					
					break;
				}
				
				i++;
				j++;
				
				if(i>maxArrayIndex||j>maxArrayIndex) circleFlag=false;
	
			}
			
		}
		
		
		return cellForAIStep;
	}

	protected static CellGameField calculateFieldForSecondDiagonal(UtilField targetField, CellGameField[][] cellArray) {
		
	
		CellGameField cellForAIStep=null;
		
		int stringIndex=targetField.getTargetField().getyCoordinate();
		
		int columnIndex=targetField.getTargetField().getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		if(targetField.getDirect().equals("up")) {// двигаемся вверх до первой свободной ячейки
			
			int i=stringIndex-1;
			
			int j=columnIndex+1;
			
			boolean circleFlag=true;
			
			while(circleFlag) {
				
				if (cellArray[i][j].getCellType()=='-') {
					
					cellForAIStep=cellArray[i][j];
					
					break;
				}
				
				i--;
				j++;
				
				if (i<0||j>maxArrayIndex) circleFlag=false;
				
			}
			
			
		}
		
		else if (targetField.getDirect().equals("down")) {// двигаемся вниз до первой свободной ячейки
			
			int i=stringIndex+1;
			
			int j=columnIndex-1;
			
			boolean circleFlag=true;
			
			while(circleFlag) {
				
				if (cellArray[i][j].getCellType()=='-') {
					
					cellForAIStep=cellArray[i][j];
					
					break;
				}
				
				i++;
				j--;
				
				if(i>maxArrayIndex||j<0) circleFlag=false;
			}
			
			
		}
		
		return cellForAIStep;
	}
	
	
	
	
	
	
	

}
