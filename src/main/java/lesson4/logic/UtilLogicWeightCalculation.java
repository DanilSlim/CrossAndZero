package lesson4.logic;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import lesson4.graphic.CellGameField;

public class UtilLogicWeightCalculation {
	
	
	public static UtilField computeLessesWeightAll(CellGameField targetCell,CellGameField [][] cellArray, int numberSymbolsForWin) {
		
		/*UtilField lessesWeightForString=computeLessesWeightForString(targetCell, cellArray, numberSymbolsForWin);
		
		UtilField lessesWeightForColumn=computeLessesWeightForColumn(targetCell, cellArray, numberSymbolsForWin);
		
		UtilField lessesWeightForFirstDiagonal=computeLessesWeightForFirstDiagonal(targetCell, cellArray, numberSymbolsForWin);
		
		UtilField lessesWeightForSecondDiagonal=computeLessesWeightForSecondDiagonal(targetCell, cellArray, numberSymbolsForWin);*/
		
		
		
		Executor executor=Executors.newFixedThreadPool(4);
		
		List<CompletableFuture<UtilField>> futuresList=new ArrayList<>(4);
		
		
		CompletableFuture<UtilField>lessesWeightForStringAsync=CompletableFuture.supplyAsync(()->computeLessesWeightForString(targetCell, cellArray, numberSymbolsForWin),executor);
		
		CompletableFuture<UtilField>lessesWeightForColumnAsync=CompletableFuture.supplyAsync(()->computeLessesWeightForColumn(targetCell, cellArray, numberSymbolsForWin), executor);
		
		CompletableFuture<UtilField>lessesWeightForFirstDiagonalAsync=CompletableFuture.supplyAsync(()->computeLessesWeightForFirstDiagonal(targetCell, cellArray, numberSymbolsForWin), executor);
		
		CompletableFuture<UtilField>lessesWeightForSecondDiagonalAsync=CompletableFuture.supplyAsync(()->computeLessesWeightForSecondDiagonal(targetCell, cellArray, numberSymbolsForWin), executor);
		
		
		futuresList.add(lessesWeightForStringAsync);
		
		futuresList.add(lessesWeightForColumnAsync);
		
		futuresList.add(lessesWeightForFirstDiagonalAsync);
		
		futuresList.add(lessesWeightForSecondDiagonalAsync);
		
		
		CompletableFuture<Void> allFutures=CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
		
		
		CompletableFuture<List<UtilField>>listAllFutures=allFutures.thenApply(v->{return futuresList.stream().map(futuresEx->futuresEx.join()).collect(Collectors.toList());});
		
		List<UtilField>listUtilField=listAllFutures.join();
		
		
		UtilField lessesWeightForString=listUtilField.get(0);
		
		UtilField lessesWeightForColumn=listUtilField.get(1);
		
		UtilField lessesWeightForFirstDiagonal=listUtilField.get(2);
		
		UtilField lessesWeightForSecondDiagonal=listUtilField.get(3);
		
		
		
		if(lessesWeightForString.getWeight()<=lessesWeightForColumn.getWeight() && 
				lessesWeightForString.getWeight()<=lessesWeightForFirstDiagonal.getWeight() && 
				lessesWeightForString.getWeight()<=lessesWeightForSecondDiagonal.getWeight())
			
			return lessesWeightForString;
		
		if (lessesWeightForColumn.getWeight()<=lessesWeightForString.getWeight() &&
				lessesWeightForColumn.getWeight()<=lessesWeightForFirstDiagonal.getWeight() &&
				lessesWeightForColumn.getWeight()<=lessesWeightForSecondDiagonal.getWeight())
			
			return lessesWeightForColumn;
		
		if(lessesWeightForFirstDiagonal.getWeight()<=lessesWeightForString.getWeight() &&
				lessesWeightForFirstDiagonal.getWeight()<=lessesWeightForColumn.getWeight() &&
				lessesWeightForFirstDiagonal.getWeight()<=lessesWeightForSecondDiagonal.getWeight())
			
			return lessesWeightForFirstDiagonal;
		
		if(lessesWeightForSecondDiagonal.getWeight()<=lessesWeightForString.getWeight() &&
				lessesWeightForSecondDiagonal.getWeight()<=lessesWeightForColumn.getWeight() &&
				lessesWeightForSecondDiagonal.getWeight()<=lessesWeightForFirstDiagonal.getWeight())
			
			return lessesWeightForSecondDiagonal;
		
		else
			
			return null;
		
		
	}
	
	
	
	
	public static UtilField computeLessesWeightForString(CellGameField targetCell,CellGameField [][] cellArray, int numberSymbolsForWin) {
		
		UtilField lessesWeightFieldForString=new UtilField(targetCell);
		
		lessesWeightFieldForString.setType("String");
		
		lessesWeightFieldForString.setWeight(1000);
		
		lessesWeightFieldForString.setDirect("rignt");
		
		List<CellGameField>targetCellsListForString=defineTargetCellsListForString(targetCell, cellArray);
		
		if(targetCellsListForString.size()>0) {
			
			List<UtilField> utilFieldList=new ArrayList<>(targetCellsListForString.size());
			
			for(int i=0;i<=targetCellsListForString.size()-1;i++) {
				
				utilFieldList.add(computeWeightForString(targetCellsListForString.get(i), cellArray, numberSymbolsForWin));
			}
			
			for(int i=0;i<=targetCellsListForString.size()-1;i++) {
				
				if(utilFieldList.get(i).getWeight()<lessesWeightFieldForString.getWeight()) {
					
					lessesWeightFieldForString.setDirect(utilFieldList.get(i).getDirect());
					
					lessesWeightFieldForString.setWeight(utilFieldList.get(i).getWeight());
					
					lessesWeightFieldForString.setTargetField(utilFieldList.get(i).getTargetField());
				}
			}
		}
		
		return lessesWeightFieldForString;
		
	}
	
	
	
	
	public static UtilField computeLessesWeightForColumn(CellGameField targetCell,CellGameField [][] cellArray, int numberSymbolsForWin) {
		
		UtilField lessesWeightFieldForColumn=new UtilField(targetCell);
		
		lessesWeightFieldForColumn.setType("Column");
		
		lessesWeightFieldForColumn.setWeight(1000);
		
		lessesWeightFieldForColumn.setDirect("up");
		
		List<CellGameField>targetCellsListForColumn=defineTargetCellsListForColumn(targetCell, cellArray);
		
		if(targetCellsListForColumn.size()>0) {
			
			List<UtilField> utilFieldList=new ArrayList<>(targetCellsListForColumn.size());
			
			for(int i=0;i<=targetCellsListForColumn.size()-1;i++) {
				
				utilFieldList.add(computeWeightForColumn(targetCellsListForColumn.get(i), cellArray, numberSymbolsForWin));
			}
			
			for(int i=0;i<=targetCellsListForColumn.size()-1;i++) {
				
				if(utilFieldList.get(i).getWeight()<lessesWeightFieldForColumn.getWeight()) {
					
				
				lessesWeightFieldForColumn.setDirect(utilFieldList.get(i).getDirect());
				
				lessesWeightFieldForColumn.setWeight(utilFieldList.get(i).getWeight());
				
				lessesWeightFieldForColumn.setTargetField(utilFieldList.get(i).getTargetField());
				}
			}
			
		}
		
		return lessesWeightFieldForColumn;
		
	}
	
	
	
