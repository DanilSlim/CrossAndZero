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
	
	
	
	
	/*public static CellGameField cellForAIStep (CellGameField lastHumanStepCell, CellGameField [][] cellArray,int numberSymbolsForWin) {
		
		CellGameField cellForAIStep=null;
		
		//приоритет отдается направлению с наименьшим весом
		
		String stringWeightKey=null;
		
		String columnWeightKey=null;
		
		String firstDiagonalWeightKey=null;
		
		String secondDiagonalWeightKey=null;
		
		Set<String> mapKeys;
		
		
		Executor executor=Executors.newFixedThreadPool(4);
		
		List<CompletableFuture<Map<String,Integer>>> futuresList=new ArrayList<>(4);
		
		
		CompletableFuture<Map<String,Integer>>stringWeightAsync=CompletableFuture.supplyAsync(()->computeWeightForString(lastHumanStepCell, cellArray, numberSymbolsForWin), executor);
		
		CompletableFuture<Map<String,Integer>>columnWeightAsync=CompletableFuture.supplyAsync(()->computeWeightForColumn(lastHumanStepCell, cellArray, numberSymbolsForWin), executor);
		
		CompletableFuture<Map<String,Integer>>firstDiagonalWeightAsync=CompletableFuture.supplyAsync(()->computeWeightForFirstDiagonal(lastHumanStepCell, cellArray, numberSymbolsForWin), executor);
		
		CompletableFuture<Map<String,Integer>>secondDiagonalWeightAsync=CompletableFuture.supplyAsync(()->computeWeightForSecondDiagonal(lastHumanStepCell, cellArray, numberSymbolsForWin), executor);
		
		
		futuresList.add(stringWeightAsync);
		
		futuresList.add(columnWeightAsync);
		
		futuresList.add(firstDiagonalWeightAsync);
		
		futuresList.add(secondDiagonalWeightAsync);
		
		
		CompletableFuture<Void> allFutures=CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
		
		
		CompletableFuture<List<Map<String,Integer>>>listAllFutures=allFutures.thenApply(v->{return futuresList.stream().map(futuresEx->futuresEx.join()).collect(Collectors.toList());});
		
		
		List<Map<String,Integer>> listMap=listAllFutures.join();
		
		
		
		
		//Map<String,Integer> stringWeight=computeWeightForString(lastHumanStepCell, cellArray, numberSymbolsForWin);
		
		Map<String,Integer> stringWeight=listMap.get(0);
		
		mapKeys=stringWeight.keySet();
		
		for(String key : mapKeys) {
		
			stringWeightKey=key;
		}
		
		//Map<String,Integer> columnWeight=computeWeightForColumn(lastHumanStepCell, cellArray, numberSymbolsForWin);
		
		Map<String,Integer> columnWeight=listMap.get(1);
		
		mapKeys=columnWeight.keySet();
		
		for(String key : mapKeys) {
		
			columnWeightKey=key;
		}
		
		//Map<String,Integer> firstDiagonalWeight=computeWeightForFirstDiagonal(lastHumanStepCell, cellArray, numberSymbolsForWin);
		
		Map<String,Integer> firstDiagonalWeight=listMap.get(2);
		
		mapKeys=firstDiagonalWeight.keySet();
		
		for(String key : mapKeys) {
		
			firstDiagonalWeightKey=key;
		}
		
		//Map<String,Integer> secondDiagonalWeight=computeWeightForSecondDiagonal(lastHumanStepCell, cellArray, numberSymbolsForWin);
		
		Map<String,Integer> secondDiagonalWeight=listMap.get(3);
		
		mapKeys=secondDiagonalWeight.keySet();
		
		for(String key : mapKeys) {
		
			secondDiagonalWeightKey=key;
		}
		
		
		
		if(firstDiagonalWeight.get(firstDiagonalWeightKey)<=stringWeight.get(stringWeightKey) && 
				firstDiagonalWeight.get(firstDiagonalWeightKey)<=columnWeight.get(columnWeightKey) &&
				firstDiagonalWeight.get(firstDiagonalWeightKey)<=secondDiagonalWeight.get(secondDiagonalWeightKey)) {
			
			if(firstDiagonalWeight.get(firstDiagonalWeightKey)==100 && secondDiagonalWeight.get(secondDiagonalWeightKey)==100 &&
					stringWeight.get(stringWeightKey)==100 && columnWeight.get(columnWeightKey)==100) {
				
				boolean endCircleFlag=false;
				
				for(int i=0; i<cellArray.length;i++) {
					
					for (int j=0; j<cellArray.length;j++) {
						
						if(cellArray[i][j].getCellType()=='-') {
							
							cellForAIStep=cellArray[i][j];
							
							cellForAIStep.setGameOverFlag(true);
							
							endCircleFlag=true;
							
							break;
						}
						
					}
					
					if(endCircleFlag) break;
					
				}
			}
			else
			
			cellForAIStep=calculateFieldForFirstDiagonal(lastHumanStepCell,firstDiagonalWeightKey,cellArray);
		}
		
		
		
		else if(secondDiagonalWeight.get(secondDiagonalWeightKey)<=stringWeight.get(stringWeightKey) &&
				secondDiagonalWeight.get(secondDiagonalWeightKey)<=columnWeight.get(columnWeightKey) &&
				secondDiagonalWeight.get(secondDiagonalWeightKey)<=firstDiagonalWeight.get(firstDiagonalWeightKey)) {
			

			if(firstDiagonalWeight.get(firstDiagonalWeightKey)==100 && secondDiagonalWeight.get(secondDiagonalWeightKey)==100 &&
					stringWeight.get(stringWeightKey)==100 && columnWeight.get(columnWeightKey)==100) {
				
				boolean endCircleFlag=false;
				
				for(int i=0; i<cellArray.length;i++) {
					
					for (int j=0; j<cellArray.length;j++) {
						
						if(cellArray[i][j].getCellType()=='-') {
							
							cellForAIStep=cellArray[i][j];
							
							cellForAIStep.setGameOverFlag(true);
							
							endCircleFlag=true;
							
							break;
						}
						
					}
					
					if(endCircleFlag) break;
					
				}
			}
			else
			
			cellForAIStep=calculateFieldForSecondDiagonal(lastHumanStepCell,secondDiagonalWeightKey, cellArray);
		}
		
		
		
		else if (stringWeight.get(stringWeightKey)<=columnWeight.get(columnWeightKey) && 
				stringWeight.get(stringWeightKey)<=firstDiagonalWeight.get(firstDiagonalWeightKey) 
				&& stringWeight.get(stringWeightKey)<=secondDiagonalWeight.get(secondDiagonalWeightKey)) {
			

			if(firstDiagonalWeight.get(firstDiagonalWeightKey)==100 && secondDiagonalWeight.get(secondDiagonalWeightKey)==100 &&
					stringWeight.get(stringWeightKey)==100 && columnWeight.get(columnWeightKey)==100) {
				
				boolean endCircleFlag=false;
				
				for(int i=0; i<cellArray.length;i++) {
					
					for (int j=0; j<cellArray.length;j++) {
						
						if(cellArray[i][j].getCellType()=='-') {
							
							cellForAIStep=cellArray[i][j];
							
							cellForAIStep.setGameOverFlag(true);
							
							endCircleFlag=true;
							
							break;
						}
						
					}
					
					if(endCircleFlag) break;
					
				}
			}
			else
			
			cellForAIStep=calculateFieldForString(lastHumanStepCell,stringWeightKey, cellArray);
		}
		
		
		else if(columnWeight.get(columnWeightKey)<=stringWeight.get(stringWeightKey) &&
				columnWeight.get(columnWeightKey)<=firstDiagonalWeight.get(firstDiagonalWeightKey) &&
				columnWeight.get(columnWeightKey)<=secondDiagonalWeight.get(secondDiagonalWeightKey)) {
			

			if(firstDiagonalWeight.get(firstDiagonalWeightKey)==100 && secondDiagonalWeight.get(secondDiagonalWeightKey)==100 &&
					stringWeight.get(stringWeightKey)==100 && columnWeight.get(columnWeightKey)==100) {
				
				boolean endCircleFlag=false;
				
				for(int i=0; i<cellArray.length;i++) {
					
					for (int j=0; j<cellArray.length;j++) {
						
						if(cellArray[i][j].getCellType()=='-') {
							
							cellForAIStep=cellArray[i][j];
							
							cellForAIStep.setGameOverFlag(true);
							
							endCircleFlag=true;
							
							break;
						}
						
					}
					
					if(endCircleFlag) break;
					
				}
			}
			else
			
			
			cellForAIStep=calculateFieldForColumn(lastHumanStepCell,columnWeightKey, cellArray);
		}
		
		
		
		
		
		//Legacy code
		if(cellForAIStep==null) {//все поле заполнено
			
			boolean endCircleFlag=false;
			
			for(int i=0; i<cellArray.length;i++) {
				
				for (int j=0; j<cellArray.length;j++) {
					
					if(cellArray[i][j].getCellType()=='O') {
						
						cellForAIStep=cellArray[i][j];
						
						endCircleFlag=true;
						
						break;
					}
					
				}
				
				if(endCircleFlag) break;
				
			}
		}
		
		
		
		return cellForAIStep;
		
	}*/
	
	
	
	/*private static CellGameField calculateFieldForSecondDiagonal(CellGameField lastHumanStepCell,String secondDiagonalWeightKey, CellGameField[][] cellArray) {
		

		CellGameField cellForAIStep=null;
		
		int stringIndex=lastHumanStepCell.getyCoordinate();
		
		int columnIndex=lastHumanStepCell.getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		if(secondDiagonalWeightKey.equals("up")) {// двигаемся вверх до первой свободной ячейки
			
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
		
		else if (secondDiagonalWeightKey.equals("down")) {// двигаемся вниз до первой свободной ячейки
			
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
	}*/



	/*private static CellGameField calculateFieldForFirstDiagonal(CellGameField lastHumanStepCell,String firstDiagonalWeightKey, CellGameField[][] cellArray) {
		
		CellGameField cellForAIStep=null;
		
		int stringIndex=lastHumanStepCell.getyCoordinate();
		
		int columnIndex=lastHumanStepCell.getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		if(firstDiagonalWeightKey.equals("up")) {// двигаемся вверх до первой свободной ячейки
			
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
		
		else if(firstDiagonalWeightKey.equals("down")) {// двигаемся вниз до первой свободной ячейки
			
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
				
				if(i<=maxArrayIndex||j<=maxArrayIndex) circleFlag=false;

			}
			
		}
		
		
		return cellForAIStep;
	}*/



	/*private static CellGameField calculateFieldForColumn(CellGameField lastHumanStepCell, String columnWeightKey,CellGameField[][] cellArray) {
		
		CellGameField cellForAIStep=null;
		
		int stringIndex=lastHumanStepCell.getyCoordinate();
		
		int columnIndex=lastHumanStepCell.getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		if(columnWeightKey.equals("up")) {// двигаемся вверх до первой свободной ячейки
			
			for(int i=stringIndex-1;i>=0;i--) {
				
				if(cellArray[i][columnIndex].getCellType()=='-') {
					
					cellForAIStep=cellArray[i][columnIndex];
					
					break;
				}
			}
		}
		
		else if(columnWeightKey.equals("down")) {// двигаемся вниз до первой свободной ячейки
			
			for(int i=stringIndex+1;i<=maxArrayIndex;i++) {
				
				if(cellArray[i][columnIndex].getCellType()=='-') {
					
					cellForAIStep=cellArray[i][columnIndex];
					
					break;
				}
			}
			
		}
		
		
		
		return cellForAIStep;
	}*/



	/*private static CellGameField calculateFieldForString(CellGameField lastHumanStepCell, String stringWeightKey,CellGameField[][] cellArray) {
		
		CellGameField cellForAIStep=null;
		
		int stringIndex=lastHumanStepCell.getyCoordinate();
		
		int columnIndex=lastHumanStepCell.getxCoordinate();
		
		int maxArrayIndex=cellArray.length-1;
		
		
		if(stringWeightKey.equals("right")) {//двигаемся вправо до первой свободной ячейки в строке
			
			for(int j=columnIndex+1;j<=maxArrayIndex;j++) {
				
				if(cellArray[stringIndex][j].getCellType()=='-') {
					
					cellForAIStep=cellArray[stringIndex][j];
					
					break;
				}
			}
			
			
		}
		
		else if(stringWeightKey.equals("left")) {//двигаемся влево до первой свободной ячейки в строке
			
			for (int j=columnIndex-1;j>=0;j--) {
				
				if(cellArray[stringIndex][j].getCellType()=='-') {
					
					cellForAIStep=cellArray[stringIndex][j];
					
					break;
				}
			}
			
		}
		
		
		
		return cellForAIStep;
		
		
	}*/



	/*private static Map<String, Integer> computeWeightForString(CellGameField lastHumanStepCell,CellGameField [][] cellArray, int numberSymbolsForWin) {
		
		//проверяем достижима ли победная комбинация при движении влево и вправо по строке от точки последнего хода человека
		
		Map<String,Integer> resultMap=new HashMap<>(1);//мапа для хранения веса и направления
		
		int stringWeight=0;
		
		int stringIndex=lastHumanStepCell.getyCoordinate();
		
		int columnIndex=lastHumanStepCell.getxCoordinate();
		
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
		
		/*else {
			
			stringWeight=100;//победная комбинация в строке не достижима
			
			resultMap.put("left", stringWeight);
		}*/
		
		
		
		/*if(leftPartWeight<=rightPartWeight) {
			
			stringWeight=leftPartWeight;//выбираем минимальный вес для всей строки
			
			resultMap.put("left", stringWeight);
		
		}
		
		else {
			
			
			stringWeight=rightPartWeight;
		
			resultMap.put("right", stringWeight);
		}
		
		return resultMap;
		
		
	}*/
	
	
	
	/*private static Map<String, Integer>computeWeightForColumn(CellGameField lastHumanStepCell,CellGameField [][] cellArray, int numberSymbolsForWin) {
		
		//проверяем достижима ли победная комбинация при движении вверх и вниз по столбцу от точки последнего хода человека
		
		Map<String,Integer> resultMap=new HashMap<>(1);//мапа для хранения веса и направления
		
		int columnWeight=0;
		
		int stringIndex=lastHumanStepCell.getyCoordinate();
		
		int columnIndex=lastHumanStepCell.getxCoordinate();
		
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
		
		/*else { //победная комбинация в столбце не достижима
			
			columnWeight=100;
			
			resultMap.put("up", columnWeight);
		}*/
		
		
		
	/*	if(upPartWeight<=downPartWeight) {
			
			columnWeight=upPartWeight;
			
			resultMap.put("up", columnWeight);
		}
		
		else {
			
			columnWeight=downPartWeight;
			
			resultMap.put("down", columnWeight);
		}
		
		return resultMap;
			
	}*/
	
	
	
	
	/*private static Map<String,Integer>computeWeightForFirstDiagonal(CellGameField lastHumanStepCell,CellGameField [][] cellArray, int numberSymbolsForWin){
		
		//проверяем достижима ли победная комбинация при движении вверх и вниз по первой диагонали от точки последнего хода человека
		
		Map<String,Integer> resultMap=new HashMap<>(1);//мапа для хранения веса и направления
		
		int firstDiagonalWeight=0;
		
		int stringIndex=lastHumanStepCell.getyCoordinate();
		
		int columnIndex=lastHumanStepCell.getxCoordinate();
		
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
			
			firstDiagonalWeight=upPartWeight;
			
			resultMap.put("up", firstDiagonalWeight);
		}
		
		else {
			
			firstDiagonalWeight=downPartWeight;
			
			resultMap.put("down", firstDiagonalWeight);
		}
		
		return resultMap;
		
	}*/
	
	
	
	
	
	/*private static Map<String,Integer> computeWeightForSecondDiagonal(CellGameField lastHumanStepCell,CellGameField [][] cellArray, int numberSymbolsForWin){
		
		//проверяем достижима ли победная комбинация при движении вверх и вниз по второй диагонали от точки последнего хода человека
		
		Map<String,Integer> resultMap=new HashMap<>(1);//мапа для хранения веса и направления
		
		int secondDiagonalWeight=0;
		
		int stringIndex=lastHumanStepCell.getyCoordinate();
		
		int columnIndex=lastHumanStepCell.getxCoordinate();
		
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
			
			secondDiagonalWeight=upPartWeight;
			
			resultMap.put("up", secondDiagonalWeight);
		}
		
		else {
			
			secondDiagonalWeight=downPartWeight;
			
			resultMap.put("down", secondDiagonalWeight);
		}
		
		return resultMap;
		
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
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
