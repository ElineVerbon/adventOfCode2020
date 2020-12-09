package day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PuzzleSolverDay9 {

	public static void main(String[] args) {
		PuzzleSolverDay9 solution = new PuzzleSolverDay9();
		solution.solvePuzzle();
	}
	
	/** Solves the puzzle */
	private void solvePuzzle() {
		List<Long> input = parseInput();
		final long incorrectValue = findIncorrectValue(input);
		System.out.println("1. Incorrect value: " + findIncorrectValue(input));
		System.out.println("2. The sum: " + findContiguousSet(input, incorrectValue));
	}
	
	/** Converts to input to a list of integers */
	private List<Long> parseInput() {
		List<Long> input = new ArrayList<>();
		try {
	    	BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/src/day9/input.txt"));
	        String line;
	    	while ((line = br.readLine() )!= null) {
	            input.add(Long.parseLong(line));
	        }
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return input;
	}
	
	private long findIncorrectValue(final List<Long> input) {
		List<Integer> range = IntStream.range(25, input.size()).boxed().collect(Collectors.toList());
		for (int n : range) {
			if(!checkValidity(input.stream().skip(n-25).limit(25).collect(Collectors.toList()), input.get(n))) {
				return input.get(n);
			}
		}
		return 0;
	}	
	
	private boolean checkValidity(final List<Long> precedingInts, final long thisInt) {
		List<Integer> range = IntStream.range(0, precedingInts.size()).boxed().collect(Collectors.toList());
		for (int n : range) {
			long option1 = precedingInts.get(n);
			for (int x : range) {
				if(n != x) {
					long option2 = precedingInts.get(x);
					if (option1 + option2 == thisInt) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private long findContiguousSet(final List<Long> input, final long theInt) {
		List<Integer> range = IntStream.range(0, input.size()).boxed().collect(Collectors.toList());
		for (int n : range) {
			long sum = 0;
			int currentIndex = n;
			List<Long> theNumbers = new ArrayList<>();
			while(sum < theInt) {
				sum += input.get(currentIndex);
				theNumbers.add(input.get(currentIndex));
				currentIndex++;
			}
			if(sum == theInt) {
				return Collections.min(theNumbers) + Collections.max(theNumbers);
			}
		}
		return 0;
	}
	
}