	public static UtilField computeLessesWeightForFirstDiagonal (CellGameField targetCell,CellGameField [][] cellArray,int numberSymbolsForWin) {
		
		UtilField lessesWeightFieldForFirstDiagonal=new UtilField(targetCell);
		
		lessesWeightFieldForFirstDiagonal.setType("FirstDiagonal");
		
		lessesWeightFieldForFirstDiagonal.setWeight(1000);
		
		lessesWeightFieldForFirstDiagonal.setDirect("up");
		
		List<CellGameField>targetCellsListForFirstDiagonal=defineTargetCellsListForFirstDiagonal(targetCell, cellArray);
		
		if(targetCellsListForFirstDiagonal.size()>0) {
			
			List<UtilField> utilFieldList=new ArrayList<>(targetCellsListForFirstDiagonal.size());
			
			for(int i=0;i<=targetCellsListForFirstDiagonal.size()-1;i++) {
				
				utilFieldList.add(computeWeightForFirstDiagonal(targetCellsListForFirstDiagonal.get(i), cellArray, numberSymbolsForWin));
			}
			
			for(int i=0;i<=targetCellsListForFirstDiagonal.size()-1;i++) {
				
				if(utilFieldList.get(i).getWeight()<lessesWeightFieldForFirstDiagonal.getWeight()) {
				
				lessesWeightFieldForFirstDiagonal.setDirect(utilFieldList.get(i).getDirect());
				
				lessesWeightFieldForFirstDiagonal.setWeight(utilFieldList.get(i).getWeight());
				
				lessesWeightFieldForFirstDiagonal.setTargetField(utilFieldList.get(i).getTargetField());
				}
			}
		}
		
		return lessesWeightFieldForFirstDiagonal;
		
	}
	
	
	public static UtilField computeLessesWeightForSecondDiagonal (CellGameField targetCell,CellGameField [][] cellArray,int numberSymbolsForWin) {
		
		UtilField lessesWeightFieldForSecondDiagonal=new UtilField(targetCell);
		
		lessesWeightFieldForSecondDiagonal.setType("SecondDiagonal");
		
		lessesWeightFieldForSecondDiagonal.setWeight(1000);
		
		lessesWeightFieldForSecondDiagonal.setDirect("up");
		
		List<CellGameField>targetCellsListForSecondDiagonal=defineTargetCellsListForSecondDiagonal(targetCell, cellArray);
		
		if(targetCellsListForSecondDiagonal.size()>0) {
			
			List<UtilField> utilFieldList=new ArrayList<>(targetCellsListForSecondDiagonal.size());
			
			for(int i=0;i<=targetCellsListForSecondDiagonal.size()-1;i++) {
				
				utilFieldList.add(computeWeightForSecondDiagonal(targetCellsListForSecondDiagonal.get(i), cellArray, numberSymbolsForWin));
			}
			
			for(int i=0;i<=targetCellsListForSecondDiagonal.size()-1;i++) {
				
				if(utilFieldList.get(i).getWeight()<lessesWeightFieldForSecondDiagonal.getWeight()) {
					
				
				lessesWeightFieldForSecondDiagonal.setDirect(utilFieldList.get(i).getDirect());
				
				lessesWeightFieldForSecondDiagonal.setWeight(utilFieldList.get(i).getWeight());
				
				lessesWeightFieldForSecondDiagonal.setTargetField(utilFieldList.get(i).getTargetField());
				}
			}
		}
		
		return lessesWeightFieldForSecondDiagonal;
	}
	
	
	
	
	////////Методы для вычисления списков ячеек для которых необходимо вычислять веса победной комбинации///////////////////////////////////////
	
