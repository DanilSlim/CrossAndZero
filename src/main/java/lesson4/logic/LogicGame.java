package lesson4.logic;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import lesson4.graphic.CellGameField;

/**
 * @author danil
 * Класс реализует логику игры
 */
@Deprecated
public class LogicGame {
	
	
	/**
	 * Метод определяет состояние игры
	 * @param cellArray - массив с ячейками игрового поля
	 *
	 * @return - String содержащую состояние игры
	 * "Human win!!"- человек победил
	 * "AI win!!" - AI победил
	 * "Dead heat" - ничья
	 * "Continue game" - игра продолжается
	 */
	public static String checkGameState(CellGameField [][] cellArray) {
		
		int dimensionField=cellArray.length;
		
		int [] sumStringX=new int[dimensionField];//элементы массива содержат сумму Х-ячеек в одной строке
		
		int [] sumStringO=new int[dimensionField];//элементы массива содержат сумму О-ячеек в одной строке
		
		int [] sumColumnX=new int[dimensionField];//элементы массива содержат сумму Х-ячеек в одном солбце
		
		int [] sumColumnO=new int[dimensionField];//элементы массива содержат сумму О-ячеек в одном солбце
		
		

		
		int sumDiag1X=0;//сумма Х-ячеек в первой диагонали
		int sumDiag2X=0;//сумма Х-ячеек во второй диагонали
		
		
		int sumDiag1O=0;//сумма О-ячеек в первой диагонали
		int sumDiag2O=0;//сумма О-ячеек во второй диагонали
		
		boolean freeCell=false;
		
		for (int i=0; i<dimensionField;i++) {
			
			int sumOneStringX=0;
			int sumOneColumnX=0;
			
			int sumOneStringO=0;
			int sumOneColumnO=0;
			
			for (int j=0;j<dimensionField;j++) {
				
				//Checking one string 
				if(cellArray[i][j].getCellType()=='X') sumOneStringX++;
				
				if(cellArray[i][j].getCellType()=='O') sumOneStringO++;
				
				if(cellArray[i][j].getCellType()=='-') freeCell=true;
				
				//Checking one column
				if(cellArray[j][i].getCellType()=='X') sumOneColumnX++;
				
				if(cellArray[j][i].getCellType()=='O') sumOneColumnO++;
				
				if(cellArray[j][i].getCellType()=='-') freeCell=true;
				
				
				//For diagonals check only two cells for j-circle
				if(i==j) { //Check first diagonal
					
					if(cellArray[i][j].getCellType()=='X') sumDiag1X++;
					
					if(cellArray[i][j].getCellType()=='O') sumDiag1O++;
				}
				else if (j==dimensionField-1-i) {      //Check second diagonal
					
					if(cellArray[i][j].getCellType()=='X') sumDiag2X++;
					
					if(cellArray[i][j].getCellType()=='O') sumDiag2O++;
				}
				
				sumStringX[i]=sumOneStringX;
				
				sumStringO[i]=sumOneStringO;
				
				sumColumnX[i]=sumOneColumnX;
				
				sumColumnO[i]=sumOneColumnO;
			}//end j for
		} //end i for
		
		if(sumDiag1X==dimensionField||sumDiag2X==dimensionField) return "Human win!!";
		
		for(int i=0;i<dimensionField;i++) {
			
			if(sumStringX[i]==dimensionField) return "Human win!!";
			
			if(sumColumnX[i]==dimensionField) return "Human win!!";
		}
			
			
		
		if (sumDiag1O==dimensionField||sumDiag2O==dimensionField) return "AI win!!";
		
		for(int i=0;i<dimensionField;i++) {
			
			if(sumStringO[i]==dimensionField) return "AI win!!";
			
			if(sumColumnO[i]==dimensionField) return "AI win!!";
		}
		
		
		
		if (!freeCell) return "Dead heat";
		
		else return "Continue game";
		
	}
	
	
	
