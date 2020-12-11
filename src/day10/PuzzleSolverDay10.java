package day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PuzzleSolverDay10 {

	public static void main(String[] args) {
		PuzzleSolverDay10 solution = new PuzzleSolverDay10();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		List<Integer> input = parseInput();
		List<List<Integer>> listInParts = solveProblem1AndBreakListIntoListsForProblem2(input);
		solveProblem2(listInParts);
	}
	
	/** Converts to input to a list of integers */
	private List<Integer> parseInput() {
		List<Integer> input = new ArrayList<>();
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
		
		Collections.sort(input);
	    return input;
	}

	/**
	 * Counts the number of steps of 1 and 3 when using all adapters.
	 * Breaks the chain of all adapters in smaller chains that are connected via ONLY a 3-point gap
	 * @param adapterList
	 * @return
	 */
	private List<List<Integer>> solveProblem1AndBreakListIntoListsForProblem2(final List<Integer> adapterList) {
		List<List<Integer>> adapterListInParts = new ArrayList<>();
		List<Integer> currentList = new ArrayList<>();
		currentList.add(0);
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
            	adapterListInParts.add(currentList);
            	currentList = new ArrayList<>();
            }
            currentList.add(thisAdapter);
            previousAdapter = thisAdapter;
        } 
        adapterListInParts.add(currentList);
        System.out.println("Solution problem 1: " + nr1s * (nr3s + 1));
		return adapterListInParts;
	}
	
	private void solveProblem2(final List<List<Integer>> adapterListInParts) {
		List<Long> optionsPerPart = new ArrayList<>();
		
		for(List<Integer> partOfList : adapterListInParts) {
			if (partOfList.size() == 1) {
				continue;
			}
			List<Integer> solution = new ArrayList<>();
			solution.add(partOfList.get(0));
			
			solve(partOfList, solution);
			optionsPerPart.add(nSolutions);
			nSolutions = (long) 0;
		}
		
		System.out.println(optionsPerPart.stream().reduce((long) 1, (a, b) -> a * b));
		
	}
	
	Long nSolutions = (long) 0;
	
	private void solve(final List<Integer> adapterList, final List<Integer> solution) {
		final int lastAdapter = adapterList.get(adapterList.size()-1);
		while (solution.get(solution.size()-1) != lastAdapter) {
			final int lastAddedNumber = solution.get(solution.size()-1);
			if (adapterList.contains(lastAddedNumber+1)) {
				solution.add(lastAddedNumber+1);
				if(lastAddedNumber+1 == lastAdapter) {
					nSolutions++;
					break;
				} else {
					solve(adapterList, solution);
				}
			}
			if (adapterList.contains(lastAddedNumber+2)) {
				solution.add(lastAddedNumber+2);
				if(lastAddedNumber+2 == lastAdapter) {
					nSolutions++;
					break;
				} else {
					solve(adapterList, solution);
				}
			}
			if (adapterList.contains(lastAddedNumber+3)) {
				solution.add(lastAddedNumber+3);
				if(lastAddedNumber+3 == lastAdapter) {
					nSolutions++;
					break;
				} else {
					solve(adapterList, solution);
				}
			}
		}
		return;
	}
	
}
