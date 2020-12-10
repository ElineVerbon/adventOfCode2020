package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PuzzleSolverDay10 {

	public static void main(String[] args) {
		PuzzleSolverDay10 solution = new PuzzleSolverDay10();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		SortedSet<Integer> input = parseInput();
		System.out.println("Number of adapters: " + input.size());
		System.out.println("Solution problem 1: " + solvePuzzle(input));
	}
	
	/** Converts to input to a list of integers */
	private SortedSet<Integer> parseInput() {
		SortedSet<Integer> input = new TreeSet<>();
		try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day10/input.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	            input.add(Integer.parseInt(line));
	        }
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return input;
	}

	private int solvePuzzle(final SortedSet<Integer> adapterList) {
		int nr3s = 0;
		int nr1s = 0;
		int previousAdapter = 0;
		Iterator<Integer> adapter = adapterList.iterator(); 
        while (adapter.hasNext()) { 
        	int thisAdapter = adapter.next();
            if(thisAdapter - 1 == previousAdapter) {
            	nr1s++;
            } else if(thisAdapter - 3 == previousAdapter) {
            	nr3s++;
            }
            previousAdapter = thisAdapter;
        } 
		return nr1s * (nr3s + 1);
	}
}
