package Solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import Resources.Tuple;

public class Solver {

	static LinkedList<Tuple<String, Integer>> unsolved = new LinkedList<>();
	static LinkedList<String> listOfBoxes = new LinkedList<>();
	
	static LinkedList<LinkedList<Integer>> wrongNumbers = new LinkedList<>();
	
	//Experimental
	static LinkedList<LinkedList<Integer>> potentialNumbers = new LinkedList<>();
	

	public Solver(LinkedList<Tuple<String, Integer>> unsolvedSoduko) {
		unsolved = unsolvedSoduko;
	}
	
	public LinkedList<Tuple<String, Integer>> solve() {
		
		fill();
		initialiseWrong();
		initialiseWrongList();

		initialisePotential();
		checkPotential();
		checkForSingle();

		
		
		while(!solved()) {
			
			int i = 0;

			for(i=0;i<81;i++) {
				if(unsolved.get(i).getValue() == 0) {
					checkIfReady(i+1);
					checkNumberForMatch(i+1);
					boxLineReduction();
					nakedPair();
					nakedTriple();
					
					checkForSingle();
					
					//Error tracker
					

					//System.out.println("Trying box: " + i);
					//System.out.println(wrongNumbers.get(i-1).toString());
				}else {/*System.out.println("Filled");*/}
			}

			
			

		}
		return unsolved;
	}
			
			
	