	public static List<CellGameField> defineTargetCellsListForString(CellGameField targetCell,CellGameField [][] cellArray){
		
		List<CellGameField> targetCellsForString=new ArrayList<>(cellArray.length);//список всех ячеек для вычисления возможной победной комбинации в строке
	
		int stringIndex=targetCell.getyCoordinate();
		
		for (int j=0;j<cellArray.length;j++) {
			
			if(cellArray[stringIndex][j].getCellType()=='X') targetCellsForString.add(cellArray[stringIndex][j]);
		}
		
		return targetCellsForString;
		
		
	}
	
	
	
	public static List<CellGameField> defineTargetCellsListForColumn (CellGameField targetCell,CellGameField [][] cellArray){
		
		List<CellGameField> targetCellsForColumn=new ArrayList<>(cellArray.length);//список всех ячеек для вычисления возможной победной комбинации в столбце
		
		int columnIndex=targetCell.getxCoordinate();
		
		for(int i=0;i<cellArray.length;i++) {
			
			if(cellArray[i][columnIndex].getCellType()=='X') targetCellsForColumn.add(cellArray[i][columnIndex]);
		}
		
		return targetCellsForColumn;
	}
	
	
	
	public static List<CellGameField> defineTargetCellsListForFirstDiagonal (CellGameField targetCell,CellGameField [][] cellArray){
		
		List<CellGameField> targetCellsForFirstDiagonal=new ArrayList<>(cellArray.length);//список всех ячеек для вычисления возможной победной комбинации в первой диагонали
		
		int stringIndex=targetCell.getyCoordinate();
		
		int columnIndex=targetCell.getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		if(NeoLogicGame.isFirstDiagonal(stringIndex, columnIndex, maxArrayIndex)) {
			
			List<Integer> listStartAndEndPointsFirstDiagonal=NeoLogicGame.computeStartEndPointsFirstDiagonal(maxArrayIndex, stringIndex, columnIndex);
			
			int startStringIndex=listStartAndEndPointsFirstDiagonal.get(0);
			
			int startColumnIndex=listStartAndEndPointsFirstDiagonal.get(1);
			
			int endStringIndex=listStartAndEndPointsFirstDiagonal.get(2);
			
			int endColumnIndex=listStartAndEndPointsFirstDiagonal.get(3);
			
			
			while(startStringIndex<=endStringIndex && startColumnIndex<=endColumnIndex) {
				
				if(cellArray[startStringIndex][startColumnIndex].getCellType()=='X') 
					
					targetCellsForFirstDiagonal.add(cellArray[startStringIndex][startColumnIndex]);
					
				startStringIndex++;
				startColumnIndex++;
			}
		}
		
		return targetCellsForFirstDiagonal;
		
	}
	
	
	
	
	public static List<CellGameField> defineTargetCellsListForSecondDiagonal (CellGameField targetCell,CellGameField [][] cellArray){
		
		List<CellGameField> targetCellsForSecondDiagonal=new ArrayList<>(cellArray.length);//список всех ячеек для вычисления возможной победной комбинации во второй диагонали
		
		int stringIndex=targetCell.getyCoordinate();
		
		int columnIndex=targetCell.getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		if(NeoLogicGame.isSecondDiagonal(stringIndex, columnIndex, maxArrayIndex)) {
			
			List<Integer> listStartAndEndPointsSecondDiagonal=NeoLogicGame.computerStartEndPointsSecondDiagonal(maxArrayIndex, stringIndex, columnIndex);
			
			int startStringIndex=listStartAndEndPointsSecondDiagonal.get(0);
			
			int startColumnIndex=listStartAndEndPointsSecondDiagonal.get(1);
			
			int endStringIndex=listStartAndEndPointsSecondDiagonal.get(2);
			
			int endColumnIndex=listStartAndEndPointsSecondDiagonal.get(3);
			
			while(startStringIndex<=endStringIndex && startColumnIndex>=endColumnIndex) {
				
				if(cellArray[startStringIndex][startColumnIndex].getCellType()=='X')  
					
					targetCellsForSecondDiagonal.add(cellArray[startStringIndex][startColumnIndex]);
				
				startStringIndex++;
				startColumnIndex--;
			}
		}
		
		
		return targetCellsForSecondDiagonal;
		
		
	}
	
	
	

	
	
/////Методы вычисляющие вес для одной ячейки///////////////////////////////////////////////////////////////////////////////////////////
	private static UtilField computeWeightForString(CellGameField targetCell,CellGameField [][] cellArray, int numberSymbolsForWin) {
		
		//проверяем достижима ли победная комбинация при движении влево и вправо по строке от точки последнего хода человека
		
		UtilField targetFiled=new UtilField(targetCell);
			
		int stringIndex=targetCell.getyCoordinate();
		
		int columnIndex=targetCell.getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		int leftPartWeight=100;
		
		int rightPartWeight=100;
		
		if(columnIndex-(numberSymbolsForWin-1)>=0) {//победная комбинация при движении влево возможна
			
			leftPartWeight=0;
			
			for(int j=1;j<=numberSymbolsForWin-1;j++) { //двигаемся влево 
				
				if(cellArray[stringIndex][columnIndex-j].getCellType()=='X') continue;//символ Х в весе не учитывается
				
				else if(cellArray[stringIndex][columnIndex-j].getCellType()=='-') leftPartWeight++;//вес увеличивается на 1 
				
				else {
					
					leftPartWeight=100;//победная комбинация при движении влево недостижима т.к. содержит символ О
				
					break;
				}
			}
			
		}
		
		if(columnIndex+(numberSymbolsForWin-1)<=maxArrayIndex) {//победная комбинация придвижении вправо возможна
			
			rightPartWeight=0;
			
			for(int j=1;j<=numberSymbolsForWin-1;j++) {// двигаемся вправо
				
				if(cellArray[stringIndex][columnIndex+j].getCellType()=='X') continue;//символ Х в весе не учитывается
				
				else if(cellArray[stringIndex][columnIndex+j].getCellType()=='-') rightPartWeight++;//вес увеличивается на 1
				
				else {
					
					rightPartWeight=100;//победная комбинация при движении вправо недостижима т.к. содержит символ О
				
					break;
				}
			}
			
		}
		
		
		
		if(leftPartWeight<=rightPartWeight) {
			
			targetFiled.setWeight(leftPartWeight);
			
			targetFiled.setDirect("left");
		
		}
		
		else {
			
			
			targetFiled.setWeight(rightPartWeight);
		
			targetFiled.setDirect("right");
		}
		
		return targetFiled;
		
		
	}
	
	

