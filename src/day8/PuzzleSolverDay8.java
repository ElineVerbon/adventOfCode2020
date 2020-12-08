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
	int accValue;
	
	public static void main(String[] args) {
		PuzzleSolverDay8 solution = new PuzzleSolverDay8();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		System.out.println("Correct answer problem 1: 1709, correct answer problem 2: 1976");
		parseInput();
		execute();
		System.out.println("1. Acc value when for the first time executing a command a second time: " + accValue);
		fixCode();
		System.out.println("2. Acc value of correct code: " + accValue);
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
	private boolean execute() {
		List<Integer> linesRead = new ArrayList<>();
		accValue = 0;
		int lineToCheck = 0;
		
		while(true) {
			if (linesRead.contains(lineToCheck)) {
				return false;
			}
			if (lineToCheck > input.size() - 1) {
				return true;
			}
			linesRead.add(lineToCheck);
			switch(input.get(lineToCheck).charAt(0)) {
				case 'a':
					accValue += Integer.parseInt(input.get(lineToCheck).substring(4));
					lineToCheck += 1;
					break;
				case 'n':
					lineToCheck += 1;
					break;
				case 'j':
					lineToCheck += Integer.parseInt(input.get(lineToCheck).substring(4));
					break;
			}
		}
	}
	
	/** Fix the code */
	private void fixCode() {
		while(true) {
			List<Integer> range = IntStream.range(0, input.size()-1).boxed().collect(Collectors.toList());
			for (int n : range) {
				if (input.get(n).charAt(0) == 'j' || input.get(n).charAt(0) == 'n') {
					if(changeInputAndCheckForLooping(n)) {
						return;
					}
				}
			}
		}
	}
	
	
	private boolean changeInputAndCheckForLooping(final int lineNumber) {
		final String currentLine = input.get(lineNumber);
		if (currentLine.substring(0, 3).equals("jmp")) {
			input.set(lineNumber, "nop" + currentLine.substring(3));
		} else {
			input.set(lineNumber, "jmp" + currentLine.substring(3));
		}
		if (!execute()) {
			input.set(lineNumber, currentLine);
			return false;
		}
		return true;
	}
	
}
