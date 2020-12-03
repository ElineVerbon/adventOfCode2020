package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.IntStream;

public class PuzzleSolverDay3 {
	int rowLength;
	int nrRows = 0;
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
		final int solution1 = countTrees(1, 1);
		System.out.println("solution 1: " + solution1);
		final int solution2 = countTrees(3, 1);
		System.out.println(solution2);
		final int solution3 = countTrees(5, 1);
		System.out.println(solution3);
		final int solution4 = countTrees(7, 1);
		System.out.println(solution4);
		final int solution5 = countTrees(1, 2);
		System.out.println(solution5);
		
		System.out.println("Number of trees when going 3 right, 1 down: " + solution2);
		System.out.println("Number of trees problem 2: " + (solution1 * solution2 * solution3 * solution4 * solution5));
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
	private int countTrees(final int stepsSideways, final int stepsDown) {
		resetCounters();
		if (landscape.charAt(0) == tree) {
			nrTrees += 1;
		}
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
	        }
	    );
		
		return nrTrees;
	}
	
	/** Resets the counters */
	private void resetCounters() {
		positionInLandscape = 0;
		nrTrees = 0;
	}
	
}