	private static UtilField computeWeightForColumn(CellGameField targetCell,CellGameField [][] cellArray, int numberSymbolsForWin) {
		
		//проверяем достижима ли победная комбинация при движении вверх и вниз по столбцу от точки последнего хода человека
		
		UtilField targetFiled=new UtilField(targetCell);
	
		int stringIndex=targetCell.getyCoordinate();
		
		int columnIndex=targetCell.getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		int upPartWeight=100;
		
		int downPartWeight=100;
		
		
		if(stringIndex-(numberSymbolsForWin-1)>=0) {//победная комбинация при движении вверх возможна
			
			upPartWeight=0;
			
			for(int i=1;i<=numberSymbolsForWin-1;i++) { //двигаемся вверх по столбцу
				
				if(cellArray[stringIndex-i][columnIndex].getCellType()=='X') continue;//символ Х в весе не учитывается
				
				else if(cellArray[stringIndex-i][columnIndex].getCellType()=='-') upPartWeight++;//вес увеличивается на 1 
				
				else {
					
					upPartWeight=100;//победная комбинация при движении вверх недостижима т.к. содержит символ О
				
					break;
				}
					
			}
			
			
		}
		if(stringIndex+(numberSymbolsForWin-1)<=maxArrayIndex) {//победная комбинация при движении вниз возможна
			
			downPartWeight=0;
			
			for (int i=1;i<=numberSymbolsForWin-1;i++) {//двигаемся вниз по столбцу
				
				if(cellArray[stringIndex+i][columnIndex].getCellType()=='X') continue;//символ Х в весе не учитывается
				
				else if(cellArray[stringIndex+i][columnIndex].getCellType()=='-') downPartWeight++;//вес увеличивается на 1 
				
				else {
					
					downPartWeight=100;//победная комбинация при движении вниз недостижима т.к. содержит символ О
					
					break;
				}
				
				
			}
			
			
		}
		
		
		
		if(upPartWeight<=downPartWeight) {
			
			targetFiled.setWeight(upPartWeight);
			
			targetFiled.setDirect("up");
		}
		
		else {
			
			targetFiled.setWeight(downPartWeight);
			
			targetFiled.setDirect("down");
		}
		
		return targetFiled;
			
	}