	/**
	 * Метод определяет ячейку для хода AI
	 * @param lastHumanStepCell - ячейка с последним ходом человека
	 * @param cellArray - массив с ячейками игрового поля
	 * 
	 * @return - CellGameField cellForAIStep - ячейка выбранная для хода AI
	 */
	public static CellGameField cellForAIStep(CellGameField lastHumanStepCell, CellGameField [][] cellArray) {
		
		int dimensionField=cellArray.length;
		
		CellGameField cellForAIStep=null;
		
		
		//int stringWeight=calculateStringWeight(cellArray, dimensionField, lastHumanStepCell.getyCoordinate());//string weight
		
		//int columnWeight=calculateColumnWeight(cellArray, dimensionField, lastHumanStepCell.getxCoordinate());//column wight
		
		
		
		
		//Multithtreading
		//Веса строки, столбца и даиагоналей вычисляются в отдельных потоках
		
		Executor executor=Executors.newFixedThreadPool(4);
		
		List<CompletableFuture<Integer>> futuresList=new ArrayList<>(4);
		
		CompletableFuture<Integer> stringWeightAsync=CompletableFuture.supplyAsync(()->calculateStringWeight(cellArray, dimensionField, lastHumanStepCell.getyCoordinate()), executor);
		
		CompletableFuture<Integer> columnWeightAsync=CompletableFuture.supplyAsync(()->calculateColumnWeight(cellArray, dimensionField, lastHumanStepCell.getxCoordinate()), executor);
		
		futuresList.add(stringWeightAsync);
		
		futuresList.add(columnWeightAsync);
		
		
		
		
		
		
		int isDiagonalsElement=isDiagonalElement(lastHumanStepCell.getyCoordinate(), lastHumanStepCell.getxCoordinate(), dimensionField);
		
		int diag1Weight=1000; //diag1Weght
		
		int diag2Weight=1000;//diag2Weight
		
		boolean diag1PresentFlag=false;
		
		boolean diag2PresentFlag=false;
		
		
		
		
		//if(isDiagonalsElement==1||isDiagonalsElement==0) diag1Weight=calculateFirstDiagonalWeight(cellArray, dimensionField);
		
		//if(isDiagonalsElement==2||isDiagonalsElement==0) diag2Weight=calculateSecondDiagonalWeight(cellArray, dimensionField);
		
		
		
		//Multithtreading
		//Веса строки, столбца и даиагоналей вычисляются в отдельных потоках
		if(isDiagonalsElement==1||isDiagonalsElement==0) {
			
			CompletableFuture<Integer> diag1WeightAsync=CompletableFuture.supplyAsync(()->calculateFirstDiagonalWeight(cellArray, dimensionField),executor);
			
			futuresList.add(diag1WeightAsync);
			
			diag1PresentFlag=true;
		}
			
		if(isDiagonalsElement==2||isDiagonalsElement==0) {
			
			CompletableFuture<Integer> diag2WeightAsync=CompletableFuture.supplyAsync(()->calculateSecondDiagonalWeight(cellArray, dimensionField),executor);
			
			futuresList.add(diag2WeightAsync);
			
			diag2PresentFlag=true;
		}
		
		
		CompletableFuture<Void> allFutures=CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
		
		
		//List<Integer> listInt = futuresList.stream().map(CompletableFuture::join).collect(Collectors.toList());
		
		//it's about exceptions. If one of your futures completed with exception - your code will not wait for completion of all futures. 
		//While allOf will work as expected.
		
		CompletableFuture<List<Integer>> listAllFutures=allFutures.thenApply(v->{return futuresList.stream().map(futuresEx->futuresEx.join()).collect(Collectors.toList());});
				
		List<Integer> listInt=listAllFutures.join();
		
		
		int stringWeight=listInt.get(0);
		
		int columnWeight=listInt.get(1);
		
		if(diag1PresentFlag) diag1Weight=listInt.get(2);
		
		if(diag2PresentFlag&&diag1PresentFlag) diag2Weight=listInt.get(3);
		else if (diag2PresentFlag&&!diag1PresentFlag) diag2Weight=listInt.get(2);
		
		
		
				
		
		if(stringWeight<=columnWeight&&stringWeight<=diag1Weight&&stringWeight<=diag2Weight) 
			
				cellForAIStep=calculateFieldForString(cellArray, dimensionField, lastHumanStepCell.getyCoordinate(),stringWeight);
		
		if(columnWeight<=stringWeight&&columnWeight<=diag1Weight&&columnWeight<=diag2Weight) 
				cellForAIStep=calculateFieldForColumn(cellArray, dimensionField, lastHumanStepCell.getxCoordinate(),columnWeight);
		
		if(diag1Weight<=stringWeight&&diag1Weight<=columnWeight&&diag1Weight<=diag2Weight) 
				cellForAIStep=calculateFieldForFirstDiagonal(cellArray, dimensionField, diag1Weight);
		
		if(diag2Weight<=stringWeight&&diag2Weight<=columnWeight&&diag2Weight<=diag1Weight) 
				cellForAIStep=calculateFieldForSecondDiagonal(cellArray, dimensionField, diag2Weight);
		
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
	}
	
	
	//calculate weight of game field string
		/**
		 * @param array - array with elements of game field
		 * @param dimensionField - dimension of game field
		 * @param i -y-координата ячейки игрового поля с ходом игрока
		 * @return - weight of string
		 * 
		 * Х - играет человек
		 * О - играет машина
		 */
		private static int calculateStringWeight(CellGameField [][] cellArray, int dimensionField, int i) {
			
			int stringWeight =0;
			
			for(int j=0;j<dimensionField;j++) {
				
				if (cellArray[i][j].getCellType()=='O') stringWeight=-100; //выигрышная комбинация в этой строке для человека не достижима
				
				if(cellArray[i][j].getCellType()=='-') stringWeight++;//вес строки определяется количеством доступных для ходов ячеек
			}
			
			return Math.abs(stringWeight);
			
		}
		
		
		/**
		 * @param array - array with elements of game field
		 * @param dimensionField - dimension of game field
		 * @param j -x-координата ячейки игрового поля с ходом игрока
		 * @return - weight of column
		 * 
		 * Х - играет человек
		 * О - играет машина
		 */
		private static int calculateColumnWeight(CellGameField [][] cellArray, int dimensionField, int j) {
			
			int columnWeight=0;
			
			for(int i=0;i<dimensionField;i++) {
				
				if (cellArray[i][j].getCellType()=='O') columnWeight=-100; //выигрышная комбинация в этом столбце для человека не достижима
				
				if(cellArray[i][j].getCellType()=='-') columnWeight++;//вес столбца определяется количеством доступных для ходов ячеек
			}
			
			return Math.abs(columnWeight);
			
			
		}
		
		
		/**
		 * Метод проверяет принадлежность ячейки в которую сделал ход человек диагоналям игрового поля
		 * @param iIndexElement - у -координата ячейки
		 * @param jIndexElement - х - координата игрового поля
		 * @param dimensionField - размерность игрового поля
		 * @return -  "1" - ячейка принадлежит первой диагонали
		 * @return -  "2" - ячейка принадлежит второй диагонали
		 * @return -  "0" - ячейка принадлежит обоим диагоналям
		 * @return -  "-1" - ячейка не принадлежит ни одной из диагоналей
		 */ 
		private static int isDiagonalElement(int iIndexCell, int jIndexCell, int dimensionField) {
			
			boolean firstDiagFlag=false;
			
			boolean secondDiagFlag=false;
			
			if(iIndexCell==jIndexCell) firstDiagFlag=true;
			
			if(jIndexCell==dimensionField-1-iIndexCell) secondDiagFlag=true;
			
			if(firstDiagFlag&&secondDiagFlag) return 0;
			
			if(firstDiagFlag&&!secondDiagFlag) return 1;
			
			if(secondDiagFlag&&!firstDiagFlag) return 2;
				
			return -1;
			
		}
		
		
		/**
		 * Метод возвращает вес диагонали
		 * @param array - массив ячеек игрового поля
		 * @param dimensionField - размерность игрового поля
		 * @return firstDiagWeight - вес первой диагонали
		 */
		private static int calculateFirstDiagonalWeight(CellGameField [][] cellArray, int dimensionField) {
			
			int firstDiagWeight=0;
			
			for(int i=0;i<dimensionField;i++) {
				
				if(cellArray[i][i].getCellType()=='O') firstDiagWeight=-100; //выигрышная комбинация на этой диагонали для человека не достижима
				
				if(cellArray[i][i].getCellType()=='-') firstDiagWeight++;//вес диагонали определяется количеством доступных для ходов ячеек
			}
			
			
			return Math.abs(firstDiagWeight);
			
			
		}
		
		
		/**
		 * Метод возвращает вес диагонали
		 * @param array - массив ячеек игрового поля
		 * @param dimensionField - размерность игрового поля
		 * @return secondDiagWeight - вес второй диагонали
		 */
		private static int calculateSecondDiagonalWeight(CellGameField [][] cellArray, int dimensionField) {
			
			int secondDiagWeight=0;
			
			for(int i=0;i<dimensionField;i++) {
				
				if(cellArray[i][dimensionField-1-i].getCellType()=='O') secondDiagWeight=-100; //выигрышная комбинация на этой диагонали для человека не достижима
				
				if(cellArray[i][dimensionField-1-i].getCellType()=='-') secondDiagWeight++;//вес диагонали определяется количеством доступных для ходов ячеек
			}
			
			
			
			return Math.abs(secondDiagWeight);
		}
		
		
	
