package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PuzzleSolverDay11problem2 {

	public static void main(String[] args) throws IOException {
		PuzzleSolverDay11problem2 solution = new PuzzleSolverDay11problem2();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle 
	 * @throws IOException */
	private void solvePuzzle() throws IOException {
		//parse input
		Path path = Paths.get(System.getProperty("user.dir") + "/src/day11/input.txt");
		List<String> grid = getInput(path);
		while (true) {
			List<String>  newGrid = fillSeats(grid);
			if (newGrid.equals(grid)) {
				break;
			} 
			grid = newGrid;
		}
		System.out.println("\n\nNumber occupied seats: " + countTotalOccupiedSeats(grid));
	}
	
	private List<String> getInput(final Path path) throws IOException {
		try (Stream<String> lines = Files.lines(path)) {
			return lines.collect(Collectors.toList());
	    }
	}
	
	private List<String>  fillSeats(final List<String>  grid) {
		List<List<List<Character>>> gridAsTwoLists = saveGridAsLists(grid);
		List<List<Character>> newLocations = modelSeatingUntilStabilized(gridAsTwoLists.get(0), gridAsTwoLists.get(1));
		return printAndReturnResult(newLocations);
	}
	
	private List<List<List<Character>>> saveGridAsLists(final List<String> grid) {
		List<List<Character>> currentLocations = new ArrayList<>();
		List<List<Character>> newLocations = new ArrayList<>();

		List<Integer> lineRange = IntStream.range(0, grid.size()).boxed().collect(Collectors.toList());
		List<Integer> charRange = IntStream.range(0, grid.get(0).length()).boxed().collect(Collectors.toList());
		
		for (int n : lineRange) {
			List<Character> currentChars = new ArrayList<>();
			List<Character> newChars = new ArrayList<>();
			String line = grid.get(n);
			for (int c : charRange) {
				currentChars.add(line.charAt(c));
				newChars.add(line.charAt(c));
			}
			currentLocations.add(currentChars);
			newLocations.add(newChars);
			
		}
		List<List<List<Character>>> gridAsTwoLists = new ArrayList<>();
		gridAsTwoLists.add(currentLocations);
		gridAsTwoLists.add(newLocations);
		return gridAsTwoLists;
	}
	
	private List<List<Character>> modelSeatingUntilStabilized(final List<List<Character>> currentLocations, final List<List<Character>> newLocations) {
		char floor = '.';
		char empty = 'L';
		char filled = '#';
		List<Integer> lineRange = IntStream.range(0, currentLocations.size()).boxed().collect(Collectors.toList());
		List<Integer> charRange = IntStream.range(0, currentLocations.get(0).size()).boxed().collect(Collectors.toList());
		
		for (int n : lineRange) {
			List<Character> thisLine = currentLocations.get(n);
			for (int c : charRange) {
				if (thisLine.get(c) != floor) {
					List<List<Integer>> surroundingSeats = getSurroundingSeats(currentLocations, n, c);
					int nSurroundingOccupiedSeats = countOccupied(currentLocations, surroundingSeats);
					if (currentLocations.get(n).get(c) == empty && nSurroundingOccupiedSeats == 0) {
						newLocations.get(n).set(c, filled);
					} else if (currentLocations.get(n).get(c) == filled && nSurroundingOccupiedSeats >= 5) {
						newLocations.get(n).set(c, empty);
					}
				}
			}
		}
		return newLocations;
	}
	
	private List<String> printAndReturnResult(final List<List<Character>> newLocations) {
		List<String> result = new ArrayList<>();
		List<Integer> lineRange = IntStream.range(0, newLocations.size()).boxed().collect(Collectors.toList());
		List<Integer> charRange = IntStream.range(0, newLocations.get(0).size()).boxed().collect(Collectors.toList());
		System.out.println("\n\nCurrent board: ");
		for (int n : lineRange) {
			//add line to results
			result.add(newLocations.get(n).stream().map(String::valueOf).collect(Collectors.joining()));
			//print the line on a new line
			System.out.println();
			for (int c : charRange) {
				System.out.print(newLocations.get(n).get(c));
			}
		}
		return result;
	}
	
	private List<List<Integer>> getSurroundingSeats(final List<List<Character>> locations, final int lineNumber, final int charNumber) {
		List<List<Integer>> surroundingSeats = new ArrayList<>();
		
		//get first seat to left above, if any
		surroundingSeats = getASurroundingSeat(surroundingSeats, locations, lineNumber, charNumber, -1, -1);
		
		//get first seat to the left, if any
		surroundingSeats = getASurroundingSeat(surroundingSeats, locations, lineNumber, charNumber, -1, 0);
		
		//get first seat to left below, if any
		surroundingSeats = getASurroundingSeat(surroundingSeats, locations, lineNumber, charNumber, -1, +1);
		
		//get first seat above, if any
		surroundingSeats = getASurroundingSeat(surroundingSeats, locations, lineNumber, charNumber, 0, -1);
		
		//get first seat below, if any
		surroundingSeats = getASurroundingSeat(surroundingSeats, locations, lineNumber, charNumber, 0, +1);
		
		//get first seat to right above, if any
		surroundingSeats = getASurroundingSeat(surroundingSeats, locations, lineNumber, charNumber, +1, -1);
		
		//get first seat to the right, if any
		surroundingSeats = getASurroundingSeat(surroundingSeats, locations, lineNumber, charNumber, +1, 0);
		
		//get first seat to right below, if any
		surroundingSeats = getASurroundingSeat(surroundingSeats, locations, lineNumber, charNumber, +1, +1);
		
		return surroundingSeats;
	}
	
	private List<List<Integer>> getASurroundingSeat(final List<List<Integer>> surroundingSeats, final List<List<Character>> locations, final int lineNumber, final int charNumber
			, final int changeInIndex, final int changeInLineNumber) {
		int currentIndex = charNumber + changeInIndex;
		int currentLineNumber = lineNumber + changeInLineNumber;
		while(currentIndex >= 0 && currentIndex < locations.get(0).size() && currentLineNumber >= 0 && currentLineNumber < locations.size()) {
			List<Character> currentLine = locations.get(currentLineNumber);
			if (currentLine.get(currentIndex) != '.') {
				List<Integer> seat = Arrays.asList(new Integer[] { currentLineNumber, currentIndex });
				surroundingSeats.add(seat);
				break;
			}
			currentIndex += changeInIndex;
			currentLineNumber += changeInLineNumber;
		}
		return surroundingSeats;
	}
	
	private int countOccupied(final List<List<Character>> locations, final List<List<Integer>> surroundingSeats) {
		int occupied = 0;
		for (List<Integer> seat : surroundingSeats) {
			if (locations.get(seat.get(0)).get(seat.get(1)) == '#') {
				occupied++;
			}
		}
		return occupied;
	}
	
	private int countTotalOccupiedSeats(final List<String> grid) {
		int occupied = 0;
		List<Integer> range = IntStream.range(0, grid.get(0).length()).boxed().collect(Collectors.toList());
		for (String line : grid) {
			for (int c : range) {
				if(line.charAt(c) == '#') {
					occupied++;
				}
			}
		}
		return occupied;
	}
	
}
