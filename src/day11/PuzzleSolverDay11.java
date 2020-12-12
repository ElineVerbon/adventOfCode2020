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

public class PuzzleSolverDay11 {

	public static void main(String[] args) throws IOException {
		PuzzleSolverDay11 solution = new PuzzleSolverDay11();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle 
	 * @throws IOException */
	private void solvePuzzle() throws IOException {
		//parse input
		Path path = Paths.get(System.getProperty("user.dir") + "/src/day11/input.txt");
		String grid = getInput(path);
		int lineLength = getLineLength(path);
		while (true) {
			String newGrid = fillSeats(lineLength, grid);
			if (newGrid.equals(grid)) {
				break;
			} 
			grid = newGrid;
		}
		System.out.println("\n\nNumber occupied seats: " + grid.chars().filter(ch -> ch == '#').count());
	}
	
	private String getInput(final Path path) throws IOException {
		try (Stream<String> lines = Files.lines(path)) {
			return lines.reduce((acc, line) -> acc + line).get();
	    }
	}
	
	private int getLineLength(final Path path) throws IOException {
		try (Stream<String> lines = Files.lines(path)) {
			return lines.findFirst().get().length();
	    }
	}
	
	private String fillSeats(final int lineLength, final String grid) {
		List<Character> locations = new ArrayList<>();
		List<Integer> range = IntStream.range(0, grid.length()).boxed().collect(Collectors.toList());
		for (int n : range) {
			locations.add(grid.charAt(n));
		}
		char floor = '.';
		char empty = 'L';
		char filled = '#';
		for (int n : range) {
			if (n==1) {
				System.out.println("hi");
			}
			if (grid.charAt(n) != floor) {
				List<Integer> surroundingPositions = getSurroundingPositions(grid, n, lineLength);
				int nSurroundingOccupiedSeats = countOccupied(grid, surroundingPositions);
				if (grid.charAt(n) == empty && nSurroundingOccupiedSeats == 0) {
					locations.set(n, filled);
				} else if (grid.charAt(n) == filled && nSurroundingOccupiedSeats >= 4) {
					locations.set(n, empty);
				}
			}
		}
		
		System.out.println("\n\nCurrent board: ");
		for (int n : range) {
			if (n % lineLength == 0 && n != 0) {
				System.out.println();
			}
			System.out.print(locations.get(n));
		}
		return locations.stream().map(String::valueOf).collect(Collectors.joining());
	}
	
	private List<Integer> getSurroundingPositions(final String grid, final int currentPosition, final int lineLength) {
		List<Integer> surroundingPositions = new ArrayList<>();
		//if position all the way at the left of the grid
		if (currentPosition % lineLength == 0) {
			surroundingPositions.addAll(positionsToTheRight(currentPosition, lineLength));
		} else if (currentPosition % lineLength == lineLength - 1) {
			surroundingPositions.addAll(positionsToTheLeft(currentPosition, lineLength));
		} else {
			surroundingPositions.addAll(positionsToTheRight(currentPosition, lineLength));
			surroundingPositions.addAll(positionsToTheLeft(currentPosition, lineLength));
		}
		surroundingPositions.addAll(positionsAboveBelow(currentPosition, lineLength));
		surroundingPositions = surroundingPositions.stream().filter(position -> position >= 0 && position < grid.length()).collect(Collectors.toList());;
		return surroundingPositions;
	}
	
	private List<Integer> positionsToTheRight(final int currentPosition, final int lineLength) {
		List<Integer> positionsRight = new ArrayList<>();
		positionsRight.add(currentPosition - lineLength + 1);
		positionsRight.add(currentPosition + 1);
		positionsRight.add(currentPosition + lineLength + 1);
		return positionsRight;
	}
	
	private List<Integer> positionsToTheLeft(final int currentPosition, final int lineLength) {
		List<Integer> positionsLeft = new ArrayList<>();
		positionsLeft.add(currentPosition - lineLength - 1);
		positionsLeft.add(currentPosition - 1);
		positionsLeft.add(currentPosition + lineLength - 1);
		return positionsLeft;
	}
	
	private List<Integer> positionsAboveBelow(final int currentPosition, final int lineLength) {
		List<Integer> positionsAboveBelow = new ArrayList<>();
		positionsAboveBelow.add(currentPosition - lineLength);
		positionsAboveBelow.add(currentPosition + lineLength);
		return positionsAboveBelow;
	}
	
	private int countOccupied(final String grid, final List<Integer> surroundPositions) {
		int occupied = 0;
		for (int index : surroundPositions) {
			if (grid.charAt(index) == '#') {
				occupied++;
			}
		}
		return occupied;
	}
	
}