		/**
		 * Метод вычисляет ячейку для хода AI в строке
		 * @param cellArray - ячейки составляющие игровое поле
		 * @param dimensionField - размерность поля
		 * @param i y-координата ячейки игрового поля с ходом игрока
		 * @return - CellGameField cellForStepAI - ячейка для хода AI или null если ячеек для хода нет
		 */
		private static CellGameField calculateFieldForString(CellGameField [][] cellArray, int dimensionField, int i, int stringWeight) {
			
			CellGameField cellForStepAI=null;
			
			if(stringWeight<100) {
			
			for(int j=0;j<dimensionField;j++) {
				
				if(cellArray[i][j].getCellType()=='-') {
					
					cellForStepAI = cellArray[i][j];
					
					break;
				 }
				
			   }
				
			} else { //свободных ячеек в строке нет
				
				boolean endSearchFlag=false;
				
				for (int k=0;k<dimensionField;k++) {
					
					for (int j=0;j<dimensionField;j++) {
						
						if(cellArray[k][j].getCellType()=='-') { //находим любую свободную ячейку на поле
							
							cellForStepAI = cellArray[k][j];
							
							endSearchFlag=true;
							
							break;
						 }
						
						if (endSearchFlag) break;
						
					}
				}
				
			}
			
			return cellForStepAI;
			
		}
		
		
		/**
		 * Метод вычисляет ячейку для хода AI в столбце
		 * @param cellArray - ячейки составляющие игровое поле
		 * @param dimensionField - размерность поля
		 * @param j x-координата ячейки игрового поля с ходом игрока
		 * @return - CellGameField cellForStepAI - ячейка для хода AI или null если ячеек для хода нет
		 */
		private static CellGameField calculateFieldForColumn(CellGameField [][] cellArray, int dimensionField, int j, int columnWeight) {
			
			CellGameField cellForStepAI=null;
			
			if(columnWeight<100) {
			
			for(int i=0;i<dimensionField;i++) {
				
				if(cellArray[i][j].getCellType()=='-') {
					
					cellForStepAI = cellArray[i][j];
					
					break;
				}
				
			}
			 
		  } else { //свободных ячеек в столбце нет
			  
			  boolean endSearchFlag=false;
				
				for (int k=0;k<dimensionField;k++) {
					
					for (int s=0;s<dimensionField;s++) {
						
						if(cellArray[k][s].getCellType()=='-') { //находим любую свободную ячейку на поле
							
							cellForStepAI = cellArray[k][s];
							
							endSearchFlag=true;
							
							break;
						 }
						
						if (endSearchFlag) break;
						
					}
			}
		  
		  
		  }
			
			return cellForStepAI;
		}
		
		
		/**
		 * Метод вычисляет ячейку для хода AI в первой диагонали
		 * @param cellArray - ячейки составляющие игровое поле
		 * @param dimensionField -  размерность игрового поля
		 * @param secondDiagonalWeight - вес второй диагонали
		 * @return CellGameField cellForStepAI - ячейка для хода AI или null если ячеек для хода нет
		 */
		private static CellGameField calculateFieldForFirstDiagonal(CellGameField [][] cellArray, int dimensionField, int firstDiagonalWeight) {
			
			CellGameField cellForStepAI=null;
			
			if(firstDiagonalWeight<100) {
			
			for(int i=0;i<dimensionField;i++) {
				
				
				if(cellArray[i][i].getCellType()=='-') {
					
					cellForStepAI = cellArray[i][i];
					
					break;
				}
			 }
			
			
		}	else {
			
			 boolean endSearchFlag=false;
				
				for (int k=0;k<dimensionField;k++) {
					
					for (int s=0;s<dimensionField;s++) {
						
						if(cellArray[k][s].getCellType()=='-') { //находим любую свободную ячейку на поле
							
							cellForStepAI = cellArray[k][s];
							
							endSearchFlag=true;
							
							break;
						 }
						
						if (endSearchFlag) break;
					}
			}
		}
			
			
			
			return cellForStepAI;
			
		}
		
		
		/**
		 * Метод вычисляет ячейку для хода AI во второй диагонали
		 * @param cellArray - ячейки составляющие игровое поле
		 * @param dimensionField -  размерность игрового поля
		 * @param secondDiagonalWeight - вес второй диагонали
		 * @return CellGameField cellForStepAI - ячейка для хода AI или null если ячеек для хода нет
		 */
		private static CellGameField calculateFieldForSecondDiagonal(CellGameField [][] cellArray, int dimensionField, int secondDiagonalWeight) {
			
			CellGameField cellForStepAI=null;
			
			if(secondDiagonalWeight<100) {
			
			for(int i=0;i<dimensionField;i++) {
				
				if(cellArray[i][dimensionField-1-i].getCellType()=='-') {
					
					cellForStepAI = cellArray[i][dimensionField-1-i];
					
					break;
				}
			  }
			
			} else {
				
				 boolean endSearchFlag=false;
					
					for (int k=0;k<dimensionField;k++) {
						
						for (int s=0;s<dimensionField;s++) {
							
							if(cellArray[k][s].getCellType()=='-') { //находим любую свободную ячейку на поле
								
								cellForStepAI = cellArray[k][s];
								
								endSearchFlag=true;
								
								break;
							 }
							
							if (endSearchFlag) break;
						}
				}
				
				
			}
			
			
			return cellForStepAI;
			
		}
	

}
