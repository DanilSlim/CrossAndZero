package lesson4.logic;

import java.util.ArrayList;
import java.util.List;


import lesson4.graphic.CellGameField;

public class NeoLogicGame {
	
	
	public static CellGameField cellForAIStepNeo (CellGameField lastHumanStepCell, CellGameField [][] cellArray,int numberSymbolsForWin) {
		

		CellGameField cellForAIStep=null;
		
		UtilField candidateField=UtilLogicWeightCalculation.computeLessesWeightAll(lastHumanStepCell, cellArray, numberSymbolsForWin);
		
		if(candidateField.getWeight()>=100) {
			
				boolean endCircleFlag=false;
				
				for(int i=0; i<cellArray.length;i++) {
					
					for (int j=0; j<cellArray.length;j++) {
						
						if(cellArray[i][j].getCellType()=='-') {
							
							cellForAIStep=cellArray[i][j];
							
							endCircleFlag=true;
							
							break;
						}
						
					}
					
					if(endCircleFlag) break;
					
				}//end i circle
				
				
				if(cellForAIStep==null) {//Нет свободных ячеек
					
					boolean endCircleFlag1=false;
					
					for(int i=0; i<cellArray.length;i++) {
						
						for (int j=0; j<cellArray.length;j++) {
							
							if(cellArray[i][j].getCellType()=='O') {
								
								cellForAIStep=cellArray[i][j];
								
								cellForAIStep.setEnabledForChoice(true);
								
								cellForAIStep.setGameOverFlag(true);
								
								endCircleFlag1=true;
								
								break;
							}
							
						}
						
						if(endCircleFlag1) break;
						
					}
					
					return cellForAIStep;//нет свободных ячеек
				}
				
				return cellForAIStep;//есть свободные ячейки
				
			
			
		}
		
		else if (lastHumanStepCell.isGameOverFlag()) {
			
			boolean endCircleFlag1=false;
			
			for(int i=0; i<cellArray.length;i++) {
				
				for (int j=0; j<cellArray.length;j++) {
					
					if(cellArray[i][j].getCellType()=='O') {
						
						cellForAIStep=cellArray[i][j];
						
						cellForAIStep.setEnabledForChoice(true);
						
						cellForAIStep.setGameOverFlag(true);
						
						endCircleFlag1=true;
						
						break;
					}
					
				}
				
				if(endCircleFlag1) break;
				
			}
			
			return cellForAIStep;
		}
			
			
		
		
		
		if(candidateField.getType().equals("String"))
			
			return UtilLogicFieldCalculation.calculateFieldForStringNeo(candidateField, cellArray);
		
		if(candidateField.getType().equals("Column"))
			
			return UtilLogicFieldCalculation.calculateFieldForColumnNeo(candidateField, cellArray);
		
		if(candidateField.getType().equals("FirstDiagonal"))
			
			return UtilLogicFieldCalculation.calculateFieldForFirstDiagonalNeo(candidateField, cellArray);
		
		if(candidateField.getType().equals("SecondDiagonal"))
			
			return UtilLogicFieldCalculation.calculateFieldForSecondDiagonal(candidateField, cellArray);
		else return null;
		
	}
	
	
	