	private static UtilField computeWeightForFirstDiagonal(CellGameField targetCell,CellGameField [][] cellArray, int numberSymbolsForWin){
		
		//проверяем достижима ли победная комбинация при движении вверх и вниз по первой диагонали от точки последнего хода человека
		
		UtilField targetFiled=new UtilField(targetCell);
		
		int stringIndex=targetCell.getyCoordinate();
		
		int columnIndex=targetCell.getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		int upPartWeight=100;
		
		int downPartWeight=100;
		
		if(stringIndex-(numberSymbolsForWin-1)>=0 && columnIndex-(numberSymbolsForWin-1)>=0) { //победная комбинация при движении вверх по диагонали возможна
			
			upPartWeight=0;
			
			for(int i=1;i<=numberSymbolsForWin-1;i++) {//движемся вверх по диагонали
				
				if(cellArray[stringIndex-i][columnIndex-i].getCellType()=='X') continue;//символ Х в весе не учитывается
				
				else if(cellArray[stringIndex-i][columnIndex-i].getCellType()=='-') upPartWeight++;//вес увеличивается на 1 
				
				else {
					
					upPartWeight=100;//победная комбинация при движении вверх недостижима т.к. содержит символ О
				
					break;
				}
			}
			
			
		}
		
		if(stringIndex+(numberSymbolsForWin-1)<=maxArrayIndex && columnIndex+(numberSymbolsForWin-1)<=maxArrayIndex) {//победная комбинация при движении вниз по диагонали возможна
			
			downPartWeight=0;
			
			for(int i=1;i<=numberSymbolsForWin-1;i++) {//движемся ввниз по диагонали
				
				if(cellArray[stringIndex+i][columnIndex+i].getCellType()=='X') continue;//символ Х в весе не учитывается
				
				else if(cellArray[stringIndex+i][columnIndex+i].getCellType()=='-') downPartWeight++;//вес увеличивается на 1 
				
				else {
					
					downPartWeight=100;//победная комбинация при движении вниз недостижима т.к. содержит символ О
				
					break;
				}
			}
			
			
		}
		
		
		
		if(upPartWeight<=downPartWeight) {
			
			targetFiled.setWeight(upPartWeight);
			
			targetFiled.setDirect("up");
		}
		
		else {
			
			targetFiled.setWeight(downPartWeight);
			
			targetFiled.setDirect("down");
		}
		
		return targetFiled;
		
	}



