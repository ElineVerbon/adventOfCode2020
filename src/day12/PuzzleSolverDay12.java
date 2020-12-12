package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PuzzleSolverDay12 {

	public static void main(String[] args) throws IOException {
		PuzzleSolverDay12 solution = new PuzzleSolverDay12();
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
		List<Character> movementClues = Arrays.asList(new Character[] { 'N', 'E', 'S', 'W', 'F' });
		Direction direction = Direction.East;
		for (String clue : clues) {
			char x = clue.charAt(0);
			if (movementClues.contains(x)) {
				northAndEastPosition = doMove(northAndEastPosition, direction, x, Integer.parseInt(clue.substring(1)));
			} else {
				direction = changeDirection(direction, x, Integer.parseInt(clue.substring(1)));
			}
//			System.out.println("east: " + northAndEastPosition.get(1));
//			System.out.println("north: " + northAndEastPosition.get(0));
		}
		System.out.println(Math.abs(northAndEastPosition.get(0)) + Math.abs(northAndEastPosition.get(1)));
	}
	
	private enum Direction
	{
	    North, East, South, West;

	    static 
	    public final Direction[] values = values();

	    public Direction next() {
	        return values[(ordinal() + 1) % values.length];
	    }
	}
	
	private List<Integer> doMove(final List<Integer> northAndEastPosition, final Direction direction, final char x, final int distance) {
		switch (x) {
			case 'N':
				return Arrays.asList(new Integer[] { northAndEastPosition.get(0) + distance, northAndEastPosition.get(1) });
			case 'S':
				return Arrays.asList(new Integer[] { northAndEastPosition.get(0) - distance, northAndEastPosition.get(1) });
			case 'E':
				return Arrays.asList(new Integer[] { northAndEastPosition.get(0), northAndEastPosition.get(1) + distance });
			case 'W':
				return Arrays.asList(new Integer[] { northAndEastPosition.get(0), northAndEastPosition.get(1) - distance });
			default:
				return doForwardMove(northAndEastPosition, direction, x, distance);
		}
	}
	
	private List<Integer> doForwardMove(final List<Integer> northAndEastPosition, final Direction direction, final char x, final int distance) {
		switch (direction) {
			case North:
				return Arrays.asList(new Integer[] { northAndEastPosition.get(0) + distance, northAndEastPosition.get(1) });
			case South:
				return Arrays.asList(new Integer[] { northAndEastPosition.get(0) - distance, northAndEastPosition.get(1) });
			case East:
				return Arrays.asList(new Integer[] { northAndEastPosition.get(0), northAndEastPosition.get(1) + distance });
			default:
				return Arrays.asList(new Integer[] { northAndEastPosition.get(0), northAndEastPosition.get(1) - distance });
		}
	}
	
	private Direction changeDirection(final Direction direction, final char x, final int degrees) {
		switch (x) {
			case 'R':
				return turn(direction, degrees);
			default:
				return turn(direction, 360 - degrees);
		}
	}
	
	private Direction turn(Direction direction, int degrees) {
		while (degrees != 0) {
			direction = direction.next();
			degrees -= 90;
		}
		return direction;
	}
	
}
