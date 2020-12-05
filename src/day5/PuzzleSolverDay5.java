package day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PuzzleSolverDay5 {
	
	List<String> seats = new ArrayList<>();
	Map<String, List<String>> seatComponents = new HashMap<>();
	List<Integer> seatIDs = new ArrayList<>();
	
	public static void main(String[] args) {
		PuzzleSolverDay5 solution = new PuzzleSolverDay5();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		parseInput();
		getAllSeatIDs();
		System.out.println("My seat number: " + getMissingSeatID());
		System.out.println("Total number of seats: " + seats.size());
		System.out.println("Highest seat ID: " + Collections.max(seatIDs));
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
	 * Find getAllSeatIDs
	 */
	private void getAllSeatIDs() {
		for (String seat : seats) {
			seatIDs.add(getNr(seat.substring(0, 7), 127, 'F') * 8 + getNr(seat.substring(7, 10), 7, 'L'));
		}
	}
	
	/** Determines the number of a String */
	private int getNr(final String aString, final int maxNr, final char lowerHalf) {
		List<Integer> currentIDs = IntStream.rangeClosed(0, maxNr).boxed().collect(Collectors.toList());
		for(char c : aString.toCharArray()) {
		    if (c == lowerHalf) {
		    	currentIDs = currentIDs.subList(0,  currentIDs.size()/2);
		    } else {
		    	currentIDs = currentIDs.subList(currentIDs.size()/2,  currentIDs.size());
		    }
		}
		return currentIDs.get(0);
	}
	
	/** Gets the missing seat ID */
	private int getMissingSeatID() {
		Collections.sort(seatIDs);
		int previousSeatID = Collections.min(seatIDs);
		for (int seatID : seatIDs) {
			if (seatID > previousSeatID + 1) {
				return seatID - 1;
			}
			previousSeatID = seatID;
		}
		return 0;
	}
}
