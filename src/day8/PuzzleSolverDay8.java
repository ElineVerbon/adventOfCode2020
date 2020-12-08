package day8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PuzzleSolverDay8 {

	List<String> input = new ArrayList<>();
	
	public static void main(String[] args) {
		PuzzleSolverDay8 solution = new PuzzleSolverDay8();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		parseInput();
		System.out.println("Acc value when executing command a second time: " + executeLoopingInput());
		System.out.println(Integer.parseInt("-5"));
		fixCode();
		System.out.println(accValue);
	}
	
	/** Converts to input to a list of integers */
	private void parseInput() {
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day8/input.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	            input.add(line);
	        }
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/** 
	 * Executes the input and keeps track of lines executed already
	 */
	private int executeLoopingInput() {
		List<Integer> linesRead = new ArrayList<>();
		int accValue = 0;
		int lineToCheck = 0;
		
		while(true) {
			if (linesRead.contains(lineToCheck)) {
				return accValue;
			}
			linesRead.add(lineToCheck);
			switch(input.get(lineToCheck).charAt(0)) {
				case 'a':
					accValue = changeParameter(input.get(lineToCheck).substring(4), accValue);
					lineToCheck += 1;
					break;
				case 'n':
					lineToCheck += 1;
					break;
				case 'j':
					lineToCheck = changeParameter(input.get(lineToCheck).substring(4), lineToCheck);
					break;
			}
		}
	}

		
	private int changeParameter(final String line, final int parameter) {
		char operator = line.charAt(0);
		if (operator == '-') {
			return parameter - Integer.parseInt(line.substring(1));
		} else {
			return parameter + Integer.parseInt(line.substring(1));
		}
	}
	
	
	private void fixCode() {
		while(true) {
			List<Integer> range = IntStream.range(0, input.size()-1).boxed().collect(Collectors.toList());
			for (int n : range) {
				if (input.get(n).charAt(0) == 'j' || input.get(n).charAt(0) == 'n') {
					if(changeInput(n)) {
						return;
					}
				}
			}
		}
	}
	
	
	private boolean changeInput(final int lineNumber) {
		final String currentLine = input.get(lineNumber);
		if (currentLine.substring(0, 3).equals("jmp")) {
			input.set(lineNumber, "nop" + currentLine.substring(3));
		} else {
			input.set(lineNumber, "jmp" + currentLine.substring(3));
		}
		if (!checkForLoopingSolved()) {
			input.set(lineNumber, currentLine);
			return false;
		}
		return true;
	}
	
	int accValue;
	
	/** 
	 * Executes the input and keeps track of lines executed already
	 */
	private boolean checkForLoopingSolved() {
		accValue = 0;
		List<Integer> linesRead = new ArrayList<>();
		int lineToCheck = 0;
		
		while(true) {
			if (linesRead.contains(lineToCheck)) {
				return false;
			}
			linesRead.add(lineToCheck);
			if (lineToCheck > input.size() - 1) {
				return true;
			}
			switch(input.get(lineToCheck).charAt(0)) {
				case 'a':
					accValue = changeParameter(input.get(lineToCheck).substring(4), accValue);
					lineToCheck += 1;
					break;
				case 'n':
					lineToCheck += 1;
					break;
				case 'j':
					lineToCheck = changeParameter(input.get(lineToCheck).substring(4), lineToCheck);
					break;
			}
		}
	}
	
}