	private static UtilField computeWeightForSecondDiagonal(CellGameField targetCell,CellGameField [][] cellArray, int numberSymbolsForWin){
		
		//проверяем достижима ли победная комбинация при движении вверх и вниз по второй диагонали от точки последнего хода человека
		
		UtilField targetFiled=new UtilField(targetCell);
		
		int stringIndex=targetCell.getyCoordinate();
		
		int columnIndex=targetCell.getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		int upPartWeight=100;
		
		int downPartWeight=100;
		
		
		if(stringIndex-(numberSymbolsForWin-1)>=0 && columnIndex+(numberSymbolsForWin-1)<=maxArrayIndex) {//победная комбинация при движении вверх по диагонали возможна
			
			upPartWeight=0;
			
			for(int i=1;i<=numberSymbolsForWin-1;i++) {//движемся вверх по диагонали
				
				if(cellArray[stringIndex-i][columnIndex+i].getCellType()=='X') continue;//символ Х в весе не учитывается
				
				else if(cellArray[stringIndex-i][columnIndex+i].getCellType()=='-') upPartWeight++;//вес увеличивается на 1 
				
				else {
					
					upPartWeight=100;//победная комбинация при движении вверх недостижима т.к. содержит символ О
				
					break;
				}
				
			}
			
		}
		
		if(stringIndex+(numberSymbolsForWin-1)<=maxArrayIndex && columnIndex-(numberSymbolsForWin-1)>=0) {//победная комбинация при движении вниз по диагонали возможна
			
			downPartWeight=0;
			
			for(int i=1;i<=numberSymbolsForWin-1;i++) {//движемся ввниз по диагонали
				
				if(cellArray[stringIndex+i][columnIndex-i].getCellType()=='X') continue;//символ Х в весе не учитывается
				
				else if(cellArray[stringIndex+i][columnIndex-i].getCellType()=='-') downPartWeight++;//вес увеличивается на 1 
				
				else {
					
					downPartWeight=100;//победная комбинация при движении вниз недостижима т.к. содержит символ О
				
					break;
				}
			}
			
		}
				
		if(upPartWeight<=downPartWeight) {
			
			targetFiled.setWeight(upPartWeight);
			
			targetFiled.setDirect("up");
		}
		
		else {
			
			targetFiled.setWeight(downPartWeight);
			
			targetFiled.setDirect("down");
		}
		
		return targetFiled;
		
	}
	
	
	
	

}
