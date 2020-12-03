package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.IntStream;

public class PuzzleSolverDay3 {
	int rowLength;
	int nrRows = 0;
	int rowsSeen;
	int stepsSideways = 3;
	int stepsDown = 1;
	String landscape;
	int positionInLandscape = 0;
	char tree = '#';
	char open = '.';
	int nrTrees = 0;
	
	public static void main(String[] args) {
		PuzzleSolverDay3 solution = new PuzzleSolverDay3();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		parseInput();
    	System.out.println("row length: " + rowLength);
    	System.out.println("landscape length: " + landscape.length());
    	System.out.println("nr rows: " + nrRows);
		countTrees();
		
		System.out.println("Number of trees when going 3 right, 1 down: " + nrTrees);
	}
	
	/** Parses input */
	private void parseInput() {
	    try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day3/inputDay3.txt"));
	        String line;
	        line = br.readLine();
	        rowLength = line.length();
    		landscape = line;
    		nrRows = 1;
	    	while ((line = br.readLine() )!= null) {
	    		landscape += line;
	    		nrRows += 1;
	        }
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/** Counts the trees */
	private void countTrees() {
		if (landscape.charAt(0) == tree) {
			nrTrees += 1;
		}
		rowsSeen = 1;
		IntStream.range(1, nrRows/stepsDown).forEach(
	        n -> {
	        	if ((positionInLandscape%(rowLength) + stepsSideways) > (rowLength-1)) {
					positionInLandscape += (rowLength * (stepsDown - 1)) + stepsSideways;
				} else {
					positionInLandscape += (rowLength * (stepsDown)) + stepsSideways;
				}
	        	if (landscape.charAt(positionInLandscape) == tree) {
	    			nrTrees += 1;
	    		}
	        	rowsSeen += 1;
	        }
	    );
		System.out.println(rowsSeen);
	}
	
}
