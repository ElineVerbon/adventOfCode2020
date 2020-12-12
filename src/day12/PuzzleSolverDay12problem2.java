package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PuzzleSolverDay12problem2 {

	public static void main(String[] args) throws IOException {
		PuzzleSolverDay12problem2 solution = new PuzzleSolverDay12problem2();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle 
	 * @throws IOException */
	private void solvePuzzle() throws IOException {
		List<String> clues = parseInput();
		System.out.println("Number of directions: " + clues.size());
		traceRoute(clues);
	}
	
	/** Converts to input to a list of integers 
	 * @throws IOException */
	private List<String> parseInput() throws IOException {
	    try (Stream<String> lines = Files.lines(Paths.get(System.getProperty("user.dir") + "/src/day12/input.txt"))) {
	    	return lines.collect(Collectors.toList());
	    }
	}
	
	/** Trace route */
	private void traceRoute(final List<String> clues) {
		List<Integer> northAndEastPosition = Arrays.asList(new Integer[] { 0, 0 });
		List<Integer> waypointPosition = Arrays.asList(new Integer[] { 1, 10 });
		List<Character> waypointMovementClues = Arrays.asList(new Character[] { 'N', 'E', 'S', 'W' });
		for (String clue : clues) {
			char x = clue.charAt(0);
			if (waypointMovementClues.contains(x)) {
				waypointPosition = moveWaypoint(waypointPosition, x, Integer.parseInt(clue.substring(1)));
			} else if (x == 'L' || x == 'R') {
				waypointPosition = rotateWaypoint(waypointPosition, x, Integer.parseInt(clue.substring(1)));
			} else {
				northAndEastPosition = moveShip(northAndEastPosition, waypointPosition, x, Integer.parseInt(clue.substring(1)));;
			}
		}
		System.out.println("Manhattan distance: " + (Math.abs(northAndEastPosition.get(0)) + Math.abs(northAndEastPosition.get(1))));
	}
	
	private List<Integer> moveWaypoint(final List<Integer> waypointPosition, final char x, final int distance) {
		switch (x) {
			case 'N':
				return Arrays.asList(new Integer[] { waypointPosition.get(0) + distance, waypointPosition.get(1) });
			case 'S':
				return Arrays.asList(new Integer[] { waypointPosition.get(0) - distance, waypointPosition.get(1) });
			case 'E':
				return Arrays.asList(new Integer[] { waypointPosition.get(0), waypointPosition.get(1) + distance });
			default:
				return Arrays.asList(new Integer[] { waypointPosition.get(0), waypointPosition.get(1) - distance });
		}
	}
	
	private List<Integer> rotateWaypoint(List<Integer> waypointPosition, final char x, int degrees) {
		if (x == 'L') {
			degrees = 360 - degrees;
		}
		while (degrees != 0) {
			waypointPosition = Arrays.asList(new Integer[] { - waypointPosition.get(1), waypointPosition.get(0) } );
			degrees = degrees - 90;
		}
		return waypointPosition;
	}
	
	private List<Integer> moveShip(List<Integer> northAndEastPosition, final List<Integer> waypointPosition, final char x, final int nTimes) {
		return Arrays.asList(new Integer[] { northAndEastPosition.get(0) + waypointPosition.get(0) * nTimes, northAndEastPosition.get(1) + waypointPosition.get(1) * nTimes });
	}
	
}