	/**
	 * Метод проверяет есть ли еще возможность продолжать игру (или ничья)
	 * @param cellArray - игровое поле
	 * @return - true ничья (нет возможности сделать следующий ход)
	 */
	public static boolean checkDeadHeat (CellGameField [][] cellArray) {
		
		boolean breakFlag=false;
		
		for(int i=0;i<cellArray.length;i++) {
			
			for (int j=0;j<cellArray.length;j++) {
				
				if(cellArray[i][j].getCellType()=='-') {
					
					breakFlag=true;
					
					break;
				}
				
				if(breakFlag)break;
			}
		}
		
		return !breakFlag;
	}
	
	
	/**
	 * Метод проверяет выигрышную комбинацию после каждого хода одного из игроков
	 * @param stepField - поле куда сделан ход
	 * @param numberSymbolsForWin - количество символов в выигрышной комбинации
	 * @param cellArray - ячейки игрового поля
	 * @return - true игрок сделавший последний ход выиграл
	 */
	public static boolean checkWin(CellGameField stepField, int numberSymbolsForWin, CellGameField [][] cellArray) {
		
		char symbolStep= stepField.getCellType();//символ для которого будем искать победную комбинацию Х или О
		
		int indexString=stepField.getyCoordinate();//в какой строке игрового поля ищем победную комбинацию
		
		int indexColumn=stepField.getxCoordinate();//в каком столбце игрового поля ищем победную комбинацию
		
		int maxArraIndex=cellArray.length-1;
		
		if (computeWinCombinationForString(cellArray, symbolStep, indexString, numberSymbolsForWin)) return true;
		
		if(computeWinCombinationForColumn(cellArray, symbolStep, indexColumn, numberSymbolsForWin)) return true;
		
		if(isFirstDiagonal(indexString, indexColumn, maxArraIndex)) {
			
			List<Integer>indexList=computeStartEndPointsFirstDiagonal(maxArraIndex, indexString, indexColumn);
			
			if(computeWinCombinationForFirstDiagonal(cellArray, indexList, symbolStep, numberSymbolsForWin)) return true;
			
		}
		
		if(isSecondDiagonal(indexString, indexColumn, maxArraIndex)) {
			
			List<Integer>indexList=computerStartEndPointsSecondDiagonal(maxArraIndex, indexString, indexColumn);
			
			if(computeWinCombinationForSecondDiagonal(cellArray, indexList, symbolStep, numberSymbolsForWin)) return true;
		}
		
		return false;
		
		
	}
	
	
	/**
	 * Метод рассчитывает выигрышную комбинацию для строки
	 * @param cellArray - игровое поле
	 * @param symbolStep - для какого символа определяем выигрышную комбинацию
	 * @param indexString - номер строки для которой считаем выигрышную комбинацию
	 * @param numberSymbolsForWin - количество символов подряд для выигрыша
	 * @return - true выигрыш
	 */
	private static boolean computeWinCombinationForString(CellGameField [][] cellArray,char symbolStep, int indexString, int numberSymbolsForWin) {
		
		int res=0;//количество символов одного значения подряд в строке
		
		for(int j=0;j<cellArray.length;j++) {
			
			if(cellArray[indexString][j].getCellType()==symbolStep) {
				
				res++;
			
				if(res==numberSymbolsForWin) break;
			}
			
			else
				res=0;
				
		}
		
		if(res==numberSymbolsForWin) return true;
		
		return false;
		
		
	}
	
	
	/**
	 * Метод рассчитывает выигрышную комбинацию для столбца
	 * @param cellArray - игровое поле
	 * @param symbolStep - для какого символа определяем выигрышную комбинацию
	 * @param indexColumn - номер столбца для которой считаем выигрышную комбинацию
	 * @param numberSymbolsForWin - количество символов подряд для выигрыша
	 * @return - true выигрыш
	 */
	private static boolean computeWinCombinationForColumn(CellGameField [][] cellArray,char symbolStep, int indexColumn, int numberSymbolsForWin) {
		
		int res=0;//количество символов одного значения подряд в столбце
		
		for(int i=0;i<cellArray.length;i++) {
			
			if(cellArray[i][indexColumn].getCellType()==symbolStep) {
				
				res++;
			
				if(res==numberSymbolsForWin) break;
			
			}
			
			else
				
				res=0;
		}
		
		if(res==numberSymbolsForWin) return true;
		
		return false;
		
		
	}
	
	
	
	/**
	 * Метод определяет необходимость вычисления выигрышной комбинации для первой диагонали
	 * @param indexString
	 * @param indexColumn
	 * @param maxArrayIndex
	 * @return
	 */
	protected static boolean isFirstDiagonal(int indexString, int indexColumn, int maxArrayIndex) {
		
		//int y=0;
		
		if (indexString-1>=0 && indexColumn-1>=0) return true;
		
		if(indexString+1<=maxArrayIndex && indexColumn+1<=maxArrayIndex) return true;
		
		return false;
		
	}
	
	
	/**
	 * Метод определяет необходимость вычисления выигрышной комбинации для второй диагонали
	 * @param indexString
	 * @param indexColumn
	 * @param maxArrayIndex
	 * @return
	 */
	protected static boolean isSecondDiagonal (int indexString, int indexColumn, int maxArrayIndex) {
		
		if(indexString-1>=0 && indexColumn+1<=maxArrayIndex) return true;
		
		if(indexString+1<=maxArrayIndex && indexColumn-1>=0) return true;
		
		return false;
	}
	
