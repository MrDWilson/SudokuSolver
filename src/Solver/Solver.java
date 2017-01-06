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
	
	public Solver(LinkedList<Tuple<String, Integer>> unsolvedSoduko) {
		unsolved = unsolvedSoduko;
	}
	
	public LinkedList<Tuple<String, Integer>> solve() {
		
		fill();
		initialiseWrong();
		initialiseWrongList();
		
		
		while(!solved()) {
			
			int i = 0;
			for(i=1;i<=81;i++) {
				if(unsolved.get(i-1).getValue() == 0) {
					checkIfReady(i);
					checkNumberForMatch(i);
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
					return true;
				}
				
			}else if(doesContain(getRestOfCols(boxNumber), i, boxNumber) != 0) {
				if(errorAvoider(boxNumber, doesContain(getRestOfCols(boxNumber), i, boxNumber))) {
					unsolved.get(boxNumber-1).setValue(doesContain(getRestOfCols(boxNumber), i, boxNumber));
					System.out.println("Entered: " + boxNumber + ", value: " + doesContain(getRestOfCols(boxNumber), i, boxNumber));
					fillBoxOnFinish(boxNumber, doesContain(getRestOfRows(boxNumber), i, boxNumber));
					return true;
				}
				
			} else if(doesContain(getRestOfBox(boxNumber), i, boxNumber) != 0) {
				if(errorAvoider(boxNumber,doesContain(getRestOfBox(boxNumber), i, boxNumber))) {
					unsolved.get(boxNumber-1).setValue(doesContain(getRestOfBox(boxNumber), i, boxNumber));
					System.out.println("Entered: " + boxNumber + ", value: " + doesContain(getRestOfBox(boxNumber), i, boxNumber));
					fillBoxOnFinish(boxNumber, doesContain(getRestOfRows(boxNumber), i, boxNumber));
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
