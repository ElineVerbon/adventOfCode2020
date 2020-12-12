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
		int lineLength = getLineLength(path);
		while (true) {
			List<String>  newGrid = fillSeats(lineLength, grid);
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
	
	private int getLineLength(final Path path) throws IOException {
		try (Stream<String> lines = Files.lines(path)) {
			return lines.findFirst().get().length();
	    }
	}
	
	private List<String>  fillSeats(final int lineLength, final List<String>  grid) {
		List<List<Character>> currentLocations = new ArrayList<>();
		List<List<Character>> newLocations = new ArrayList<>();
		
		//go through each line
		List<Integer> lineRange = IntStream.range(0, grid.size()).boxed().collect(Collectors.toList());
		List<Integer> charRange = IntStream.range(0, grid.get(0).length()).boxed().collect(Collectors.toList());
		for (int n : lineRange) {
			List<Character> currentChars = new ArrayList<>();
			List<Character> newChars = new ArrayList<>();
			//go through each char in a line
			String line = grid.get(n);
			for (int c : charRange) {
				currentChars.add(line.charAt(c));
				newChars.add(line.charAt(c));
			}
			currentLocations.add(currentChars);
			newLocations.add(newChars);
			
		}
		char floor = '.';
		char empty = 'L';
		char filled = '#';
		for (int n : lineRange) {
			List<Character> thisLine = currentLocations.get(n);
			for (int c : charRange) {
				if (n == 1 && c == 0) {
					System.out.println("");
				}
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
		
		List<String> result = new ArrayList<>();
		System.out.println("\n\nCurrent board: ");
		for (int n : lineRange) {
			result.add(newLocations.get(n).stream().map(String::valueOf).collect(Collectors.joining()));
			System.out.println();
			for (int c : charRange) {
				System.out.print(newLocations.get(n).get(c));
			}
		}
		return result;
	}
	
	private List<List<Integer>> getSurroundingSeats(final List<List<Character>> locations, final int lineNumber, final int charNumber) {
		List<List<Integer>> surroundingSeats = new ArrayList<>();
		List<Character> currentLine;
		List<Integer> seat;
		
		
		//get first seat to left above, if any
		int currentIndex = charNumber - 1;
		int currentLineNumber = lineNumber - 1;
		while(currentIndex >= 0 && currentLineNumber >= 0) {
			currentLine = locations.get(currentLineNumber);
			if (currentLine.get(currentIndex) != '.') {
				seat = Arrays.asList(new Integer[] { currentLineNumber, currentIndex });
				surroundingSeats.add(seat);
				break;
			}
			currentIndex--;
			currentLineNumber--;
		}
		
		//get first seat to the left, if any
		currentLine = locations.get(lineNumber);
		currentIndex = charNumber - 1;
		while(currentIndex >= 0) {
			if (currentLine.get(currentIndex) != '.') {
				seat = Arrays.asList(new Integer[] { lineNumber, currentIndex });
				surroundingSeats.add(seat);
				break;
			}
			currentIndex--;
		}
		
		//get first seat to left below, if any
		currentIndex = charNumber - 1;
		currentLineNumber = lineNumber + 1;
		while(currentIndex >= 0 && currentLineNumber < locations.size()) {
			currentLine = locations.get(currentLineNumber);
			if (currentLine.get(currentIndex) != '.') {
				seat = Arrays.asList(new Integer[] { currentLineNumber, currentIndex });
				surroundingSeats.add(seat);
				break;
			}
			currentIndex--;
			currentLineNumber++;
		}
		
		//get first seat above, if any
		currentLineNumber = lineNumber - 1;
		while(currentLineNumber >= 0 ) {
			if (locations.get(currentLineNumber).get(charNumber) != '.') {
				seat = Arrays.asList(new Integer[] { currentLineNumber, charNumber });
				surroundingSeats.add(seat);
				break;
			}
			currentLineNumber--;
		}
		
		//get first seat below, if any
		currentLineNumber = lineNumber + 1;
		while(currentLineNumber < locations.size()) {
			if (locations.get(currentLineNumber).get(charNumber) != '.') {
				seat = Arrays.asList(new Integer[] { currentLineNumber, charNumber });
				surroundingSeats.add(seat);
				break;
			}
			currentLineNumber++;
		}
		
		//get first seat to right above, if any
		currentIndex = charNumber + 1;
		currentLineNumber = lineNumber - 1;
		while(currentIndex < currentLine.size() && currentLineNumber >= 0) {
			currentLine = locations.get(currentLineNumber);
			if (currentLine.get(currentIndex) != '.') {
				seat = Arrays.asList(new Integer[] { currentLineNumber, currentIndex });
				surroundingSeats.add(seat);
				break;
			}
			currentIndex++;
			currentLineNumber--;
		}
		
		//get first seat to the right, if any
		currentLine = locations.get(lineNumber);
		currentIndex = charNumber + 1;
		while(currentIndex < currentLine.size()) {
			if (currentLine.get(currentIndex) != '.') {
				seat = Arrays.asList(new Integer[] { lineNumber, currentIndex });
				surroundingSeats.add(seat);
				break;
			}
			currentIndex++;
		}
		
		//get first seat to right below, if any
		currentIndex = charNumber + 1;
		currentLineNumber = lineNumber + 1;
		while(currentIndex < currentLine.size() && currentLineNumber < locations.size()) {
			currentLine = locations.get(currentLineNumber);
			if (currentLine.get(currentIndex) != '.') {
				seat = Arrays.asList(new Integer[] { currentLineNumber, currentIndex });
				surroundingSeats.add(seat);
				break;
			} 
			currentIndex++;
			currentLineNumber++;
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