	protected static List<Integer> computeStartEndPointsFirstDiagonal(int maxArrayIndex, int indexString, int indexColumn) {
		
		List <Integer> indexList=new ArrayList<>(4);
		
		int startStringIndex=indexString;
		int startColumnIndex=indexColumn;
		
		int endStringIndex=indexString;
		int endColumIndex=indexColumn;
		
		if(startStringIndex>=startColumnIndex) { //часть поля под главной диагональю включая ее
			
			while (startColumnIndex>0) { //вверх по диагонали от точки хода с права на лево
				
				startStringIndex--;
				startColumnIndex--;
			}
			
			indexList.add(startStringIndex);
			indexList.add(startColumnIndex);
			
			
			while (endStringIndex<maxArrayIndex) {//вниз по диагонали от точки хода с лева на право
				
				endStringIndex++;
				endColumIndex++;
			}
			
			indexList.add(endStringIndex);
			indexList.add(endColumIndex);
			
			
		} else {//часть поля над главной диагональю не включая ее
			
			
			while (startStringIndex>0) { //вверх по диагонали от точки хода с права на лево
				
				startStringIndex--;
				startColumnIndex--;
			}
			
			indexList.add(startStringIndex);
			indexList.add(startColumnIndex);
			
			
			while (endColumIndex<maxArrayIndex) {//вниз по диагонали от точки хода с лева на право
				
				endStringIndex++;
				endColumIndex++;
			}
			
			indexList.add(endStringIndex);
			indexList.add(endColumIndex);
			
			
		}
		
		
		
		return indexList;
		
		
	}
	
	
	protected static List<Integer> computerStartEndPointsSecondDiagonal(int maxArrayIndex, int indexString, int indexColumn){
		
		List <Integer> indexList=new ArrayList<>(4);
		
		int startStringIndex=indexString;
		int startColumnIndex=indexColumn;
		
		int endStringIndex=indexString;
		int endColumIndex=indexColumn;
		
		
		if(startColumnIndex<=maxArrayIndex-startStringIndex) {//часть поля над второй главной диагональю включая ее
			
			while (startStringIndex>0) {// вверх по диагонали от точки хода с лева на право
				
				startStringIndex--;
				startColumnIndex++;
			}
			
			indexList.add(startStringIndex);
			indexList.add(startColumnIndex);
			
			
			while(endColumIndex>0) { //вниз по диагонали от точки хода с права на лево
				
				endStringIndex++;
				endColumIndex--;
			}
			
			indexList.add(endStringIndex);
			indexList.add(endColumIndex);
		}
		
		else { //часть поля под второй главной диагональю не включая ее
			
			
			
			while (startColumnIndex<maxArrayIndex) {// вверх по диагонали от точки хода с лева на право
				
				startStringIndex--;
				startColumnIndex++;
			}
			
			indexList.add(startStringIndex);
			indexList.add(startColumnIndex);
			
			
			while(endStringIndex<maxArrayIndex) { //вниз по диагонали от точки хода с права на лево
				
				endStringIndex++;
				endColumIndex--;
			}
			
			indexList.add(endStringIndex);
			indexList.add(endColumIndex);
			
			
		}
		
		
		
		
		
		return indexList;
	}
	
	
	private static boolean computeWinCombinationForFirstDiagonal(CellGameField [][] cellArray, List<Integer>indexList, char symbolStep,int numberSymbolsForWin) {
		
		int startStringIndex=indexList.get(0);
		
		int startColumnIndex=indexList.get(1);
		
		int endStringIndex=indexList.get(2);
		
		int endColumnIndex=indexList.get(3);
		
		int res=0;
		
		while(startStringIndex<=endStringIndex && startColumnIndex<=endColumnIndex) {
			
			if(cellArray[startStringIndex][startColumnIndex].getCellType()==symbolStep) {
				
				res++;
				
				if(res==numberSymbolsForWin) break;
			
			}
			
			else
				res=0;
			
			startStringIndex++;
			startColumnIndex++;
		}
		
		
		if(res==numberSymbolsForWin) return true;
		
		return false;
	}
	
	
	private static boolean computeWinCombinationForSecondDiagonal(CellGameField [][] cellArray, List<Integer>indexList, char symbolStep,int numberSymbolsForWin) {
		
		int startStringIndex=indexList.get(0);
		
		int startColumnIndex=indexList.get(1);
		
		int endStringIndex=indexList.get(2);
		
		int endColumnIndex=indexList.get(3);
		
		int res=0;
		
		while(startStringIndex<=endStringIndex && startColumnIndex>=endColumnIndex) {
			
			if(cellArray[startStringIndex][startColumnIndex].getCellType()==symbolStep) { 
				
				res++;
			
				if(res==numberSymbolsForWin) break;
			
			}
			
			else
				res=0;
			
			startStringIndex++;
			startColumnIndex--;
		}
		
		
		if(res==numberSymbolsForWin) return true;
		
		return false;
		
	}

}
