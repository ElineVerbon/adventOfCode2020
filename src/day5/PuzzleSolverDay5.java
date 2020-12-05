package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PuzzleSolverDay5 {
	
	List<String> seats = new ArrayList<>();
	Map<String, List<String>> seatComponents = new HashMap<>();
	
	public static void main(String[] args) {
		PuzzleSolverDay5 solution = new PuzzleSolverDay5();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		parseInput();
		breakSeatsIntoComponents();
		final int maxSeatID = getHighestSeatID();
		System.out.println("Total number of seats: " + seats.size());
		
		System.out.println("Highest seat ID: " + maxSeatID);
	}
	
	/** Parses input */
	private void parseInput() {
		try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day5/input.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	    		seats.add(line);
	        }
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/** 
	 * Breaks a seat into the first seven characters (row) and last three (column) 
	 * and puts them in a map, with all seat in the same row in the same key
	 */
	private void breakSeatsIntoComponents() {
		for (String seat : seats) {
			String row = seat.substring(0, 7);
			String column = seat.substring(7, 10);
			if (seatComponents.containsKey(row)) {
				List<String> columns = seatComponents.get(row);
				columns.add(column);
			} else {
				List<String> columns = new ArrayList<>();
				columns.add(column);
				seatComponents.put(row,columns);
			}
			
		}
	}
	
	/**
	 * Find the highest seat ID
	 */
	private int getHighestSeatID() {
		int maxRowID = 0;
		int maxID = 0;
		for (Map.Entry<String,List<String>> entry : seatComponents.entrySet()) {
			final int rowID = getNr(entry.getKey(), 127, 'F', 'B');
			if (rowID > maxRowID) {
				maxRowID = rowID;
				int maxColID = 0;
				for (String col : entry.getValue()) {
					final int colID = getNr(col, 7, 'L', 'R');
					if (colID > maxColID) {
						maxColID = colID;
					}
				}
				maxID = maxRowID * 8 + maxColID;
			};
		}
		return maxID;
	}
	
	/** Determines the number of a String */
	private int getNr(final String aString, final int maxNr, final char lowerHalf, final char upperHalf) {
		List<Integer> currentIDs = IntStream.rangeClosed(0, maxNr).boxed().collect(Collectors.toList());
		for(char c : aString.toCharArray()) {
		    if (c == lowerHalf) {
		    	currentIDs = currentIDs.subList(0,  currentIDs.size()/2);
		    }
		    if (c == upperHalf) {
		    	currentIDs = currentIDs.subList(currentIDs.size()/2,  currentIDs.size());
		    }
		}
		return currentIDs.get(0);
	}
}