	public static Boolean solved() {
		int i = 0;
		for(i=0;i<81;i++) {
			if(unsolved.get(i).getValue() == 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void fill() {
		int i = 0;
		for(i=0;i<81;i++) {
			listOfBoxes.add(String.valueOf(i));
		}
	}
	
	public static void initialiseWrong() {
		int i = 0;
		for(i=0;i<81;i++) {
			wrongNumbers.add(new LinkedList<>());

			potentialNumbers.add(new LinkedList<>());

		}
	}
	
	public static void checkNumberForMatch(int boxNumber) {
		int i = 0;
		List<Integer> rows = getRestOfRows(boxNumber);
		List<Integer> cols = getRestOfCols(boxNumber);
		List<Integer> box = getRestOfBox(boxNumber);
		for(i=0;i<9;i++) {
			int num = rows.get(i);
			if(unsolved.get(num-1).equals(boxNumber)) {}else if(unsolved.get(num-1).getValue() != 0) {
				if(!wrongNumbers.get(boxNumber-1).contains(unsolved.get(num-1).getValue())) {
					wrongNumbers.get(boxNumber-1).add(unsolved.get(num-1).getValue());
					//System.out.println("Added: " + i);
				}
			}
		}
		
		for(i=0;i<9;i++) {
			int num = cols.get(i);
			if(unsolved.get(num-1).equals(boxNumber)) {
			}else if(unsolved.get(num-1).getValue() != 0) {
				if(!wrongNumbers.get(boxNumber-1).contains(unsolved.get(num-1).getValue())) {
					wrongNumbers.get(boxNumber-1).add(unsolved.get(num-1).getValue());
					//System.out.println("Added: " + i);
				}
			}
		}
		
		for(i=0;i<9;i++) {
			int num = box.get(i);
			if(unsolved.get(num-1).equals(boxNumber)) {
			}else if(unsolved.get(num-1).getValue() != 0) {
				if(!wrongNumbers.get(boxNumber-1).contains(unsolved.get(num-1).getValue())) {
					wrongNumbers.get(boxNumber-1).add(unsolved.get(num-1).getValue());
					//System.out.println("Added: " + i);
				}
			}
		}
		
		
	}
	
	public static boolean checkIfReady(int boxNumber) {
		int i = 0;
		for(i=1;i<10;i++) {
			if(doesContain(getRestOfRows(boxNumber), i, boxNumber) != 0) {
				if(errorAvoider(boxNumber, doesContain(getRestOfRows(boxNumber), i, boxNumber))) {
					unsolved.get(boxNumber-1).setValue(doesContain(getRestOfRows(boxNumber), i, boxNumber));
					System.out.println("Entered: " + boxNumber + ", value: " + doesContain(getRestOfRows(boxNumber), i, boxNumber));
					fillBoxOnFinish(boxNumber, doesContain(getRestOfRows(boxNumber), i, boxNumber));

					checkPotential();

					return true;
				}
				
			}else if(doesContain(getRestOfCols(boxNumber), i, boxNumber) != 0) {
				if(errorAvoider(boxNumber, doesContain(getRestOfCols(boxNumber), i, boxNumber))) {
					unsolved.get(boxNumber-1).setValue(doesContain(getRestOfCols(boxNumber), i, boxNumber));
					System.out.println("Entered: " + boxNumber + ", value: " + doesContain(getRestOfCols(boxNumber), i, boxNumber));
					fillBoxOnFinish(boxNumber, doesContain(getRestOfRows(boxNumber), i, boxNumber));

					checkPotential();

					return true;
				}
				
			} else if(doesContain(getRestOfBox(boxNumber), i, boxNumber) != 0) {
				if(errorAvoider(boxNumber,doesContain(getRestOfBox(boxNumber), i, boxNumber))) {
					unsolved.get(boxNumber-1).setValue(doesContain(getRestOfBox(boxNumber), i, boxNumber));
					System.out.println("Entered: " + boxNumber + ", value: " + doesContain(getRestOfBox(boxNumber), i, boxNumber));
					fillBoxOnFinish(boxNumber, doesContain(getRestOfRows(boxNumber), i, boxNumber));

					checkPotential();

					return true;
				}
				
			} 
		}
		
		return false;
	}
	
	public static void initialiseWrongList() {
		int i = 0;
		int j = 0;
		for(i=0;i<81;i++) {
			if(unsolved.get(i).getValue() != 0) {
				List<Integer> temp = new ArrayList<>();
				for(j=1;j<10;j++) {
					if(j != unsolved.get(i).getValue()) {
						temp.add(j);
						//System.out.println("Added :" + j);
					}
				}
				wrongNumbers.get(i).addAll(temp);
			}

			/*if(unsolved.get(i).getValue() != 0) {
				List<Integer> temp = new ArrayList<>();
				for(j=1;j<10;j++) {
					if(j == unsolved.get(i).getValue()) {
						temp.add(j);
					}
				}
				potentialNumbers.get(i).addAll(temp);
			}*/
		}
	}
	
	public static void initialisePotential() {
		
		for(int i=0; i<81;i++) {
			potentialNumbers.get(i).addAll(Arrays.asList(1,2,3,4,5,6,7,8,9));
		}
		
		for(int i=1;i<82;i++) {
			if(unsolved.get(i-1).getValue() != 0) {
				List<Integer> rows = getRestOfRows(i);
				List<Integer> cols = getRestOfCols(i);
				List<Integer> box = getRestOfBox(i);
				for(int j=0;j<9;j++) {
					if(rows.get(j) != i) {
						if(potentialNumbers.get(rows.get(j)-1).contains(unsolved.get(i-1).getValue())) {
							potentialNumbers.get(rows.get(j)-1).remove(unsolved.get(i-1).getValue());
						}
						
					} //else { potentialNumbers.get(rows.get(j)).clear(); }
				}
				for(int j=0;j<9;j++) {
					if(cols.get(j) != i) {
						if(potentialNumbers.get(cols.get(j)-1).contains(unsolved.get(i-1).getValue())) {
							potentialNumbers.get(cols.get(j)-1).remove(unsolved.get(i-1).getValue());
						}
						
					} //else { potentialNumbers.get(cols.get(j)).clear(); }
				}
				for(int j=0;j<9;j++) {
					if(box.get(j) != i) {
						if(potentialNumbers.get(box.get(j)-1).contains(unsolved.get(i-1).getValue())) {
							potentialNumbers.get(box.get(j)-1).remove(unsolved.get(i-1).getValue());
						}
						
					} //else { potentialNumbers.get(box.get(j)).clear(); }
				}
				if(!potentialNumbers.get(i-1).isEmpty()) {
					potentialNumbers.get(i-1).clear();
				}
				
			}
		}
		
	}
	
	public static void checkPotential() {
		
		for(int i=1;i<82;i++) {
			if(unsolved.get(i-1).getValue() != 0) {
				List<Integer> rows = getRestOfRows(i);
				List<Integer> cols = getRestOfCols(i);
				List<Integer> box = getRestOfBox(i);
				for(int j=0;j<9;j++) {
					if(rows.get(j) != i) {
						if(potentialNumbers.get(rows.get(j)-1).contains(unsolved.get(i-1).getValue())) {
							potentialNumbers.get(rows.get(j)-1).remove(unsolved.get(i-1).getValue());
						}
						
					} //else { potentialNumbers.get(rows.get(j)).clear(); }
				}
				for(int j=0;j<9;j++) {
					if(cols.get(j) != i) {
						if(potentialNumbers.get(cols.get(j)-1).contains(unsolved.get(i-1).getValue())) {
							potentialNumbers.get(cols.get(j)-1).remove(unsolved.get(i-1).getValue());
						}
						
					} //else { potentialNumbers.get(cols.get(j)).clear(); }
				}
				for(int j=0;j<9;j++) {
					if(box.get(j) != i) {
						if(potentialNumbers.get(box.get(j)-1).contains(unsolved.get(i-1).getValue())) {
							potentialNumbers.get(box.get(j)-1).remove(unsolved.get(i-1).getValue());
						}
						
					} //else { potentialNumbers.get(box.get(j)).clear(); }
				}
				if(!potentialNumbers.get(i-1).isEmpty()) {
					potentialNumbers.get(i-1).clear();
				}
				
				//System.out.println("Removed: " + i + ", value: " + unsolved.get(i-1).getValue());
				
			}
		}
		
	}
	
	public static void nakedPair() {
		List<Integer> row = Arrays.asList(1,10,19,28,37,46,55,64,73);
		List<Integer> col = Arrays.asList(1,2,3,4,5,6,7,8,9);
		List<Integer> box = Arrays.asList(1,4,7,28,31,34,55,58,61);
		for(int i=0;i<9;i++) {
			List<Integer> rows = getRestOfRows(row.get(i));
			for(int j = 0; j<9;j++) {
				if(potentialNumbers.get(rows.get(j)-1).size() == 2) {
					for(int k=0;k<9;k++) {
						if(rows.get(k) != rows.get(j)) {
							if(potentialNumbers.get(rows.get(k)-1).size() == 2) {
								if(potentialNumbers.get(rows.get(k)-1).equals(potentialNumbers.get(rows.get(j)-1))) {
									removePair(
											rows.get(j), 
											rows.get(k), 
											potentialNumbers.get(rows.get(j)-1).getFirst(), 
											potentialNumbers.get(rows.get(j)-1).getLast(), 
											1
											);
//									System.out.println("Naked pair.");
								}
							}
						}
					}
				}
			}
		}
		for(int i=0;i<9;i++) {
			List<Integer> cols = getRestOfCols(col.get(i));
			for(int j = 0; j<9;j++) {
				if(potentialNumbers.get(cols.get(j)-1).size() == 2) {
					for(int k=0;k<9;k++) {
						if(cols.get(k) != cols.get(j)) {
							if(potentialNumbers.get(cols.get(k)-1).size() == 2) {
								if(potentialNumbers.get(cols.get(k)-1).equals(potentialNumbers.get(cols.get(j)-1))) {
									removePair(
											cols.get(j), 
											cols.get(k), 
											potentialNumbers.get(cols.get(j)-1).getFirst(), 
											potentialNumbers.get(cols.get(j)-1).getLast(), 
											2
											);
//									System.out.println("Naked pair.");
								}
							}
						}
					}
				}
			}
		}
		for(int i=0;i<9;i++) {
			List<Integer> boxs = getRestOfRows(box.get(i));
			for(int j = 0; j<9;j++) {
				if(potentialNumbers.get(boxs.get(j)-1).size() == 2) {
					for(int k=0;k<9;k++) {
						if(boxs.get(k) != boxs.get(j)) {
							if(potentialNumbers.get(boxs.get(k)-1).size() == 2) {
								if(potentialNumbers.get(boxs.get(k)-1).equals(potentialNumbers.get(boxs.get(j)-1))) {
									removePair(
											boxs.get(j), 
											boxs.get(k), 
											potentialNumbers.get(boxs.get(j)-1).getFirst(), 
											potentialNumbers.get(boxs.get(j)-1).getLast(), 
											3
											);
//									System.out.println("Naked pair.");
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void removePair(int boxOne, int boxTwo, int val1, int val2, int op) {
		if(op == 1) {
			List<Integer> row = getRestOfRows(boxOne);
			for(int i = 0;i<9;i++) {
				if(row.get(i) != boxOne && row.get(i) != boxTwo && row.contains(boxOne) && row.contains(boxTwo)) {
					if(potentialNumbers.get(row.get(i)-1).contains(val1)) {
						potentialNumbers.get(row.get(i)-1).removeFirstOccurrence(val1);
					} 
					if(potentialNumbers.get(row.get(i)-1).contains(val2)) {
						potentialNumbers.get(row.get(i)-1).removeFirstOccurrence(val2);
					}
				}
			}
		}else if(op == 2) {
			List<Integer> col = getRestOfCols(boxOne);
			for(int i = 0;i<9;i++) {
				if(col.get(i) != boxOne && col.get(i) != boxTwo && col.contains(boxOne) && col.contains(boxTwo)) {
					if(potentialNumbers.get(col.get(i)-1).contains(val1)) {
						potentialNumbers.get(col.get(i)-1).removeFirstOccurrence(val1);
					} 
					if(potentialNumbers.get(col.get(i)-1).contains(val2)) {
						potentialNumbers.get(col.get(i)-1).removeFirstOccurrence(val2);
					}
				}
			}
			
		}else if (op== 3) {
			List<Integer> box = getRestOfBox(boxOne);
			for(int i = 0;i<9;i++) {
				if(box.get(i) != boxOne && box.get(i) != boxTwo && box.contains(boxOne) && box.contains(boxTwo)) {
					if(potentialNumbers.get(box.get(i)-1).contains(val1)) {
						potentialNumbers.get(box.get(i)-1).removeFirstOccurrence(val1);
					} 
					if(potentialNumbers.get(box.get(i)-1).contains(val2)) {
						potentialNumbers.get(box.get(i)-1).removeFirstOccurrence(val2);
					}
				}
			}
			
		}
	}
	
	public static void nakedTriple() {
		
			List<Integer> row = Arrays.asList(1,10,19,28,37,46,55,64,73);
			List<Integer> col = Arrays.asList(1,2,3,4,5,6,7,8,9);
			List<Integer> box = Arrays.asList(1,4,7,28,31,34,55,58,61);
			for(int i=0;i<9;i++) {
				List<Integer> rows = getRestOfRows(row.get(i));
				for(int j = 0; j<9;j++) {
					if(potentialNumbers.get(rows.get(j)-1).size() == 3) {
						for(int k=0;k<9;k++) {
							if(rows.get(k) != rows.get(j)) {
								if(potentialNumbers.get(rows.get(k)-1).size() == 3) {
									for(int a=0;a<9;a++) {
										if(rows.get(k) != rows.get(a) && rows.get(j) != rows.get(a)) {
											if(potentialNumbers.get(rows.get(a)-1).size() == 3) {
												if(potentialNumbers.get(rows.get(k)-1).equals(potentialNumbers.get(rows.get(j)-1))) {
													if(potentialNumbers.get(rows.get(k)-1).equals(potentialNumbers.get(rows.get(a)-1))) {
														removeTriple(
																rows.get(j), 
																rows.get(k), 
																rows.get(a),
																potentialNumbers.get(rows.get(j)-1).getFirst(), 
																potentialNumbers.get(rows.get(j)-1).get(1), 
																potentialNumbers.get(rows.get(a)-1).getLast(),
																1
																);
														System.out.println("Naked Triple.");
													}
												}
											}
										}
									}
									
								}
							}
						}
					}
				}
			}
			for(int i=0;i<9;i++) {
				List<Integer> cols = getRestOfCols(col.get(i));
				for(int j = 0; j<9;j++) {
					if(potentialNumbers.get(cols.get(j)-1).size() == 3) {
						for(int k=0;k<9;k++) {
							if(cols.get(k) != cols.get(j)) {
								if(potentialNumbers.get(cols.get(k)-1).size() == 3) {
									for(int a=0;a<9;a++) {
										if(cols.get(k) != cols.get(a) && cols.get(j) != cols.get(a)) {
											if(potentialNumbers.get(cols.get(a)-1).size() == 3) {
												if(potentialNumbers.get(cols.get(k)-1).equals(potentialNumbers.get(cols.get(j)-1))) {
													if(potentialNumbers.get(cols.get(k)-1).equals(potentialNumbers.get(cols.get(a)-1))) {
														removeTriple(
																cols.get(j), 
																cols.get(k), 
																cols.get(a),
																potentialNumbers.get(cols.get(j)-1).getFirst(), 
																potentialNumbers.get(cols.get(j)-1).get(1), 
																potentialNumbers.get(cols.get(a)-1).getLast(),
																2
																);
														System.out.println("Naked Triple.");
													}
												}
											}
										}
									}
									
								}
							}
						}
					}
				}
			}
			for(int i=0;i<9;i++) {
				List<Integer> boxs = getRestOfRows(box.get(i));
				for(int j = 0; j<9;j++) {
					if(potentialNumbers.get(boxs.get(j)-1).size() == 3) {
						for(int k=0;k<9;k++) {
							if(boxs.get(k) != boxs.get(j)) {
								if(potentialNumbers.get(boxs.get(k)-1).size() == 3) {
									for(int a=0;a<9;a++) {
										if(boxs.get(k) != boxs.get(a) && boxs.get(j) != boxs.get(a)) {
											if(potentialNumbers.get(boxs.get(a)-1).size() == 3) {
												if(potentialNumbers.get(boxs.get(k)-1).equals(potentialNumbers.get(boxs.get(j)-1))) {
													if(potentialNumbers.get(boxs.get(k)-1).equals(potentialNumbers.get(boxs.get(a)-1))) {
														removeTriple(
																boxs.get(j), 
																boxs.get(k), 
																boxs.get(a),
																potentialNumbers.get(boxs.get(j)-1).getFirst(), 
																potentialNumbers.get(boxs.get(j)-1).get(1), 
																potentialNumbers.get(boxs.get(a)-1).getLast(),
																3
																);
														System.out.println("Naked Triple.");
													}
												}
											}
										}
									}
									
								}
							}
						}
					}
				}
			}
		
	}
	
	public static void removeTriple(int boxOne, int boxTwo, int boxThree, int val1, int val2, int val3, int op) {
		if(op == 1) {
			List<Integer> row = getRestOfRows(boxOne);
			for(int i = 0;i<9;i++) {
				if(row.get(i) != boxOne && row.get(i) != boxTwo && row.get(i) != boxThree && row.contains(boxOne) && row.contains(boxTwo) && row.contains(boxThree)) {
					if(potentialNumbers.get(row.get(i)-1).contains(val1)) {
						potentialNumbers.get(row.get(i)-1).removeFirstOccurrence(val1);
					} 
					if(potentialNumbers.get(row.get(i)-1).contains(val2)) {
						potentialNumbers.get(row.get(i)-1).removeFirstOccurrence(val2);
					}
					if(potentialNumbers.get(row.get(i)-1).contains(val3)) {
						potentialNumbers.get(row.get(i)-1).removeLastOccurrence(val3);
					}
				}
			}
		}else if(op == 2) {
			List<Integer> col = getRestOfCols(boxOne);
			for(int i = 0;i<9;i++) {
				if(col.get(i) != boxOne && col.get(i) != boxTwo && col.get(i) != boxThree && col.contains(boxOne) && col.contains(boxTwo) && col.contains(boxThree)) {
					if(potentialNumbers.get(col.get(i)-1).contains(val1)) {
						potentialNumbers.get(col.get(i)-1).removeFirstOccurrence(val1);
					} 
					if(potentialNumbers.get(col.get(i)-1).contains(val2)) {
						potentialNumbers.get(col.get(i)-1).removeFirstOccurrence(val2);
					}
					if(potentialNumbers.get(col.get(i)-1).contains(val3)) {
						potentialNumbers.get(col.get(i)-1).removeFirstOccurrence(val3);
					}
				}
			}
			
		}else if (op== 3) {
			List<Integer> box = getRestOfBox(boxOne);
			for(int i = 0;i<9;i++) {
				if(box.get(i) != boxOne && box.get(i) != boxTwo && box.get(i) != boxThree && box.contains(boxOne) && box.contains(boxTwo) && box.contains(boxThree)) {
					if(potentialNumbers.get(box.get(i)-1).contains(val1)) {
						potentialNumbers.get(box.get(i)-1).removeFirstOccurrence(val1);
					} 
					if(potentialNumbers.get(box.get(i)-1).contains(val2)) {
						potentialNumbers.get(box.get(i)-1).removeFirstOccurrence(val2);
					}
					if(potentialNumbers.get(box.get(i)-1).contains(val3)){
						potentialNumbers.get(box.get(i)-1).removeFirstOccurrence(val3);
					}
				}
			}
			
		}
		
	}
	
	public static void checkForSingle() {
		for(int i=0;i<81;i++) {
			if(unsolved.get(i).getValue() == 0) {
				if(potentialNumbers.get(i).size() == 1) {
					if(errorAvoider(i+1, potentialNumbers.get(i).getFirst())) {
						int temp = potentialNumbers.get(i).getFirst();
						unsolved.get(i).setValue(temp);
						fillBoxOnFinish(i+1, temp);
						System.out.println("Entered 2.0: " + (i+1) + ", value: " + temp);
						//System.out.println(potentialNumbers.get(i).toString());
						checkPotential();
					}
				}
			}
			
			

		}
	}
	
	public static void boxLineReduction() {
		
		for(int i = 0;i<9;i++) {
			
			switch(i) {
			case 0: boxLine(1,2,3,1,2,3,1,1,1);
			case 1: boxLine(1,2,3,4,5,6,1,4,2);
			case 2: boxLine(1,2,3,7,8,9,1,7,3);
			case 3: boxLine(4,5,6,1,2,3,4,1,4);
			case 4: boxLine(4,5,6,4,5,6,4,4,5);
			case 5: boxLine(4,5,6,7,8,9,4,7,6);
			case 6: boxLine(7,8,9,1,2,3,7,1,7);
			case 7: boxLine(7,8,9,4,5,6,7,4,8);
			case 8: boxLine(7,8,9,7,8,9,7,7,9);
			}
			
		}
		
	}
	
	public static void boxLine(
			int rowOne, 
			int rowTwo, 
			int rowThree, 
			int colOne, 
			int colTwo, 
			int colThree, 
			int rowEx, 
			int colEx, 
			int boxNumber
			) {
		
		List<Integer> row = Arrays.asList(1,10,19,28,37,46,55,64,73);
		List<Integer> col = Arrays.asList(1,2,3,4,5,6,7,8,9);
		List<Integer> box = Arrays.asList(1,4,7,28,31,34,55,58,61);
		
		List<Integer> rowOneFull = getRestOfRows(row.get(rowOne-1));
		List<Integer> rowTwoFull = getRestOfRows(row.get(rowTwo-1));
		List<Integer> rowThreeFull = getRestOfRows(row.get(rowThree-1));
		
		List<Integer> colOneFull = getRestOfCols(col.get(colOne-1));
		List<Integer> colTwoFull = getRestOfCols(col.get(colTwo-1));
		List<Integer> colThreeFull = getRestOfCols(col.get(colThree-1));
		
		List<Integer> rowOneBox = new ArrayList<>();
		rowOneBox.add(rowEx-1);
		rowOneBox.add(rowEx);
		rowOneBox.add(rowEx+1);
		rowOneFull.remove(rowEx-1);
		rowOneFull.remove(rowEx-1);
		rowOneFull.remove(rowEx-1);
		
		List<Integer> rowTwoBox = new ArrayList<>();
		rowTwoBox.add(rowEx-1);
		rowTwoBox.add(rowEx);
		rowTwoBox.add(rowEx+1);
		rowTwoFull.remove(rowEx-1);
		rowTwoFull.remove(rowEx-1);
		rowTwoFull.remove(rowEx-1);
		
		List<Integer> rowThreeBox = new ArrayList<>();
		rowThreeBox.add(rowEx-1);
		rowThreeBox.add(rowEx);
		rowThreeBox.add(rowEx+1);
		rowThreeFull.remove(rowEx-1);
		rowThreeFull.remove(rowEx-1);
		rowThreeFull.remove(rowEx-1);
		
		List<Integer> colOneBox = new ArrayList<>();
		colOneBox.add(colEx-1);
		colOneBox.add(colEx);
		colOneBox.add(colEx+1);
		colOneFull.remove(colEx-1);
		colOneFull.remove(colEx-1);
		colOneFull.remove(colEx-1);
		
		List<Integer> colTwoBox = new ArrayList<>();
		colTwoBox.add(colEx-1);
		colTwoBox.add(colEx);
		colTwoBox.add(colEx+1);
		colTwoFull.remove(colEx-1);
		colTwoFull.remove(colEx-1);
		colTwoFull.remove(colEx-1);
		
		List<Integer> colThreeBox = new ArrayList<>();
		colThreeBox.add(colEx-1);
		colThreeBox.add(colEx);
		colThreeBox.add(colEx+1);
		colThreeFull.remove(colEx-1);
		colThreeFull.remove(colEx-1);
		colThreeFull.remove(colEx-1);
		
		
		checkForRed(rowOneFull, rowOneBox, boxNumber);
		checkForRed(rowTwoFull, rowTwoBox, boxNumber);
		checkForRed(rowThreeFull, rowThreeBox, boxNumber);
		checkForRed(colOneFull, colOneBox, boxNumber);
		checkForRed(colTwoFull, colTwoBox, boxNumber);
		checkForRed(colThreeFull, colThreeBox, boxNumber);
	}
	
	public static int checkForRed(List<Integer> rowCol, List<Integer> box, int boxNumber) {
		for(int i=1;i<10;i++) {
			for(int k=0;k<6;k++) {
				if(potentialNumbers.get(rowCol.get(k)-1).contains(i) || unsolved.get(rowCol.get(k)-1).getValue() == i) {
					return 0;
				} else if(!potentialNumbers.get(rowCol.get(k)-1).contains(i) && k == 5 && unsolved.get(rowCol.get(k)-1).getValue() != i) {
					for(int z = 0; z<3;z++) {
						if(potentialNumbers.get(box.get(z)).contains(i)) {
							clearRed(i, boxNumber, box);
						}
					}
				}
			}
		}
		return 1;
	}
	
	public static void clearRed(int number, int boxNumber, List<Integer> excluded) {
		
		List<Integer> box = getRestOfBox(boxNumber);
		
		for(int i=0;i<9;i++) {
			if(!excluded.contains(box.get(i))) {
				if(potentialNumbers.get(box.get(i)-1).contains(number)) {
					potentialNumbers.get(box.get(i)-1).removeFirstOccurrence(number);
					System.out.println("Reduction cleared.");
					System.out.println("Box: " + (box.get(i)) + ", value: " + number);
				}
			}
		}
		
	}
	
	public static List<Integer> getRestOfRows(int boxNumber) {
		List<Integer> row1 = Arrays.asList(1,2,3,4,5,6,7,8,9);
		List<Integer> row2 = Arrays.asList(10,11,12,13,14,15,16,17,18);
		List<Integer> row3 = Arrays.asList(19,20,21,22,23,24,25,26,27);
		List<Integer> row4 = Arrays.asList(28,29,30,31,32,33,34,35,36);
		List<Integer> row5 = Arrays.asList(37,38,39,40,41,42,43,44,45);
		List<Integer> row6 = Arrays.asList(46,47,48,49,50,51,52,53,54);
		List<Integer> row7 = Arrays.asList(55,56,57,58,59,60,61,62,63);
		List<Integer> row8 = Arrays.asList(64,65,66,67,68,69,70,71,72);
		List<Integer> row9 = Arrays.asList(73,74,75,76,77,78,79,80,81);
		
		List<Integer> selected = new ArrayList<>();
		
		if(row1.contains(boxNumber)) {
			selected.addAll(row1);
		}else if(row2.contains(boxNumber)) {
			selected.addAll(row2);
		}else if(row3.contains(boxNumber)) {
			selected.addAll(row3);
		}else if(row4.contains(boxNumber)) {
			selected.addAll(row4);
		}else if(row5.contains(boxNumber)) {
			selected.addAll(row5);
		}else if(row6.contains(boxNumber)) {
			selected.addAll(row6);
		}else if(row7.contains(boxNumber)) {
			selected.addAll(row7);
		}else if(row8.contains(boxNumber)) {
			selected.addAll(row8);
		}else if(row9.contains(boxNumber)) {
			selected.addAll(row9);
		}
		return selected;
	}
	
	public static List<Integer> getRestOfCols(int boxNumber) {
		List<Integer> col1 = Arrays.asList(1,10,19,28,37,46,55,64,73);
		List<Integer> col2 = Arrays.asList(2,11,20,29,38,47,56,65,74);
		List<Integer> col3 = Arrays.asList(3,12,21,30,39,48,57,66,75);
		List<Integer> col4 = Arrays.asList(4,13,22,31,40,49,58,67,76);
		List<Integer> col5 = Arrays.asList(5,14,23,32,41,50,59,68,77);
		List<Integer> col6 = Arrays.asList(6,15,24,33,42,51,60,69,78);
		List<Integer> col7 = Arrays.asList(7,16,25,34,43,52,61,70,79);
		List<Integer> col8 = Arrays.asList(8,17,26,35,44,53,62,71,80);
		List<Integer> col9 = Arrays.asList(9,18,27,36,45,54,63,72,81);
		
		List<Integer> selected = new ArrayList<>();
		
		if(col1.contains(boxNumber)) {
			selected.addAll(col1);
		}else if(col2.contains(boxNumber)) {
			selected.addAll(col2);
		}else if(col3.contains(boxNumber)) {
			selected.addAll(col3);
		}else if(col4.contains(boxNumber)) {
			selected.addAll(col4);
		}else if(col5.contains(boxNumber)) {
			selected.addAll(col5);
		}else if(col6.contains(boxNumber)) {
			selected.addAll(col6);
		}else if(col7.contains(boxNumber)) {
			selected.addAll(col7);
		}else if(col8.contains(boxNumber)) {
			selected.addAll(col8);
		}else if(col9.contains(boxNumber)) {
			selected.addAll(col9);
		}
		
		return selected;
	}
	
	public static List<Integer> getRestOfBox(int boxNumber) {
		List<Integer> box1 = Arrays.asList(1,2,3,10,11,12,19,20,21);
		List<Integer> box2 = Arrays.asList(4,5,6,13,14,15,22,23,24);
		List<Integer> box3 = Arrays.asList(7,8,9,16,17,18,25,26,27);
		List<Integer> box4 = Arrays.asList(28,29,30,37,38,39,46,47,48);
		List<Integer> box5 = Arrays.asList(31,32,33,40,41,42,49,50,51);
		List<Integer> box6 = Arrays.asList(34,35,36,43,44,45,52,53,54);
		List<Integer> box7 = Arrays.asList(55,56,57,64,65,66,73,74,75);
		List<Integer> box8 = Arrays.asList(58,59,60,67,68,69,76,77,78);
		List<Integer> box9 = Arrays.asList(61,62,63,70,71,72,79,80,81);
		
		List<Integer> selected = new ArrayList<>();
		
		if(box1.contains(boxNumber)) {
			selected.addAll(box1);
		}else if(box2.contains(boxNumber)) {
			selected.addAll(box2);
		}else if(box3.contains(boxNumber)) {
			selected.addAll(box3);
		}else if(box4.contains(boxNumber)) {
			selected.addAll(box4);
		}else if(box5.contains(boxNumber)) {
			selected.addAll(box5);
		}else if(box6.contains(boxNumber)) {
			selected.addAll(box6);
		}else if(box7.contains(boxNumber)) {
			selected.addAll(box7);
		}else if(box8.contains(boxNumber)) {
			selected.addAll(box8);
		}else if(box9.contains(boxNumber)) {
			selected.addAll(box9);
		}
		
		return selected;
	}
	
	public static int doesContain(List<Integer> list, int num, int boxNumber) {
		
		for(int in : list) {
			if(in == boxNumber) {} else if(!wrongNumbers.get(in-1).contains(num)) {
				return 0;
			}
		}
		//System.out.println(wrongNumbers.toString());
		return num;
	}
	
	public String toString() {
		String print = "";
		/*for(i=1;i<=81;i++) {
			if*i-1 == 0 || i-1 == 10 || i-1 == 19 || i-1 == 28 || i-1 == 37 || i-1 == 46 || i-1 == 55 || i-1 == 64 || i-1 == 73){
				print+= "\n";
			}else {
				print+="\t"+unsolved.get(i-1).getValue();
			}
			//print+=" "+unsolved.get(i-1).getValue();
		}*/
		
		StringBuilder sb = new StringBuilder();
		 //sb.append(unsolved.get(0).getValue()).append(" ");
		 for (int i1 = 1 ; i1 <= 81; i1++) {
		      sb.append(unsolved.get(i1-1).getValue()).append(" ");
		      if ( (i1) % 9 == 0 ) { sb.append("\n"); }
		 }
		// System.out.print(sb.toString());
		
		return sb.toString(); //+ ", count: " + print.length();
		
		/*int br = 8;
		for (int i = 1; i < 82; i++) {
		    //System.out.print(list[i] + " ");
		    print+= unsolved.get(i-1).getValue() + " ";
		    if (i % br == (br - 1))
		        System.out.println();
		}
		return print;
	}*/
	}
	
	public static void fillBoxOnFinish(int boxNumber, int value) {
		for(int i = 1; i<10; i++) {
			if(!wrongNumbers.get(boxNumber-1).contains(i)) {
				if(i != value) {
					wrongNumbers.get(boxNumber-1).add(i);
				}
			}
		}
	}
	
	public static boolean errorAvoider(int boxNumber, int value) {
		List<Integer> rows = getRestOfRows(boxNumber);
		List<Integer> cols = getRestOfCols(boxNumber);
		List<Integer> box = getRestOfBox(boxNumber);
		
		for(int i=0; i<9;i++) {
			if(!rows.get(i).equals(boxNumber)) {
				if(unsolved.get(rows.get(i)-1).getValue().equals(value)) {
					return false;
				}
			}
		}
		
		for(int i=0; i<9;i++) {
			if(!cols.get(i).equals(boxNumber)) {
				if(unsolved.get(cols.get(i)-1).getValue().equals(value)) {
					return false;
				}
			}
		}
		
		for(int i=0; i<9;i++) {
			if(!box.get(i).equals(boxNumber)) {
				if(unsolved.get(box.get(i)-1).getValue().equals(value)) {
					return false;
				}
			}
		}
		
		return true;
	}
}
