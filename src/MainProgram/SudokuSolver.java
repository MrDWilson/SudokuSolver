package MainProgram;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import Resources.Tuple;
import Solver.Solver;

/*
 * This is the main program that will deal with the input and passing that input to the handlers
 * and output.
 */

public class SudokuSolver {

	public static void main(String[] args) {
		
		Scanner reader = new Scanner(System.in);
		System.out.println("Please enter soduko a row at a time, with empty boxes as 0s.");
		ArrayList<Long> sodukoUnsolved = new ArrayList<Long>();
		
		int i;
		for(i=1;i<10;i++) {
			System.out.println("Please enter row " + i + ":");
			long temp = reader.nextLong();
			String format = String.format("%09d", temp);
			if(String.valueOf(format).length() != 9) {
				System.err.println("The row has to be 9 digits long.");
				System.exit(0);
			}
			System.out.println(format);
			sodukoUnsolved.add(Long.parseLong(format));
		}
		
		
		reader.close();
		
		LinkedList<LinkedList<Long>> allRows = new LinkedList<LinkedList<Long>>();
		
		for(i=0;i<9;i++) {
			allRows.add(getRow(sodukoUnsolved.get(i)));
		}
		
		int j = 0;
		
		LinkedList<Tuple<String, Integer>> sodukoReady = new LinkedList<>();
		int number = 1;
		for(i=1;i<10;i++) {
			LinkedList<Long> currentList = allRows.pop();
			for(j=1;j<10;j++) {
				Long currentLong = currentList.pop();
				sodukoReady.add(new Tuple<String, Integer>(String.valueOf(number), currentLong.intValue()));
				number+= 1;
			}
		}
		
		Solver solved = new Solver(sodukoReady);
		
		solved.solve();
		
		System.out.println(solved.toString());
		//Call soduko solver here on Sodukounsolved
		

	}
	
	public static LinkedList<Long> getRow(Long row) {
		LinkedList<Long> stack = new LinkedList<Long>();
		int i = 0;
		String test = String.valueOf(String.format("%09d", row));
		for(i=8;i>=0;i--) {
			stack.push(Long.parseLong(String.valueOf(test.charAt(i))));
		}
		/*
		Long rowM = row;
		while(rowM > 0) {
			stack.push(rowM%10);
			rowM = rowM/10;
		}*/
		
		return stack;
	}

}
